package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ConsumoApi {

    private HttpClient client;
    private String urlApi;
    private HttpRequest req;
    HttpResponse<String> response;
    public ConsumoApi(){
        client = HttpClient.newHttpClient();
        urlApi = "https://gutendex.com/books/";
    }
    /**
     * @return Optional<String> del valor de la consulta a la API
     * */
    public Optional<String> obtenerLibros(){
        req = HttpRequest.newBuilder()
                .uri(URI.create(urlApi)).build();
        response = envioConsulta(req);
        return Optional.ofNullable(response.body());
    }


    public Optional<String> buscarLibro(String titulo){
        req = HttpRequest.newBuilder()
                .uri(URI.create((urlApi + "?search=" + titulo).replaceAll(" ", "%20")))
                .build();
        response = envioConsulta(req);
        return Optional.ofNullable(response.body());
    }

    /**
     * Funcion que trabaja los posibles errores al intentar conectar y acceder a la API
     * @param request peticion que se desea enviar a API
     * @return HtttpResponse<Strin> del valor de la consulta a la API
     * */
    private HttpResponse<String> envioConsulta(HttpRequest request){
        try {
            response = client.send(req, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch(InterruptedException | IOException e){
            System.out.println("Ocurrio un error al intentar acceder a la Api");
        }
        return null;
    }
}
