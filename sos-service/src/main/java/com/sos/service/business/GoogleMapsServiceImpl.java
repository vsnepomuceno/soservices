package com.sos.service.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.poi.util.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sos.entities.Endereco;

@Service
public class GoogleMapsServiceImpl implements GoogleMapsService{

	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";
	
	private String getJSONByGoogle(String endereco) throws IOException {

		URL url = new URL(URL + "?address=" + URLEncoder.encode(endereco, "UTF-8")+ "&sensor=true");

		URLConnection conn = url.openConnection();
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		IOUtils.copy(conn.getInputStream(), output);
		output.close();

		return output.toString();
	}
	
	@Override
	public void configurarLatLongEndereco(Endereco endereco){
		JSONObject jsonResult;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(endereco.getLogradouro());
			sb.append(", ");
			sb.append(endereco.getNumero());
			sb.append(", ");
			sb.append(endereco.getCep());
			jsonResult = new JSONObject(getJSONByGoogle(sb.toString()));
			JSONArray jsonArrayResult = jsonResult.getJSONArray("results");
			
			if(jsonArrayResult != null && jsonArrayResult.length() > 0){
				JSONObject jsonObject = (JSONObject) jsonArrayResult.get(0);
				JSONObject jsonGeometry = jsonObject.getJSONObject("geometry");
				JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
				endereco.setLatitude(jsonLocation.getDouble("lat"));
				endereco.setLongitude(jsonLocation.getDouble("lng"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}