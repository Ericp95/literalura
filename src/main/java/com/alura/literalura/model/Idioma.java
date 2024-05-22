package com.alura.literalura.model;


import com.alura.literalura.model.dto.IdiomaDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="idiomas")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    String siglaIdioma;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Libro> libros;
    public Idioma(IdiomaDTO idiomaDTO ){
        siglaIdioma = idiomaDTO.lenguajes().get(0);
    }

    public  Idioma(){}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getIdioma() {
        return siglaIdioma;
    }

    public void setIdioma(String idioma) {
        this.siglaIdioma = idioma;
    }
}
