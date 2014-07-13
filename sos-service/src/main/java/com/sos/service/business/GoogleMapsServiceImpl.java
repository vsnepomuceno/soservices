package com.sos.service.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.ServerException;

import org.apache.poi.util.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sos.entities.Endereco;

@Service
public class GoogleMapsServiceImpl implements GoogleMapsService{

	private static final String URL_GEOCODE = "http://maps.googleapis.com/maps/api/geocode/json";
	private static final String URL_DISTANCE_MATRIX = "http://maps.googleapis.com/maps/api/distancematrix/json";
	private static final String SEPERADOR_ENDERECO = ", ";
	
	private String getJSONGeoCodeByGoogle(String endereco) throws IOException {
		URL url = getGeoCodeUrl(endereco);
		return getJSONByGoogle(url);
	}
	
	private String getJSONMatrixDistanceByGoogle(String origem, String destino) throws IOException {
		URL url = getMatrixDistanceUrl(origem, destino);
		return getJSONByGoogle(url);
	}
	
	private String getJSONByGoogle(URL url) throws IOException{
		URLConnection conn = url.openConnection();
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		IOUtils.copy(conn.getInputStream(), output);
		output.close();
		return output.toString();
	}
	
	private URL getMatrixDistanceUrl(String origem, String destino) throws IOException{
		return new URL(URL_DISTANCE_MATRIX + "?origins=" + URLEncoder.encode(origem, "UTF-8") + 
				"&destinations="  + URLEncoder.encode(destino, "UTF-8") + "&sensor=true");
	}
	
	private URL getGeoCodeUrl(String endereco) throws IOException{
		return new URL(URL_GEOCODE + "?address=" + URLEncoder.encode(endereco, "UTF-8")+ "&sensor=true");
	}
	
	@Override
	public Long calcularDistancia(Endereco enderecoOrigem, double latitudeDestino, double longitudeDestino){
		JSONObject jsonResult;
		Long distance = null;
		try {
			String origem = enderecoOrigem.getLatitude() + "," + enderecoOrigem.getLongitude();
			String destino = latitudeDestino + "," + longitudeDestino;
			jsonResult = new JSONObject(getJSONMatrixDistanceByGoogle(origem, destino));
			String status = jsonResult.getString("status");
			if("OK".equalsIgnoreCase(status)){
				JSONArray jsonArrayResult = jsonResult.getJSONArray("rows");
				
				if(jsonArrayResult != null && jsonArrayResult.length() > 0){
					JSONObject jsonObject = (JSONObject) jsonArrayResult.get(0);
					JSONObject jsonElement = (JSONObject) jsonObject.getJSONArray("elements").get(0);
					distance = jsonElement.getJSONObject("distance").getLong("value");
				}
			}else{
				throw new ServerException("Requisição sem sucesso com o a API do Google MAPS");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return distance;
	}
	
	@Override
	public void configurarLatLongEndereco(Endereco endereco){
		JSONObject jsonResult;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(endereco.getLogradouro());
			sb.append(SEPERADOR_ENDERECO);
			sb.append(endereco.getNumero());
			sb.append(SEPERADOR_ENDERECO);
			sb.append(endereco.getCidade());
			sb.append(SEPERADOR_ENDERECO);
			sb.append(endereco.getEstado());
			sb.append(SEPERADOR_ENDERECO);
			sb.append(endereco.getCep());
			jsonResult = new JSONObject(getJSONGeoCodeByGoogle(sb.toString()));
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