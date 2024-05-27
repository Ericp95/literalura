package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findTituloByTituloContainsIgnoreCase(String tituloLibro);
}
