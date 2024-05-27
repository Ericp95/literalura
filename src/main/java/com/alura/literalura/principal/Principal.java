package com.alura.literalura.principal;

import com.alura.literalura.model.ConvierteDatos;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.IIdiomaRepository;
import com.alura.literalura.repository.ILibroRepository;
import com.alura.literalura.service.ConsumoApi;

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

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
}

