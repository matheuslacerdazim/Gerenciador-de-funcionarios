package com.gerenciador.funcionarios.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePayLoad {

    private String message;
    private LocalDateTime dataHora;

    public ResponsePayLoad(String message){
        this.message = message;
        this.dataHora = LocalDateTime.now();
    }
}
