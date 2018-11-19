package com.entrega.controllers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.junit.Test;

import com.entrega.entidades.Revision;
import com.entrega.entidades.Trabajo;
import com.entrega.entidades.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;




public class TestRestInterfaceTrabajo {

	public final String BASE_URL="http://localhost:8080/EntregaArquitectura/api";

	public final HttpClient client = HttpClientBuilder.create().build();

	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
		crearTema("Java",true);
		crearTema("JavaScript",false);
		crearTema("Eclipse",true);
		crearTema("Phyton",false);
		crearTema("MongoDB",false);
		crearTema("Nodejs",true);
		crearUsuario(1,"Maximiliano","Guerra","Municipalidad de Tandil",1);
		crearUsuario(2,"Arturo","Garcia Reinhold","Infor",2);
		crearUsuario(3,"Carlos","Cabrera Gentille","Beereal",3);
		crearUsuario(4,"Juan Martin","Del Portro","Ikea",3);
		crearUsuario(5,"Novac","Djocovic","Canon",2);
		crearUsuario(6,"Roger","Federer","Lego",1);
		crearTrabajo(1,1,"JavaScript");
		crearTrabajo(2,2,"Desarrollo IA");
		crearTrabajo(4,3,"Junit");
		getTrabajo();
		listarTrabajos();
		crearRevsiones(1,1);
		comprobarUsuarioyTrabajoReview(1,1);
		findTrabajosByUsuario(1);
		updateUsuario();
//		crearRevsiones(2,5);
//		crearRevsiones(3,6);
	}

	public void crearTema(String name,boolean general) throws ClientProtocolException, IOException {
		String url = BASE_URL + "/tema";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("name", name);
		jsonObject.put("temaGeneral", general);
		String jsonString = jsonObject.toString();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
	}
	public void crearUsuario(int id,String name,String apellido,String lugarDeTrabajo,int tema) throws ClientProtocolException, IOException {
		int tema2=tema+1;
		String tema1Url= BASE_URL + "/tema/"+tema;
		String tema2Url= BASE_URL + "/tema/"+tema2;
		HttpGet request = new HttpGet(tema1Url);
		HttpResponse response1 = client.execute(request);
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContent1 = getResultContent(response1);
		String resultContent2 = getResultContent(response2);
		String url = BASE_URL + "/usuario";
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("nombre", name);
		jsonObject.put("apellido", apellido);
		jsonObject.put("lugarDeTrabajo",lugarDeTrabajo);
		ArrayNode temas = jsonObject.putArray("temasConocimiento");
		temas.addPOJO(resultContent1);
		temas.addPOJO(resultContent2);
		jsonObject.putPOJO("temasConocimiento", temas); 
		String jsonString = jsonObject.toString();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
	}
	public void crearTrabajo(int idTema,int idUsuario,String nombreTrabajo) throws ClientProtocolException, IOException {
		int idTema2=idTema+1;
		String tema1Url= BASE_URL + "/tema/"+idTema;
		String tema2Url= BASE_URL + "/tema/"+idTema2;
		HttpGet request = new HttpGet(tema1Url);
		HttpResponse response1 = client.execute(request);
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContent1 = getResultContent(response1);
		String resultContent2 = getResultContent(response2);

		String userUrl= BASE_URL + "/usuario/"+idUsuario;
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response3 = client.execute(request3);
		String resultContent3 = getResultContent(response3);

		String url = BASE_URL + "/trabajos";

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", nombreTrabajo);
		ArrayNode temas = jsonObject.putArray("temasConocimiento");
		temas.addPOJO(resultContent1);
		temas.addPOJO(resultContent2);
		jsonObject.putPOJO("temasConocimiento", temas); 
		ArrayNode autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContent3);
		jsonObject.putPOJO("autores", autores);
		String jsonString = jsonObject.toString();
		System.out.println("trabajo :"+nombreTrabajo);
		System.out.println(jsonString);
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}
	public void crearRevsiones(int idTrabajo,int idUsuario)  throws ClientProtocolException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String trabajo1Url= BASE_URL + "/trabajos/"+idTrabajo;
		HttpGet request = new HttpGet(trabajo1Url);
		HttpResponse response1 = client.execute(request);
		String resultContent1 = getResultContent(response1);

		String userUrl= BASE_URL + "/usuario/"+idUsuario;
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response3 = client.execute(request3);
		String resultContent3 = getResultContent(response3);
		Usuario user=mapper.readValue(resultContent3, Usuario.class);
		Trabajo work=mapper.readValue(resultContent1, Trabajo.class);
		boolean aceptReview=work.aceptaRevision(user);
//		System.out.println(user.getTemasConocimiento());
//		System.out.println(work.getTemasConocimiento());
		if(aceptReview) {
			String url = BASE_URL + "/revision";

			ObjectNode jsonObject = mapper.createObjectNode();
//			jsonObject.putPOJO("evaluador", resultContent3);
//			jsonObject.putPOJO("trabajo", resultContent1);
			Revision review=new Revision();
			review.setEvaluador(user);
			review.setTrabajo(work);
			
			String jsonString = mapper.writeValueAsString(review);
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
			HttpResponse response = client.execute(post);

			System.out.println("\nPOST "+url);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			String resultContent = getResultContent(response);
			System.out.println("Response Content : " + resultContent);
		}else {
			System.out.println("No Acepta Revision");
		}
	}
	public void findTrabajosByUsuario(int id)  throws ClientProtocolException, IOException {
		String url = BASE_URL +"/usuario/trabajo/"+id;
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		String resultContent = getResultContent(response);
		System.out.println("entre");
		System.out.println(resultContent);
		
	}
	private String getResultContent(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}else {
			return "";
		}
	}

	public void comprobarUsuarioyTrabajoReview(int usuario,int trabajo) throws ClientProtocolException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String trabajo1Url= BASE_URL + "/trabajos/"+trabajo;
		HttpGet request = new HttpGet(trabajo1Url);
		HttpResponse response1 = client.execute(request);
		String resultContent1 = getResultContent(response1);

		String userUrl= BASE_URL + "/usuario/"+usuario;
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response3 = client.execute(request3);
		String resultContent3 = getResultContent(response3);
		Usuario user=mapper.readValue(resultContent3, Usuario.class);
		Trabajo work=mapper.readValue(resultContent1, Trabajo.class);
		assertTrue(user.getReview().size()!=0);
		assertTrue(work.getRevisiones().size()!=0);
		System.out.println("hice los assert");
	}
	public void getTrabajo() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos/1";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void updateUsuario() throws ClientProtocolException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Pepe");
		jsonObject.put("apellido", "Pepino");
		jsonObject.put("lugarDeTrabajo", "Pepineria");
		jsonObject.put("expert", false);
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/usuario/5";
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}


	public void listarTrabajos() throws ClientProtocolException, IOException {
		System.out.println("entre en el metodo del test");

		String url = BASE_URL + "/trabajos";

		HttpGet request = new HttpGet(url);

		System.out.println(request);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	//	public void updatePerro() throws ClientProtocolException, IOException {
	//
	//		ObjectMapper mapper = new ObjectMapper();
	//		ObjectNode jsonObject = mapper.createObjectNode();
	//		jsonObject.put("nombre", "Roque");
	//		jsonObject.put("raza", "Callejero");
	//		jsonObject.put("edad", 8);
	//		String jsonString = jsonObject.toString();
	//
	//		String url = BASE_URL + "/perros/1";
	//		HttpPut request = new HttpPut(url);
	//		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
	//		HttpResponse response = client.execute(request);
	//
	//		System.out.println("\nPUT "+url);
	//		
	//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
	//
	//		String resultContent = getResultContent(response);
	//
	//		System.out.println("Response Content : " + resultContent);
	//
	//	}
	//	
	//	public void deletePerro() throws ClientProtocolException, IOException {
	//
	//		String url = BASE_URL + "/perros/2";
	//		
	//		HttpDelete request = new HttpDelete(url);
	//		
	//		HttpResponse response = client.execute(request);
	//		
	//		System.out.println("\nDELETE "+url);
	//
	//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
	//
	//		String resultContent = getResultContent(response);
	//
	//		System.out.println("Response Content : " + resultContent);
	//
	//	}
	//	
	//	private void findPerrosByEdad() throws ClientProtocolException, IOException {
	//		
	//		String url = BASE_URL + "/perros/findPerrosByEdad?from=3&to=5";
	//
	//		HttpGet request = new HttpGet(url);
	//
	//		HttpResponse response = client.execute(request);
	//
	//		System.out.println("\nGET "+url);
	//		
	//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
	//
	//		String resultContent = getResultContent(response);
	//
	//		System.out.println("Response Content : " + resultContent);
	//		
	//	}

}

