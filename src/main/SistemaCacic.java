package main;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.entrega.entidades.Revision;
import com.entrega.entidades.Tema;
import com.entrega.entidades.Trabajo;
import com.entrega.entidades.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SistemaCacic {
	private static  final String BASE_URL="http://localhost:8080/EntregaArquitectura/api";
	private static HttpClient client = HttpClientBuilder.create().build();
	
	public  static void resetClient() {
		SistemaCacic.client=HttpClientBuilder.create().build();
	}
	public static void crearTema(String name,boolean general) throws ClientProtocolException, IOException {
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
	public  static void addParticipante(int dni,String name,String apellido,String lugarDeTrabajo,int tema) throws ClientProtocolException, IOException {
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
		jsonObject.put("id", dni);
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
	
	public static void crearTrabajo(int idTema,int idUsuario,String nombreTrabajo) throws ClientProtocolException, IOException {
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
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

	}
	
	public static void crearRevsiones(int idTrabajo,int idUsuario)  throws ClientProtocolException, IOException {
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

		if(aceptReview) {
			String url = BASE_URL + "/revision";

			ObjectNode jsonObject = mapper.createObjectNode();

			Revision review=new Revision();
			review.setEvaluador(user);
			review.setTrabajo(work);
			Calendar fechaRevision= new GregorianCalendar(2011, 0, 31);
			review.setFechaRevision(fechaRevision);

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
	
	public static List <Trabajo> findTrabajosByUsuario(int id)  throws ClientProtocolException, IOException {
		String url = BASE_URL +"/usuario/trabajo/"+id;
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		String resultContent = getResultContent(response);

		
		return readResult(resultContent);

	}
	
	/**
	 * @param usuario
	 * @param trabajo
	 * @throws ClientProtocolException
	 * @throws IOException
	 * comprobamos si efectivamente se genero la review y tanto el trabajo como el usuario contienen la misma
	 */
	public static void comprobarUsuarioyTrabajoReview(int usuario,int trabajo) throws ClientProtocolException, IOException {
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
	
	public static Trabajo getTrabajo(int id) throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos/"+id;

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(resultContent, Trabajo.class);
		
	

	}
	
	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * update de usuario
	 */
	public static Usuario updateUsuario(int id,String name,String apellido,String lugarDeTrabajo) throws ClientProtocolException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", name);
		jsonObject.put("apellido", apellido);
		jsonObject.put("lugarDeTrabajo", lugarDeTrabajo);
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/usuario/"+id;
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		String userUrl= BASE_URL + "/usuario/"+id;
		HttpGet request3 = new HttpGet(userUrl);
		HttpResponse response1 = client.execute(request3);
		String resultContent1 = getResultContent(response1);
		
		return mapper.readValue(resultContent1, Usuario.class);

	}
	
	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos todos los trabajos
	 */
	public static List <Trabajo> getTrabajos() throws ClientProtocolException, IOException {
		System.out.println("entre en el metodo del test");

		String url = BASE_URL + "/trabajos";

		HttpGet request = new HttpGet(url);

		System.out.println(request);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		return readResult(resultContent);

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
	public static List<Trabajo> getTrabajosRevisadosPorUsuario(int id)throws ClientProtocolException, IOException{
		String url = BASE_URL + "/usuario/"+id+"/revisados";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		return readResult(resultContent);
	}
	
	private static List<Trabajo> readResult(String resultContent) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(resultContent, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Trabajo.class));
	}
	
	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos revisiones de un usuario en rangos de fechas
	 */
	public static List<Revision> getRevisionesPorFecha(int id,String desde,String hasta) throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuario/"+id+"/"+desde+"/"+hasta;

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(resultContent, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Revision.class));

	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * traemos los trabajos por un usuario determinado y un tema
	 */
	public static List<Trabajo> getTrabajosByUserAndTema(int id,String tema) throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuario/"+id+"/trabajos/"+tema;

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

		return readResult(resultContent);

	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * Este metodo trae los trabajos revisados por un usuario en un determinado tema
	 */
	public static List<Trabajo> getTrabajosReviewByUserAndTema(int id ,String tema) throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos/"+id+"/trabajos/"+tema;

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		return readResult(resultContent);
	}
	public static List<Usuario> getAllUser() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuario";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

		ObjectMapper mapper = new ObjectMapper();
		List <Usuario> usuarios=mapper.readValue(resultContent, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Usuario.class));
	
		return usuarios;
		

	}
	public static List <Revision> getAllReview() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/revision";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

		ObjectMapper mapper = new ObjectMapper();

		List <Revision> revisiones=mapper.readValue(resultContent, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Revision.class));
		
		return revisiones;

	}
	
	public static List <Tema>  getAllTema() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/tema";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
		
		ObjectMapper mapper = new ObjectMapper();
		
		List <Tema> temas=mapper.readValue(resultContent, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Tema.class));
		
		return temas;

	}


	private static String getResultContent(HttpResponse response) throws IOException {
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
}
