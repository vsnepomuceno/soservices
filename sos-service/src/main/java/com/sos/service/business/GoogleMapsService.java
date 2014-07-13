package com.sos.service.business;

import com.sos.entities.Endereco;

public interface GoogleMapsService {
	void configurarLatLongEndereco(Endereco endereco);
	Long calcularDistancia(Endereco enderecoOrigem, double latitudeDestino, double longitudeDestino);
}