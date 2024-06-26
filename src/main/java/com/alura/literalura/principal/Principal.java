package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.ConvierteDatos;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import com.alura.literalura.model.dto.AutorDTO;
import com.alura.literalura.model.dto.LibroDTO;
import com.alura.literalura.model.dto.ResultadoConsultaDTO;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.IIdiomaRepository;
import com.alura.literalura.repository.ILibroRepository;
import com.alura.literalura.service.ConsumoApi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private ConsumoApi conn = new ConsumoApi();
    private Scanner teclado = new Scanner(System.in);
    private String busquedaUsuario;
    private ConvierteDatos conversor = new ConvierteDatos();
    private ILibroRepository libroRepo;
    private IAutorRepository autorRepo;
    private IIdiomaRepository idiomaRepo;
    public Principal(ILibroRepository libroRepo, IAutorRepository autorRepo, IIdiomaRepository idiomaRepo){
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
        this.idiomaRepo = idiomaRepo;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro
                    2 - Listar autores registrados
                    3 - Listar libros registrados
                    4 - Listar libros por idiomas
                    5 - Buscar autores vivos en un determinado año             
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    obtenerLibro();
                    break;
                case 2:
                    obtenerListaAutores();
                    break;
                case 3:
                    obtenerListadoLibrosBuscados();
                    break;
                case 4:
                    obtenerLibrosPorIdioma();
                    break;
                case 5:
                    obtenerAutoresVivosEnAnio();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }//fin mostrar el menu

    private void obtenerLibro() {
        System.out.println("Ingrese el libro que desea buscar");
        busquedaUsuario = teclado.nextLine();
        Optional<String> resultado = conn.buscarLibro(busquedaUsuario);
        //Verificamos que haya respuesta de la API y que no este vacia,
        // sino significa que no se encuentra el libro
        if (resultado.isPresent() &&
                conversor.convertirDatos(resultado.get(), ResultadoConsultaDTO.class).resultado().size()>0)
        {
            ResultadoConsultaDTO resultConsulta = conversor
                    .convertirDatos(resultado.get(), ResultadoConsultaDTO.class);
            LibroDTO libroObtenido = resultConsulta.resultado().get(0);
            //Por cada libroDTO obtengo el autorDTO de la lista AutorDTO de la respuesta
            Autor autorResultado = new Autor(libroObtenido.autor().isEmpty() ?
                    new AutorDTO("Desconocido", 0,0) :
                    libroObtenido.autor().get(0));
            Libro libroGuardar = new Libro(libroObtenido);
            Idioma idiomaGuardar = libroGuardar.getIdioma();

            //Verificamos que no exista ya el autor del libro en la base de datos
            List<Autor> autoresBase = autorRepo
                    .findIdByNombreContainsIgnoreCase(autorResultado.getNombre());

            //Verificamos que no exista ya el autor del libro en la base de datos
            List<Libro> librosBase = libroRepo
                    .findTituloByTituloContainsIgnoreCase(libroGuardar.getTitulo());

            //Verificamos que no exista ya el Idioma del libro en la base de datos
            List<Idioma> idiomasBase = idiomaRepo.findBySiglaIdioma(idiomaGuardar.getSiglaIdioma());

            //Si hay idiomas guardados en la base, se procede a verificar
            if(!idiomasBase.isEmpty()){
                //Verificamos que el idioma del libro sea igual a alguno de la base
                idiomasBase.forEach(i -> {
                    if (idiomaGuardar.getSiglaIdioma().contains(i.getSiglaIdioma())){
                        libroGuardar.setIdioma(i);
                    }
                    //Sino se procede a guardar el nuevo idioma
                    else {
                        idiomaRepo.save(idiomaGuardar);
                    }
                });
            }
            //Si no hay idiomas aun guardados, se procede a guardar el primero en automatico
            else idiomaRepo.save(idiomaGuardar);

            //Si no existe ni autor ni libro en la base, se añaden a la base
            if (autoresBase.size() == 0 && librosBase.size() == 0){
                autorRepo.save(autorResultado);
                libroGuardar.setAutor(autorResultado);
                libroRepo.save(libroGuardar);

            }
            //Si existe el autor pero el libro no, se añade el libro con el autor de la base
            else if (autoresBase.size() != 0 && librosBase.size() == 0){
                libroGuardar.setAutor(autoresBase.get(0));
                libroRepo.save(libroGuardar);
            }
            System.out.println(libroGuardar);

        } else {
            System.out.println("Lo sentimos, no se ha encontrado el libro que buscas");
        }
    }

    private void obtenerListadoLibrosBuscados(){
        List<Libro> listaLibros = libroRepo.findAll();
        if (listaLibros.size() > 0){
            System.out.println("Los libros que has buscado son:");
            listaLibros.forEach(System.out::println);
        } else {
            System.out.println("No hay ningun libro registrado hasta el momento");
        }
    }

    private void obtenerLibrosPorIdioma(){
        System.out.println("Ingrese las siglas del idioma a filtrar:");
        List<Idioma> listaIdiomas = idiomaRepo.findAll();
        listaIdiomas.forEach(i -> System.out.println(i.getSiglaIdioma()));
        String idiomaAFiltrar = verificarSiglasIdioma();
        listaIdiomas.forEach(idioma -> {
            if (idioma.getSiglaIdioma().equals(idiomaAFiltrar)){
                System.out.println(idioma.getLibros());
            }
        });
    }

    private void obtenerListaAutores(){
        List<Autor> listaAutores = autorRepo.findAll();
        if(listaAutores.size() > 0){
            System.out.println("Los autores registrados son:");
            listaAutores.forEach(System.out::println);
        } else {
            System.out.println("No hay ningun autor registrado por el momento");
        }
    }

    private void obtenerAutoresVivosEnAnio(){
        int anioLimite = 0;
        List<Autor> listaAutores;
        System.out.println("""
                ----------------------------------
                1 - Buscar autores hasta un año
                2 - Buscar autores de un año hasta otro
                0 - volver al menu anterior
                ----------------------------------
                """);
        busquedaUsuario = verificarIngresoUsuario();
        while (!busquedaUsuario.equals("0")){
            switch (busquedaUsuario){
                case "1":
                    System.out.println("Ingrese el año hasta donde desea buscar:");
                    anioLimite = verificarIngresoAnio();
                    listaAutores = autorRepo.findByAnnoFallecimientoLessThanEqual(anioLimite);
                    if (listaAutores.size() > 0){
                        listaAutores.forEach(System.out::println);
                    } else {
                        System.out.println("No hay ningun Autor que existiese antes de ese año");
                    }
                    return;
                case "2":
                    System.out.println("Ingrese el año de nacimiento");
                    int anioNacimiento = verificarIngresoAnio();
                    System.out.println("Ahora el año de Limite");
                    anioLimite = verificarIngresoAnio();
                    listaAutores = autorRepo
                            .findByAnnoNacimientoBetween(anioNacimiento, anioLimite);
                    if (!listaAutores.isEmpty()){
                        listaAutores.forEach(System.out::println);
                    } else {
                        System.out.println("No hay ningun Autor que existiese antes de ese año");
                    }
                    return;
            }
        }
    }

    private String verificarIngresoUsuario(){
        String resultado = "";
        while (!resultado.matches("^\\d$")){
            resultado = teclado.nextLine();
            if(!resultado.matches("^\\d$")){
                System.out.println("Ingrese un valor valido:");
            } else return resultado;
        }
        return null;
    }

    private int verificarIngresoAnio(){
        String resultado = "";
        while (!resultado.matches("^\\d{4}$")){
            resultado = teclado.nextLine();
            if(!resultado.matches("^\\d{4}$")){
                System.out.println("Ingrese un valor valido:");
            } else break;
        }
        return Integer.valueOf(resultado);
    }

    private String verificarSiglasIdioma(){
        String resultado = "";
        while (!resultado.matches("^\\w{2}$")){
            resultado = teclado.nextLine();
            if(!resultado.matches("^\\w{2}$")){
                System.out.println("Ingrese un valor valido:");
            } else return resultado;
        }
        return null;
    }

}

