package com.entrega.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entrega.controllers.TemaRestController.RecursoDuplicado;
import com.entrega.controllers.TemaRestController.RecursoNoExistente;
import com.entrega.dao.RevisionDAO;
import com.entrega.dao.UsuarioDAO;
import com.entrega.entidades.Revision;
import com.entrega.entidades.Usuario;

@Path("/revision") 
public class RevisionRestController {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReview(Revision review) {

		Revision result= RevisionDAO.getInstance().persist(review);
		if(result==null) {
			throw new RecursoDuplicado(review.getId());
		}else {
			return Response.status(201).entity(review).build();
		}
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Revision> getUsuarioById() {
		return RevisionDAO.getInstance().findAll();
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
