package com.entrega.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entrega.entidades.Trabajo;
import com.entrega.dao.TrabajoDAO;

@Path("/trabajos")
public class TrabajoRestController {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> getAllTrabajo() {
		System.out.println("entre en get all trabajo");
		return TrabajoDAO.getInstance().findAll();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Trabajo getTrabajoById(@PathParam("id") String msg) {

		int id = Integer.valueOf(msg);
		Trabajo work = TrabajoDAO.getInstance().findById(id);
		if(work!=null)
			return work;
		else
			throw new RecursoNoExiste(id);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTrabajo(Trabajo trabajo) {

		Trabajo result= TrabajoDAO.getInstance().persist(trabajo);
		if(result==null) {
			throw new RecursoDuplicado(trabajo.getId());
		}else {
			return Response.status(201).entity(trabajo).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTrabajo(@PathParam("id") int id) {

		boolean foundWork=  TrabajoDAO.getInstance().delete(id);
		if(foundWork) {
			return Response.status(200).build();
		}
		else {
			throw new RecursoNoExiste(id);
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTrabajo(@PathParam("id") int id,Trabajo work) {
		throw new UnsupportedOperationException();
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