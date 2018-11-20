package com.entrega.controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.entrega.dao.UsuarioDAO;
import com.entrega.entidades.Revision;
import com.entrega.entidades.Trabajo;
import com.entrega.entidades.Usuario;

@Path("/usuario")
public class UsuarioRestController {


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario user) {
		Usuario result= UsuarioDAO.getInstance().persist(user);
		if(result==null) {
			throw new RecursoDuplicado(user.getId());
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
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") int id,Usuario usuario) {
		Usuario result= UsuarioDAO.getInstance().update(id, usuario);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(usuario).build();
		}
	}
	
	
	@GET
	@Path("/trabajo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> getTrabajosByUsuario(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		List<Trabajo>trabajos = UsuarioDAO.getInstance().findTrabajosByUser(id);
		if(trabajos!=null) {
			System.out.println(trabajos);
			return trabajos;
			
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
	
	
	@GET
	@Path("/{id}/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Revision> findPerrosByEdad(@PathParam("id") String msg, @PathParam("from") String dateFrom,
			@PathParam("to") String dateTo) {
		
		int idUser = Integer.valueOf(msg);
		Calendar calendarFrom;
		Calendar calendarTo;
		calendarTo = Calendar.getInstance();
		calendarFrom= Calendar.getInstance();

		System.out.println(idUser);
		System.out.println(dateFrom);
		System.out.println(dateTo);

		try {
			java.util.Date fechaTo= new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
			java.util.Date fechaFrom=new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
			calendarTo.setTime(fechaTo);
			calendarFrom.setTime(fechaFrom); 
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println(calendarFrom);
		System.out.println(calendarTo);

		List<Revision>revisiones = UsuarioDAO.getInstance().findRevisiones(idUser, calendarFrom, calendarTo);
		if(revisiones!=null) {
			System.out.println(revisiones);
			return revisiones;
			
		}else
			throw new RecursoNoExiste(idUser);
	}

}
