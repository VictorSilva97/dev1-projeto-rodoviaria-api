/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.victor.projeto.controller;
import br.edu.ifrs.restinga.dev1.victor.projeto.erro.NaoEncontradoException;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IOnibusDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Onibus;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/")
@CrossOrigin
public class OnibusController {
    
    @Autowired
    IOnibusDAO onibusDAO;
    
    @RequestMapping(path="/onibus/", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Onibus inserir(@RequestBody Onibus onibus){
        try{
            if(ehOnibusValido(onibus)){                
               return onibusDAO.save(onibus);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    } 
    
    @RequestMapping(path="/onibus/", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Onibus> listar(){
        return onibusDAO.findAll();
    }
    
    @RequestMapping(path="/onibus/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Onibus recuperar(@PathVariable int id){
        final Optional<Onibus> onibus = onibusDAO.findById(id);
        if(onibus.isPresent())
            return onibus.get();
        else 
            throw new NaoEncontradoException("ID não encontrado.");
    }
    
    @RequestMapping(path="/onibus/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Onibus atualizar(@PathVariable int id, @RequestBody Onibus onibusAtualizado){
        try{
            if(ehOnibusValido(onibusAtualizado)){
                Onibus onibus = this.recuperar(id);
        
                onibus.setMarca(onibusAtualizado.getMarca());
                onibus.setModelo(onibusAtualizado.getModelo());
                onibus.setPlaca(onibusAtualizado.getPlaca());

                return onibusDAO.save(onibus);
            }            
        }catch(Exception e){
            throw e;
        }
        return null;        
    }

    @RequestMapping(path="/onibus/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id){
        if(onibusDAO.existsById(id))
            onibusDAO.deleteById(id);
        else
            throw new NaoEncontradoException("ID não encontrado");        
    }    
    
    private boolean ehOnibusValido(Onibus onibus) {
        return true;
    }       
   
}