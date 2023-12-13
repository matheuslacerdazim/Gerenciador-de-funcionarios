package com.gerenciador.funcionarios.service;

import com.gerenciador.funcionarios.exception.ResourceNotFoundException;
import com.gerenciador.funcionarios.model.Coordinates;
import com.gerenciador.funcionarios.model.Endereco;
import com.gerenciador.funcionarios.model.Funcionario;
import com.gerenciador.funcionarios.model.Location;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncionarioService {

    @Autowired
    EnderecoService enderecoService;
    Logger logger = LoggerFactory.getLogger(FuncionarioService.class);

    public Map<Long, Funcionario> funcionarios = initFuncionarios();

    private Map<Long, Funcionario> initFuncionarios() {
        Map<Long,Funcionario> funcionarios = new HashMap<Long, Funcionario>();

        for(int i = 1; i <= 30; i++) {
            Faker faker = new Faker();
            Random random = new Random();

            String nome = faker.name().firstName();
            String cpf = faker.number().digits(11);
            String cep = faker.number().digits(8);
            String state = faker.address().state();
            String city = faker.address().city();
            String neighborhood = faker.address().firstName();
            String street = faker.address().streetAddress();
            String service = "Correios";
            String type = "Type 1";
            double latitude = -90 + (90 - (-90)) * random.nextDouble();
            double longitude = -180 + (180 - (-180)) * random.nextDouble();

            Funcionario funcionario = Funcionario.builder().id((long)i).nome(nome).cpf(cpf)
                    .endereco(Endereco.builder().cep(cep).state(state).city(city).neighborhood(neighborhood).street(street).service(service)
                            .location(Location.builder().type(type).coordinates(new Coordinates(latitude, longitude)).build()).build()).build();
            funcionarios.put((long) i, funcionario);

        }
        return funcionarios;
    }
    private Long lastId = 30L;
    public Long IncrementId(){
        this.lastId++;
        return lastId;

    }


    public List<Funcionario> getAll(){
        return funcionarios.values().stream().toList();
    }
    public List<Funcionario> getAll(Integer size) {
        List<Funcionario> list = funcionarios.values().stream().toList();

        if(size > list.size()){
            throw new ResourceNotFoundException("Tamanho solicitado é maior que o tamanho da lista de funcionários");
        }
        return list.subList(0,size);
    }
    public List<Funcionario> getAll(Integer size, String sort, String order) {
        if(sort.equals("")){
            return getAll(size);
        }else {
            List<Funcionario> subsized = getAll(size);

            if(size > subsized.size()){
                throw new ResourceNotFoundException("Tamanho solicitado é maior que o tamanho da lista de funcionários");
            }

            Comparator<Funcionario> comparator = Comparator.comparing(Funcionario::getNome);
            if(order.equals("desc")){
                comparator = comparator.reversed();
            }
            List<Funcionario> list = subsized.stream().sorted(comparator).toList();
            return list;

        }
    }
    public Funcionario getById(Long id) {

        Funcionario funcionario = funcionarios.get(id);

        if(funcionario == null) {
            throw new ResourceNotFoundException("Funcionário inexistente");
        }
        return funcionario;
    }
    public void create(Funcionario funcionario){
        try {
            Long id = IncrementId();
            funcionario.setId(id);
            funcionario.setEndereco(enderecoService.getByCep(funcionario.getEndereco().getCep()));
            logger.info("Status Code: " + HttpStatus.CREATED.value());
            funcionarios.put(id, funcionario);
        }catch (ResourceNotFoundException ex){
            logger.error("Status Code: " + HttpStatus.NOT_FOUND.value(),ex);
            throw ex;
        }
    }
    public void update(Long id, Funcionario funcionario) {

        if (!funcionarios.containsKey(id)){
            throw new ResourceNotFoundException("Funcionário Inexistente");
        }
        try {
            funcionario.setId(id);
            funcionario.setEndereco(enderecoService.getByCep(funcionario.getEndereco().getCep()));
            logger.info("Status Code: " + HttpStatus.CREATED.value());
            funcionarios.put(id, funcionario);
        }catch (ResourceNotFoundException ex){
            logger.error("Status Code: " + HttpStatus.NOT_FOUND.value(),ex);
            throw ex;
        }
    }
    public Funcionario deleteById(Long id) {
        if(!funcionarios.containsKey(id)) throw new ResourceNotFoundException("Cliente Inexistente");
        Funcionario removed = funcionarios.remove(id);
        return removed;
    }

}
