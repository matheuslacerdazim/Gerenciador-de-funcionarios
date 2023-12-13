package com.gerenciador.funcionarios;

import com.gerenciador.funcionarios.model.Endereco;
import com.gerenciador.funcionarios.model.Funcionario;
import com.gerenciador.funcionarios.service.EnderecoService;
import com.gerenciador.funcionarios.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.Map;

@SpringBootApplication
public class FuncionariosApplication {

	public static void main(String[] args) {SpringApplication.run(FuncionariosApplication.class, args);}

}
