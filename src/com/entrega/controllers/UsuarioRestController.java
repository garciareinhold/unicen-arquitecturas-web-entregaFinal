package com.entrega.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.entrega.dao.UsuarioDAO;
import com.entrega.entidades.Usuario;

@Path("/usuario")
public class UsuarioRestController {


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario user) {
		Usuario result= UsuarioDAO.getInstance().persist(user);
		if(result==null) {
			throw new RecursoDuplicado(user.getDni());
		}else {
			return Response.status(201).entity(user).build();
		}
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuarioById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario user = UsuarioDAO.getInstance().findById(id);
		if(user!=null) {
			return user;
		}else
			throw new RecursoNoExiste(id);
	}
	public class RecursoDuplicado extends WebApplicationException {
		public RecursoDuplicado(int id) {
			super(Response.status(Response.Status.CONFLICT)
					.entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
		}
	}

	public class RecursoNoExiste extends WebApplicationException {
		public RecursoNoExiste(int id) {
			super(Response.status(Response.Status.NOT_FOUND)
					.entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
		}
	}
}
