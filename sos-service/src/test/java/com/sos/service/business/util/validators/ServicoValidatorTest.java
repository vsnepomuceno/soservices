package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.Endereco;
import com.sos.entities.Prestador;
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
		TipoServico tipoServico = new TipoServico();
		tipoServico.setValorado(true);
		servico.setDescricao("Serviço de Encanamento");
		servico.setTipoServico(tipoServico);
		servico.setValor(10.0);
		
		Prestador prestador = new Prestador();
		carregarCamposUsuario(prestador);
		carregarCamposEndereco(prestador);
		
		servico.setPrestador(prestador);
		
		Assert.assertTrue(ServicoValidator.validarCamposServico(servico).isValido());
	}
	
	private void carregarCamposUsuario(Prestador prestador){
		prestador.setEmail("diogopeixoto@diogopeixoto.com");
		prestador.setNome("Diogo Peixoto");
		prestador.setSenha("12345");
	}
	
	private void carregarCamposEndereco(Prestador prestador){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setNumero(10);
		endereco.setCep("xxxxx-xxx");
		endereco.setCidade("Recife");
		endereco.setEstado("PE");
		prestador.setEndereco(endereco);
	}
}