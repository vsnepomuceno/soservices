package com.sos.service.business.util;

import java.util.Comparator;

import com.sos.entities.Servico;

public class ServicosComparator implements Comparator<Servico> {

	@Override
	public int compare(Servico servico1, Servico servico2) {
		if(servico1.getPrestador().getNota() > servico2.getPrestador().getNota()){
			return -1;
		}else if(servico1.getPrestador().getNota() == servico2.getPrestador().getNota()){
			if(servico1.getPrestador().getDistancia() <= servico2.getPrestador().getDistancia()){
				return -1;
			}else{
				return 1;
			}
		}else{
			return 1;
		}
	}
}