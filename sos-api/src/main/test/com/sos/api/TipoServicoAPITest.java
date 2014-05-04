package com.sos.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class TipoServicoAPITest {

	@Test
	public void cadastrarTipoServico() throws IOException{
		String url = "http://localhost:8080/sos-api/tipo-servico";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		
		String urlParameters = "{\"nome\":\"Ájuda Dê ;. %$\",\"valorado\":false}";
 
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
	public void deletarTipoServico() {
		try{
			String url = "http://localhost:8080/sos-api/tipo-servico/1";
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
	public void editarTipoServico() throws IOException{
		String url = "http://localhost:8080/sos-api/tipo-servico/1";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("PUT");
		con.addRequestProperty("Content-Type", "application/json");
 
		String urlParameters = "{\"nome\":\"Eletricista\",\"valorado\":true}";
 
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