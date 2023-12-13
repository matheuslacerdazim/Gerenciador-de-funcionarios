package com.gerenciador.funcionarios;

import com.gerenciador.funcionarios.exception.ResourceNotFoundException;
import com.gerenciador.funcionarios.model.Coordinates;
import com.gerenciador.funcionarios.model.Endereco;
import com.gerenciador.funcionarios.model.Funcionario;
import com.gerenciador.funcionarios.model.Location;
import com.gerenciador.funcionarios.service.FuncionarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FuncionarioServiceTests {
  Logger logger = LoggerFactory.getLogger(FuncionarioServiceTests.class);
  @Autowired
  FuncionarioService funcionarioService;

  @Test
  @DisplayName("Testar Get")
  public void TesteGetAll() {
    logger.info("Testando GetAll");
    List<Funcionario> funcionarios = funcionarioService.getAll(30);
    assertEquals(30, funcionarios.size());

    logger.info("Testando GetById");
    Funcionario funcionario = funcionarioService.getById(3L);
    assertEquals(3L,funcionario.getId());
  }

  @Test
  @DisplayName("Testar Post")
  public void TesteCreate(){
    logger.info("Testando create com cep inválido");
    Funcionario funcionario1 = Funcionario.builder().id((long)0).nome("Matheus").cpf("09906790639")
                                .endereco(Endereco.builder().cep("0856150").state("state").city("city").neighborhood("neighborhood").street("street").service("service")
                                .location(Location.builder().type("type").coordinates(new Coordinates(00, 00)).build()).build()).build();

                                assertThrows(ResourceNotFoundException.class, () ->
                                        funcionarioService.create(funcionario1));

    logger.info("Testando create com cep válido");
    Funcionario funcionario2 = Funcionario.builder().id((long)0).nome("Matheus").cpf("09906790639")
                                .endereco(Endereco.builder().cep("08561100").state("state").city("city").neighborhood("neighborhood").street("street").service("service")
                                .location(Location.builder().type("type").coordinates(new Coordinates(00, 00)).build()).build()).build();

                                funcionarioService.create(funcionario2);
                                assertEquals("Rua Dante Fuga",funcionario2.getEndereco().getStreet());
  }

  @Test
  @DisplayName("Testar Delete")
  public void TestDeleteById(){
    logger.info("Testando retorno de funcionario inexistente");
    assertThrows(ResourceNotFoundException.class,() ->
           funcionarioService.deleteById(150L) );
  }
}
