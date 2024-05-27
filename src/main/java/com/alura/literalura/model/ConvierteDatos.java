package com.alura.literalura.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {

    private ObjectMapper mapper = new ObjectMapper();


    public <T> T convertirDatos (String json, Class<T> clase){
        try {
            return mapper.readValue(json, clase);
        } catch (JsonMappingException e) {
            System.out.println("Ocurrio un error al intentar convertir los datos Json");
        } catch (JsonProcessingException e){
            System.out.println("Ocurrio en el proceso al convertir los datos Json");
        }
        return null;
    }
}
