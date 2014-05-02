package com.sos.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class PrestadorAPITest {

	@Test
	public void cadastrarPrestador() throws IOException{
		String url = "http://localhost:8080/sos-api/prestador";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.addRequestProperty("Content-Type", "application/json");
 
		String urlParameters = "{\"nome\":\"Diogo Peixoto\",\"email\":\"peixotoo@gmail.com\", \"senha\": \"12345\", \"cpf\":\"073.758.824-10\","
				+ "\"logradouro\": \"Rua José Bonifácio\", \"numero\": 169, \"complemento\": \"Apartamento 401\", \"cep\": \"50710-435\","
				+ "\"telefone\": \"(81) 8777-1988\"}";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		System.out.println(response.toString());
	}
	
	@Test
	public void deletarPrestador() {
		try{
			String url = "http://localhost:8080/sos-api/prestador/2";
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("DELETE");
			int responseCode = connection.getResponseCode();

			System.out.println("\nSending 'DELETE' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void editarPrestador() throws IOException{
		String url = "http://localhost:8080/sos-api/prestador/2";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("PUT");
		con.addRequestProperty("Content-Type", "application/json");
 
		String urlParameters = "{\"nome\":\"Diogo Peixoto\",\"email\":\"diogopeixoto@hotmail.com\", \"senha\": \"12345\", "
				+ "\"cpf\":\"073.758.824-10\","
				+ "\"logradouro\": \"Rua José Bonifácio\", \"numero\": 154, \"complemento\": \"Apartamento 303\", \"cep\": \"50710-435\","
				+ "\"telefone\": \"(81) 8777-1988\"}";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		System.out.println(response.toString());
	}
}