package com.gerenciador.funcionarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.funcionarios.exception.ResourceNotFoundException;
import com.gerenciador.funcionarios.model.Endereco;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EnderecoService {

    public Endereco getByCep(String cep) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("https://brasilapi.com.br/api/cep/v2/" + cep))
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new ResourceNotFoundException(response.body());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Endereco endereco = objectMapper.readValue(response.body(),Endereco.class);
            return endereco;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
