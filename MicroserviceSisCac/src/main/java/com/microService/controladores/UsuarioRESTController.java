package com.microService.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microService.entidades.Trabajo;
import com.microService.entidades.Usuario;
import com.microService.servicios.UsuarioDAO;


@RestController
@RequestMapping("/usuarios")
public class UsuarioRESTController {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody	
	public List<Usuario> getAllUser() {
		return usuarioDao.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Usuario getById (@PathVariable("id") int id) {
        return usuarioDao.findById(id);
    }
		
	@RequestMapping(value = "/trabajo/{id}", method = RequestMethod.GET)
	@ResponseBody	
	public List<Trabajo> getTrabajosDeUnAutor(@PathVariable("id") int id) {
        return usuarioDao.findWorksByIdUser(id); 
	}
	
}