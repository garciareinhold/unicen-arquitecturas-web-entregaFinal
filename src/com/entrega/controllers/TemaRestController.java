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

import com.entrega.controllers.TrabajoRestController.RecursoDuplicado;
import com.entrega.controllers.TrabajoRestController.RecursoNoExiste;
import com.entrega.dao.TemaDAO;
import com.entrega.dao.TrabajoDAO;
import com.entrega.entidades.Tema;
import com.entrega.entidades.Trabajo;

@Path("/tema")
public class TemaRestController {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTema(Tema tema) {

		Tema result= TemaDAO.getInstance().persist(tema);
		if(result==null) {
			throw new RecursoDuplicado(tema.getId());
		}else {
			return Response.status(201).entity(tema).build();
		}
	}
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tema getTrabajoById(@PathParam("name") String msg) {

		String nombre = msg;
		Tema tema = TemaDAO.getInstance().findByName(nombre);
		if(tema!=null)
			return tema;
		else
			throw new RecursoNoExistente(nombre);
	}
	public class RecursoDuplicado extends WebApplicationException {
	     public RecursoDuplicado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	public class RecursoNoExistente extends WebApplicationException {
	     public RecursoNoExistente(String name) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con el nombre "+name+" no existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
}
