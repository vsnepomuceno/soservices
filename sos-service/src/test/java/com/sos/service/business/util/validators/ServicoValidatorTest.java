package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.Servico;
import com.sos.entities.TipoServico;

public class ServicoValidatorTest {

	@Test
	public void validarCamposServicoTestCamposVazios(){
		Servico servico = new Servico();
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestDescricaoVazia(){
		Servico servico = new Servico();
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(false);
		servico.setTipoServico(tipoServico);
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestTipoServicoVazio(){
		Servico servico = new Servico();
		servico.setDescricao("Serviço de Encanamento");
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestValorVazio(){
		Servico servico = new Servico();
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(true);
		servico.setDescricao("Serviço de Encanamento");
		servico.setTipoServico(tipoServico);
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestValorNegativo(){
		Servico servico = new Servico();
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(true);
		servico.setDescricao("Serviço de Encanamento");
		servico.setTipoServico(tipoServico);
		servico.setValor(-1.0);
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestValorZero(){
		Servico servico = new Servico();
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(true);
		servico.setDescricao("Serviço de Encanamento");
		servico.setTipoServico(tipoServico);
		servico.setValor(0.0);
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	@Test
	public void validarCamposServicoTestPassou(){
		Servico servico = new Servico();
		servico.setDescricao("Serviço de Encanamento");
		servico.setTipoServico(new TipoServico());
		servico.setValor(0.0);
		Assert.assertFalse(ServicoValidator.validarCamposServico(servico).isValido());
	}
}
