/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.victor.projeto.controller;

import br.edu.ifrs.restinga.dev1.victor.projeto.erro.NaoEncontradoException;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IMotoristaDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Motorista;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Victor
 */
@RestController
@RequestMapping("/api")
public class MotoristaController {
    
    @Autowired
    IMotoristaDAO motoristaDAO;
    
    @RequestMapping(path="/motorista/", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Motorista inserir(@RequestBody Motorista motorista){
        try{
            if(ehMotoristaValido(motorista)){                
               return motoristaDAO.save(motorista);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    } 
    
    @RequestMapping(path="/motorista/", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Motorista> listar(){
        return motoristaDAO.findAll();
    }
    
    @RequestMapping(path="/motorista/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Motorista recuperar(@PathVariable int id){
        final Optional<Motorista> motorista = motoristaDAO.findById(id);
        if(motorista.isPresent())
            return motorista.get();
        else 
            throw new NaoEncontradoException("ID não encontrado.");
    }
    
    @RequestMapping(path="/motorista/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Motorista atualizar(@PathVariable int id, @RequestBody Motorista motoristaAtualizado){
        try{
            if(ehMotoristaValido(motoristaAtualizado)){   
                Motorista motorista = this.recuperar(id);
        
                motorista.setNome(motoristaAtualizado.getNome());
                motorista.setCnh(motoristaAtualizado.getCnh());

                return motoristaDAO.save(motorista);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    }

    @RequestMapping(path="/motorista/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id){
        if(motoristaDAO.existsById(id))
            motoristaDAO.deleteById(id);
        else
            throw new NaoEncontradoException("ID não encontrado");        
    }    
    
    private boolean ehMotoristaValido(Motorista motorista) {
        return true;
    }       
}

