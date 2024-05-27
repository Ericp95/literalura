package com.alura.literalura.model;

import com.alura.literalura.model.dto.AutorDTO;
import com.alura.literalura.model.dto.IdiomaDTO;
import com.alura.literalura.model.dto.LibroDTO;
import jakarta.persistence.*;

@Entity
@Table(name="libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Idioma idioma;
    private int descargas;
    @Column(name = "id_api")
    private int idApi;


    public Libro(){}

    public Libro (LibroDTO libroDTO){
        this.titulo= libroDTO.titulo();
        this.idApi=libroDTO.id();
        this.descargas=libroDTO.descargas();
        this.autor= new Autor(libroDTO.autor().isEmpty() ?
                new AutorDTO("Desconocido", 0,0)
                : libroDTO.autor().get(0));
        this.idioma= new Idioma(new IdiomaDTO(libroDTO.idioma()));


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return """
                
                ------LIBRO------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                -----------------
                """.formatted(titulo, autor.getNombre(), idioma.siglaIdioma, descargas);
    }
}
//generar getter y setter despues!!!