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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



public class TestRestInterfaceTrabajo {

	public final String BASE_URL="http://localhost:8080/EntregaArquitectura/api";

	public final HttpClient client = HttpClientBuilder.create().build();

	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
//		crearTrabajos();
//		listarTrabajos();
		crearTemas();
		crearUsuarios();
	}
	
	public void crearTemas() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/tema";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Java");
		jsonObject.put("esGeneral", false);
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
		jsonObject.put("esGeneral", false);
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
		jsonObject.put("esGeneral", false);
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
		jsonObject.put("esGeneral", true);
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
		jsonObject.put("esGeneral", false);
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
		System.out.println(resultContent1);
		System.out.println(resultContent2);
		String url = BASE_URL + "/usuario";
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		ObjectNode jsonObject2 = mapper.createObjectNode();
		jsonObject2.put("Tema", resultContent1);
		jsonObject.put("dni", 1);
		jsonObject.put("nombre", "Maximiliano");
		jsonObject.put("apellido", "Guerra");
		jsonObject.put("lugarDeTrabajo","Edsa");
		jsonObject.put("esExperto",false);
		jsonObject.put("temasConocimiento","");
		String jsonString = jsonObject.toString();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);
//
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//		String resultContent = getResultContent(response);
//		System.out.println("Response Content : " + resultContent);
//
//		jsonObject = mapper.createObjectNode();
//		jsonObject.put("name", "JavaScript");
//		jsonObject.put("esGeneral", false);
//		jsonString = jsonObject.toString();
//
//		post = new HttpPost(url);
//		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
//		response = client.execute(post);
//
//		System.out.println("\nPOST "+url);
//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//		resultContent = getResultContent(response);
//		System.out.println("Response Content : " + resultContent);
	}
	public void crearTrabajos() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Javascript");

		String jsonString = jsonObject.toString();

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

