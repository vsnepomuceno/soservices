package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.TipoServico;

public class TipoServicoValidatorTest {

	@Test
	public void validarCamposTipoServicoTestCamposVazios(){
		TipoServico tipoServico = new TipoServico();
		Assert.assertFalse(TipoServicoValidator.validarCamposTipoServico(tipoServico).isValido());
	}
	
	@Test
	public void validarCamposTipoServicoTestNomeVazio(){
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(true);
		Assert.assertFalse(TipoServicoValidator.validarCamposTipoServico(tipoServico).isValido());
	}
	
	@Test
	public void validarCamposTipoServicoTestValoradoVazio(){
		TipoServico tipoServico = new TipoServico();
		tipoServico.setNome("Tipo Serviço");
		Assert.assertFalse(TipoServicoValidator.validarCamposTipoServico(tipoServico).isValido());
	}
	
	@Test
	public void validarCamposTipoServicoTestPassou(){
		TipoServico tipoServico = new TipoServico();
		tipoServico.setNome("Tipo Serviço");
		tipoServico.setValorado(true);
		Assert.assertTrue(TipoServicoValidator.validarCamposTipoServico(tipoServico).isValido());
	}
}
