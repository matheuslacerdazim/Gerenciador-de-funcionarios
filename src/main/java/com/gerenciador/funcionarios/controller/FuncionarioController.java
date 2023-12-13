package com.gerenciador.funcionarios.controller;

import com.gerenciador.funcionarios.exception.ResourceNotFoundException;
import com.gerenciador.funcionarios.model.Funcionario;
import com.gerenciador.funcionarios.payload.ResponsePayLoad;
import com.gerenciador.funcionarios.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity getAll(@RequestParam (required = false, defaultValue = "10") Integer size,
                                    @RequestParam (required = false, defaultValue = "") String sort,
                                    @RequestParam (required = false, defaultValue = "") String order
                                    ){
        try {
            List<Funcionario> funcionarios = funcionarioService.getAll(size,sort,order);
            return ResponseEntity.ok(funcionarios);
        }catch(ResourceNotFoundException ex){
            ResponsePayLoad responsePayLoad = new ResponsePayLoad(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayLoad);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Funcionario funcionario = funcionarioService.getById(id);
            return ResponseEntity.ok(funcionario);
        } catch (ResourceNotFoundException ex) {
            ResponsePayLoad responsePayLoad = new ResponsePayLoad(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayLoad);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Funcionario funcionario){
        try{
            funcionarioService.create(funcionario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(ResourceNotFoundException ex){
            ResponsePayLoad responsePayLoad = new ResponsePayLoad(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayLoad);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Funcionario funcionario){
        try {
            funcionarioService.update(id, funcionario);
            return ResponseEntity.ok().build();
        }catch(ResourceNotFoundException ex){
            ResponsePayLoad responsePayLoad = new ResponsePayLoad(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayLoad);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        try {
            Funcionario removed = funcionarioService.deleteById(id);
            return ResponseEntity.ok(removed);
        } catch(ResourceNotFoundException ex){
            ResponsePayLoad responsePayLoad = new ResponsePayLoad(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayLoad);

        }
    }
}
