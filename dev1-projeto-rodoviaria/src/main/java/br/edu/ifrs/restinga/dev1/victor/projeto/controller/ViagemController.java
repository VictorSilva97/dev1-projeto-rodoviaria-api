/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.victor.projeto.controller;

import br.edu.ifrs.restinga.dev1.victor.projeto.erro.NaoEncontradoException;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IMotoristaDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IOnibusDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IUsuarioDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IViagemDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Motorista;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Onibus;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Viagem;
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
public class ViagemController {
    
    @Autowired
    IViagemDAO viagemDAO;
    
    @Autowired
    IUsuarioDAO usuarioDAO;
    
    @Autowired
    IMotoristaDAO motoristaDAO;
    
    @Autowired
    IOnibusDAO onibusDAO;
    
    @RequestMapping(path="/viagens/", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Viagem inserir(@RequestBody Viagem viagem){
        try{
            if(ehViagemValida(viagem)){
                viagem.setUsuario(usuarioDAO.save(viagem.getUsuario()));
                viagem.setMotorista(motoristaDAO.save(viagem.getMotorista()));
                viagem.setOnibus(onibusDAO.save(viagem.getOnibus()));
                
                return viagemDAO.save(viagem);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    }

    @RequestMapping(path="/viagens/", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Viagem> listar(){
        return viagemDAO.findAll();
    }
    
    @RequestMapping(path="/viagens/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Viagem recuperar(@PathVariable int id){
        final Optional<Viagem> viagem = viagemDAO.findById(id);
        if(viagem.isPresent())
            return viagem.get();
        else 
            throw new NaoEncontradoException("ID não encontrado.");
    }
    
    @RequestMapping(path="/viagens/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Viagem atualizar(@PathVariable int id, @RequestBody Viagem viagemAtualizada){
        try{
            if(ehViagemValida(viagemAtualizada)){   
                Viagem viagem = this.recuperar(id);
        
                viagem.setOrigem(viagemAtualizada.getOrigem());
                viagem.setDestino(viagemAtualizada.getDestino());
                viagem.setData(viagemAtualizada.getData());
                viagem.setHoraSaida(viagemAtualizada.getHoraSaida());
                viagem.setHoraChegada(viagemAtualizada.getHoraChegada());
                viagem.setUsuario(usuarioDAO.save(viagemAtualizada.getUsuario()));
                viagem.setMotorista(motoristaDAO.save(viagemAtualizada.getMotorista()));
                viagem.setOnibus(onibusDAO.save(viagemAtualizada.getOnibus()));
                
                return viagemDAO.save(viagem);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    }
    
    @RequestMapping(path="/viagens/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id){
        if(viagemDAO.existsById(id))
            viagemDAO.deleteById(id);
        else
            throw new NaoEncontradoException("ID não encontrado");        
    }    
    
    private boolean ehViagemValida(Viagem viagem) {
        return true;
    }
       
}
