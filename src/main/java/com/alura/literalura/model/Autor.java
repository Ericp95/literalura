package com.alura.literalura.model;

import com.alura.literalura.model.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int annoNacimiento;
    private int annoFallecimiento;
    private String nombre;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}
    public Autor(AutorDTO autorDTO){
        this.annoNacimiento= autorDTO.annoNacimiento();
        this.annoFallecimiento=autorDTO.annoMuerte();
        this.nombre= autorDTO.nombre();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnnoFallecimiento() {
        return annoFallecimiento;
    }

    public void setAnnoFallecimiento(int annoFallecimiento) {
        this.annoFallecimiento = annoFallecimiento;
    }

    public int getAnnoNacimiento() {
        return annoNacimiento;
    }

    public void setAnnoNacimiento(int annoNacimiento) {
        this.annoNacimiento = annoNacimiento;
    }

    @Override
    public String toString() {
        return """
                
                ------AUTOR------
                Nombre: %s
                Año nacimiento: %d
                Año fallecimiento: %d
                Libros: %s
                -----------------
                """.formatted(nombre, annoNacimiento, annoFallecimiento,libros);
    }
}
