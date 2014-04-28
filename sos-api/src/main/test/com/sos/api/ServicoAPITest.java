package com.sos.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class ServicoAPITest {

	@Test
	public void cadastrarServico() throws IOException{
		String url = "http://localhost:8080/sos-api/servico";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.addRequestProperty("Content-Type", "application/json");
 
		String urlParameters = "{\"descricao\":\"Fazendo um teste.\",\"valor\":25.00, \"id_tipo_servico\": 3}";
 
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
	public void deletarServico() {
		try{
			String url = "http://localhost:8080/sos-api/servico/1";
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
	public void editarServico() throws IOException{
		String url = "http://localhost:8080/sos-api/servico/1";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("PUT");
		con.addRequestProperty("Content-Type", "application/json");
 
		String urlParameters = "{\"descricao\":\"Fazendo um teste.\",\"valor\":100.00, \"id_tipo_servico\": 4}";
 
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