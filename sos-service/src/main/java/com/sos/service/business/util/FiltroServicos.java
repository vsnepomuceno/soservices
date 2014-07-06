package com.sos.service.business.util;

import com.sos.entities.TipoServico;

public class FiltroServicos {

	private TipoServico tipoServico;
	private double latitude;
	private double longitude;
	private double distancia;
	
	public FiltroServicos(TipoServico tipoServico, double latitude, double longitude, double distancia) {
		this.tipoServico = tipoServico;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distancia = distancia;
	}

	@Override
	public String toString() {
		return "FiltroPrestadores [idTIpoServico=" + tipoServico.getNome()
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", distancia=" + distancia + "]";
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getDistancia() {
		return distancia;
	}
}