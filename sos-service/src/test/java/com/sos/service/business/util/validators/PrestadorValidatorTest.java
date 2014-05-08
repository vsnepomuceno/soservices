package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.Endereco;
import com.sos.entities.Prestador;

public class PrestadorValidatorTest {

	@Test
	public void validarCamposPrestadorCamposVazios(){
		Prestador prestador = new Prestador();
		carregarCamposUsuario(prestador);
		Assert.assertFalse(PrestadorValidator.validarCamposPrestador(prestador).isValido());
	}
	
	@Test
	public void validarCamposPrestadorCpfConfigurado(){
		Prestador prestador = new Prestador();
		carregarCamposUsuario(prestador);
		prestador.setCpf("xxx.xxx.xxx-xx");
		Assert.assertFalse(PrestadorValidator.validarCamposPrestador(prestador).isValido());
	}
	
	@Test
	public void validarCamposPrestadorTelefoneConfigurado(){
		Prestador prestador = new Prestador();
		carregarCamposUsuario(prestador);
		prestador.setTelefone("(xx) xxxx-xxxxx");
		Assert.assertFalse(PrestadorValidator.validarCamposPrestador(prestador).isValido());
	}
	
	@Test
	public void validarCamposPrestadorPassou(){
		Prestador prestador = new Prestador();
		carregarCamposUsuario(prestador);
		carregarCamposEndereco(prestador);
		prestador.setCpf("xxx.xxx.xxx-xx");
		prestador.setTelefone("(xx) xxxx-xxxxx");
		Assert.assertTrue(PrestadorValidator.validarCamposPrestador(prestador).isValido());
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