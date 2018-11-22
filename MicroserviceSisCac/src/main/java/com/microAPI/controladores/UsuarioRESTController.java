package com.microAPI.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microAPI.entidades.Trabajo;
import com.microAPI.entidades.Usuario;
import com.microAPI.servicios.UsuarioDAO;


@RestController
@RequestMapping("/usuarios")
public class UsuarioRESTController {
	
	@Autowired
	private UsuarioDAO daoUsuario;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody	
	public List<Usuario> getAllUser() {
		return daoUsuario.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Usuario getById (@PathVariable("id") int id) {
        return daoUsuario.encontrarPorId(id);
    }
		
	@RequestMapping(value = "/trabajo/{id}", method = RequestMethod.GET)
	@ResponseBody	
	public List<Trabajo> getTrabajosDeUnAutor(@PathVariable("id") int id) {
        return daoUsuario.findWorksByIdUser(id); 
	}
	
}