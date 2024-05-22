package com.alura.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias(value = "name") String nombre,
        @JsonAlias(value = "birth_year") int annoNacimiento,
        @JsonAlias(value = "death_year")int annoMuerte

) {
}