package com.entrega.controllers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.entrega.entidades.Revision;
import com.entrega.entidades.Tema;
import com.entrega.entidades.Trabajo;
import com.entrega.entidades.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import main.SistemaCacic;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRestInterfaceTrabajo {

	public final String BASE_URL="http://localhost:8080/EntregaArquitectura/api";

	public final HttpClient client = HttpClientBuilder.create().build();

//	@Test
//	public void testRESTInterface() throws ClientProtocolException, IOException {
//		crearTemas();
//		crearUsuarios();
//		crearTrabajos();
//		getTrabajo();
//		crearRevsiones();
//		findTrabajosByUsuario(2);
//		updateUsuario();
//		getRevisionesPorFecha();
//		getTrabajosRevisadosPorUsuario();
//		getTrabajosByUserAndTema();
//		getTrabajosReviewByUserAndTema();
//	}
	@Test
	public void test01_crearTemas() throws ClientProtocolException, IOException {
		SistemaCacic.crearTema("Java",false);
		SistemaCacic.crearTema("JavaScript",false);
		SistemaCacic.crearTema("Eclipse",false);
		SistemaCacic.crearTema("Phyton",false);
		SistemaCacic.crearTema("MongoDB",false);
		SistemaCacic.crearTema("Nodejs",true);
		SistemaCacic.crearTema("C++",false);
		SistemaCacic.crearTema("COBOL",true);
		SistemaCacic.crearTema("Basic",false);
		SistemaCacic.crearTema("FOTRAN",true);
		List<Tema>temas=SistemaCacic.getAllTema();
		System.out.println("cantidad de trabajos creados = "+temas.size());
		assertTrue(temas.size()==10);
		SistemaCacic.resetClient();
	}
	@Test
	public void test02_crearUsuarios() throws ClientProtocolException, IOException {
		SistemaCacic.addParticipante(1,"Maximiliano","Guerra","Municipalidad de Tandil",1);
		SistemaCacic.addParticipante(2,"Arturo","Garcia Reinhold","Infor",2);
		SistemaCacic.addParticipante(3,"Carlos","Cabrera Gentille","Beereal",3);
		SistemaCacic.addParticipante(4,"Juan Martin","Del Portro","Ikea",3);
		SistemaCacic.addParticipante(5,"Novac","Djocovic","Canon",2);
		SistemaCacic.addParticipante(6,"Roger","Federer","Lego",4);
		SistemaCacic.addParticipante(7,"Rafael","Nadal","Google",5);
		SistemaCacic.addParticipante(8,"Alexander","Zverev","Toyota",4);
		SistemaCacic.addParticipante(9,"Kevin","Anderson","Movistar",1);
		SistemaCacic.addParticipante(10,"Marin","Cilic","Edsa",8);
		List<Usuario>usuarios=SistemaCacic.getAllUser();
		System.out.println("cantidad de usuarios creados = "+usuarios.size());
		assertTrue(usuarios.size()==10);
		SistemaCacic.resetClient();
	
	}
	@Test
	public void test03_crearTrabajos() throws ClientProtocolException, IOException {
		SistemaCacic.crearTrabajo(1,1,"JavaScript");
		SistemaCacic.crearTrabajo(2,2,"Desarrolo IA");
		SistemaCacic.crearTrabajo(3,3,"Junit");
		SistemaCacic.crearTrabajo(4,4,"Robotica");
		SistemaCacic.crearTrabajo(5,5,"Base de datos");
		SistemaCacic.crearTrabajo(6,6,"JAVA");
		SistemaCacic.crearTrabajo(1,7,"Microservice");
		SistemaCacic.crearTrabajo(7,8,"CloudComputing");
		SistemaCacic.crearTrabajo(3,9,"Jelastic");
		SistemaCacic.crearTrabajo(8,10,"Hibernate");
		List<Trabajo>trabajos=SistemaCacic.getTrabajos();
		System.out.println("cantidad de trabajos creados = "+trabajos.size());
		assertTrue(trabajos.size()==10);
		SistemaCacic.resetClient();

	}
	@Test
	public void test04_crearRevsiones()  throws ClientProtocolException, IOException {
		SistemaCacic.crearRevsiones(1,9);
		SistemaCacic.crearRevsiones(2,5);
		SistemaCacic.crearRevsiones(3,4);
		List<Revision>revisiones=SistemaCacic.getAllReview();
		System.out.println("cantidad de trabajos creados = "+revisiones.size());
		assertTrue(revisiones.size()==3);
		SistemaCacic.resetClient();
	}
	@Test
	public void test05_findTrabajosByUsuario()  throws ClientProtocolException, IOException {
		SistemaCacic.resetClient();
		int id=2;
		List<Trabajo> trabajos=  SistemaCacic.findTrabajosByUsuario(id);
		assertTrue(trabajos.size()==1);
		SistemaCacic.resetClient();
	
	}

	/**
	 * @param usuario
	 * @param trabajo
	 * @throws ClientProtocolException
	 * @throws IOException
	 * comprobamos si efectivamente se genero la review y tanto el trabajo como el usuario contienen la misma
	 */
//	public void comprobarUsuarioyTrabajoReview(int usuario,int trabajo) throws ClientProtocolException, IOException {
//		SistemaCacic.comprobarUsuarioyTrabajoReview(usuario, trabajo);
//	}
	@Test
	public void test06_getTrabajo() throws ClientProtocolException, IOException {
		Trabajo work=SistemaCacic.getTrabajo(1);
		assertEquals(work.getNombre(),"JavaScript");
		SistemaCacic.resetClient();
	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * update de usuario
	 */
	@Test
	public void test10_updateUsuario() throws ClientProtocolException, IOException {

		Usuario user=SistemaCacic.updateUsuario(5,"Pepe","Argento","Renault");
		assertEquals(user.getNombre(), "Pepe");
		assertEquals(user.getApellido(), "Argento");
		assertEquals(user.getLugarDeTrabajo(), "Renault");
		assertTrue(user.getId()==5);
		SistemaCacic.resetClient();
	}
	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos los trbajos revisados por un usuario
	 */
	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void test08_getTrabajosRevisadosPorUsuario()throws ClientProtocolException, IOException{
		List<Trabajo>trabajos=SistemaCacic.getTrabajosRevisadosPorUsuario(5);
		assertTrue(trabajos.size()==1);
		SistemaCacic.resetClient();
	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos revisiones de un usuario en rangos de fechas
	 */
	@Test
	public void test07_getRevisionesPorFecha() throws ClientProtocolException, IOException {

		List<Revision>revisiones= SistemaCacic.getRevisionesPorFecha(9,"1992-01-01","2019-12-21");
		assertTrue(revisiones.size()==1);
		SistemaCacic.resetClient();
	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos trabajos por un usuario determinado y un tema
	 */
	@Test
	public void test07_getTrabajosByUserAndTema() throws ClientProtocolException, IOException {

		List<Trabajo> trabajos= SistemaCacic.getTrabajosByUserAndTema(9,"Eclipse");
		assertTrue(trabajos.size()==1);
		SistemaCacic.resetClient();

	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * Este metodo trae los trabajos revisados por un usuario en un determinado tema
	 */
	@Test
	public void test11_getTrabajosReviewByUserAndTema() throws ClientProtocolException, IOException {
		List<Trabajo> trabajos=SistemaCacic.getTrabajosReviewByUserAndTema(9,"JavaScript");
		assertTrue(trabajos.size()==1);
		SistemaCacic.resetClient();
	}
}

