package com.sos.service.business.util;

import com.sos.entities.Prestador;

public class DistanciaUtil {

	private static final double PIx = 3.141592653589793;
    private static final double RADIO = 6378.16;

    public static double Radians(double x){
        return x * PIx / 180;
    }

	public static boolean verificarPertenceRaioDistancia(Prestador prestador, double distanciaMaxima, double latitude, double longitude) {
		double distancia = DistanceBetweenPlaces(prestador.getEndereco().getLongitude(), prestador.getEndereco().getLatitude(), longitude, latitude);
		return distancia <= distanciaMaxima;
	}
    
    private static double DistanceBetweenPlaces(double longitude1, double latitude1, double longitude2, double latitude2){
        double dlon =  Radians(longitude2 - longitude1);
        double dlat =  Radians(latitude2 - latitude1);

        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) + Math.cos(Radians(latitude1)) * Math.cos(Radians(latitude2)) * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (angle * RADIO) * 0.62137;
    }
}