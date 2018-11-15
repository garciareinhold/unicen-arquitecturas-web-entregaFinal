package com.entrega.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
		
//		listarTrabajos();
		crearTemas();
		crearUsuarios();
		crearTrabajos();
		crearRevsiones();
	}
	
	public void crearTemas() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/tema";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Java");
		jsonObject.put("temaGeneral", false);
		String jsonString = jsonObject.toString();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "JavaScript");
		jsonObject.put("temaGeneral", false);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Eclipse");
		jsonObject.put("temaGeneral", false);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Python");
		jsonObject.put("temaGeneral", true);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Atom");
		jsonObject.put("temaGeneral", false);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}
	public void crearUsuarios() throws ClientProtocolException, IOException {
		String tema1Url= BASE_URL + "/tema/JavaScript";
		String tema2Url= BASE_URL + "/tema/Java";
		HttpGet request = new HttpGet(tema1Url);
		HttpResponse response1 = client.execute(request);
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContent1 = getResultContent(response1);
		String resultContent2 = getResultContent(response2);
		
		String url = BASE_URL + "/usuario";
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("dni", 1);
		jsonObject.put("nombre", "Maximiliano");
		jsonObject.put("apellido", "Guerra");
		jsonObject.put("lugarDeTrabajo","Edsa");
		ArrayNode temas = jsonObject.putArray("temasConocimiento");
		temas.addPOJO(resultContent1);
		temas.addPOJO(resultContent2);
		jsonObject.putPOJO("temasConocimiento", temas); 
		String jsonString = jsonObject.toString();
		System.out.println(jsonString);
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		ObjectNode jsonObject2 = mapper.createObjectNode();
		jsonObject2.put("dni", 2);
		jsonObject2.put("nombre", "Arturo");
		jsonObject2.put("apellido", "Garcia Reinhold");
		jsonObject2.put("lugarDeTrabajo","Infor");

		jsonString = jsonObject2.toString();
		System.out.println(jsonString);
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		
		ObjectNode jsonObject3 = mapper.createObjectNode();
		jsonObject3.put("dni", 3);
		jsonObject3.put("nombre", "Carlos");
		jsonObject3.put("apellido", "Cabrera");
		jsonObject3.put("lugarDeTrabajo","Beereal");

		jsonString = jsonObject3.toString();
		System.out.println(jsonString);
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		ObjectNode jsonObject4 = mapper.createObjectNode();
		jsonObject4.put("dni", 4);
		jsonObject4.put("nombre", "Juan Martin");
		jsonObject4.put("apellido", "Del Potro");
		jsonObject4.put("lugarDeTrabajo","IKEA");

		jsonString = jsonObject4.toString();
		System.out.println(jsonString);
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		ObjectNode jsonObject5 = mapper.createObjectNode();
		jsonObject5.put("dni", 5);
		jsonObject5.put("nombre", "Novac");
		jsonObject5.put("apellido", "Djocovic");
		jsonObject5.put("lugarDeTrabajo","Canon");

		jsonString = jsonObject5.toString();
		System.out.println(jsonString);
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		ObjectNode jsonObject6 = mapper.createObjectNode();
		jsonObject6.put("dni", 6);
		jsonObject6.put("nombre", "Roger");
		jsonObject6.put("apellido", "Federer");
		jsonObject6.put("lugarDeTrabajo","Lego");

		jsonString = jsonObject6.toString();
		System.out.println(jsonString);
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
	}
	public void crearTrabajos() throws ClientProtocolException, IOException {
		String tema1Url= BASE_URL + "/tema/JavaScript";
		String tema2Url= BASE_URL + "/tema/Java";
		HttpGet request = new HttpGet(tema1Url);
		HttpResponse response1 = client.execute(request);
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContent1 = getResultContent(response1);
		String resultContent2 = getResultContent(response2);
		
		String userUrl= BASE_URL + "/usuario/3";
		String user2Url= BASE_URL + "/usuario/4";
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response3 = client.execute(request3);
		HttpGet request4 = new HttpGet(user2Url);
		HttpResponse response4 = client.execute(request4);
		String resultContent3 = getResultContent(response3);
		String resultContent4 = getResultContent(response4);
		
		String url = BASE_URL + "/trabajos";
		//post 1er Trabajo
		ObjectMapper mapper = new ObjectMapper();
		
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Javascript");
		ArrayNode temas = jsonObject.putArray("temasConocimiento");
		temas.addPOJO(resultContent1);
		temas.addPOJO(resultContent2);
		jsonObject.putPOJO("temasConocimiento", temas); 
		ArrayNode autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContent3);
		autores.addPOJO(resultContent4);
		System.out.println(resultContent3);
		System.out.println(resultContent4);
		jsonObject.putPOJO("autores", autores);
		String jsonString = jsonObject.toString();
		System.out.println(jsonString);
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		//post 2do Trabajo
		ObjectNode jsonObject2 = mapper.createObjectNode();
		jsonObject2.put("nombre", "Desarrollo AI");
//		ArrayNode temas2 = jsonObject2.putArray("temasConocimiento");
//		temas2.addPOJO(resultContent1);
//		temas2.addPOJO(resultContent2);
//		jsonObject.putPOJO("temasConocimiento", temas); 
//		ArrayNode autores2 = jsonObject2.putArray("autores");
//		autores2.addPOJO(resultContent3);
//		autores2.addPOJO(resultContent4);
		jsonString = jsonObject2.toString();
		
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		//post 3er trabajo
		
		ObjectNode jsonObject3 = mapper.createObjectNode();
		jsonObject3.put("nombre", "Angular");
//		ArrayNode temas2 = jsonObject2.putArray("temasConocimiento");
//		temas2.addPOJO(resultContent1);
//		temas2.addPOJO(resultContent2);
//		jsonObject.putPOJO("temasConocimiento", temas); 
//		ArrayNode autores2 = jsonObject2.putArray("autores");
//		autores2.addPOJO(resultContent3);
//		autores2.addPOJO(resultContent4);
		jsonString = jsonObject3.toString();
		
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}
	public void crearRevsiones()  throws ClientProtocolException, IOException {
		String trabajo1Url= BASE_URL + "/trabajos/6";
		String trabajo2Url= BASE_URL + "/trabajos/7";
		HttpGet request = new HttpGet(trabajo1Url);
		HttpResponse response1 = client.execute(request);
		HttpGet request2 = new HttpGet(trabajo2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContent1 = getResultContent(response1);
		String resultContent2 = getResultContent(response2);
		
		String userUrl= BASE_URL + "/usuario/3";
		String user2Url= BASE_URL + "/usuario/2";
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response3 = client.execute(request3);
		HttpGet request4 = new HttpGet(user2Url);
		HttpResponse response4 = client.execute(request4);
		String resultContent3 = getResultContent(response3);
		String resultContent4 = getResultContent(response4);
		
		String url = BASE_URL + "/revision";
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		Usuario user= mapper.readValue(resultContent3, Usuario.class);
		Trabajo work=mapper.readValue(resultContent1, Trabajo.class);
		
		Revision review=new Revision();
		review.setEvaluador(user);
		review.setTrabajo(work);
		user.addRevision(review);
		work.addReview(review);
		String jsonString = mapper.writeValueAsString(review);
		System.out.println(jsonString);
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
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

	
	public void getTrabajo() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos/3";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

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

