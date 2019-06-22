/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.victor.projeto.controller;

import br.edu.ifrs.restinga.dev1.victor.projeto.erro.NaoEncontradoException;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.dao.IUsuarioDAO;
import br.edu.ifrs.restinga.dev1.victor.projeto.modelo.entidade.Usuario;
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
public class UsuarioController {
    
    @Autowired
    IUsuarioDAO usuarioDAO;
    
    @RequestMapping(path="/usuario/", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@RequestBody Usuario usuario){
        try{
            if(ehUsuarioValido(usuario)){                
               return usuarioDAO.save(usuario);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    } 

    @RequestMapping(path="/usuario/", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> listar(){
        return usuarioDAO.findAll();
    }
    
    @RequestMapping(path="/usuario/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Usuario recuperar(@PathVariable int id){
        final Optional<Usuario> usuario = usuarioDAO.findById(id);
        if(usuario.isPresent())
            return usuario.get();
        else 
            throw new NaoEncontradoException("ID não encontrado.");
    }
    
    @RequestMapping(path="/usuario/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Usuario atualizar(@PathVariable int id, @RequestBody Usuario usuarioAtualizado){
        try{
            if(ehUsuarioValido(usuarioAtualizado)){   
                Usuario usuario = this.recuperar(id);
        
                usuario.setLogin(usuarioAtualizado.getLogin());
                usuario.setSenha(usuarioAtualizado.getSenha());

                return usuarioDAO.save(usuario);
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    }
    
    @RequestMapping(path="/usuario/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id){
        if(usuarioDAO.existsById(id))
            usuarioDAO.deleteById(id);
        else
            throw new NaoEncontradoException("ID não encontrado");        
    }
    
    private boolean ehUsuarioValido(Usuario usuario) {
        return true;
    }
    
}
