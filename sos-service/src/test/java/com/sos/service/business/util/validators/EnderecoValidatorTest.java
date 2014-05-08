package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.Endereco;


public class EnderecoValidatorTest{

	@Test
	public void validarCamposEnderecoTestCamposVazios(){
		Endereco endereco = new Endereco();
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestLogradouroConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestNumeroConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setNumero(10);
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestCepConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setCep("xxxxx-xxx");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestCidadeConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setCidade("Recife");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestEstadoConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setEstado("PE");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestLogradouroNumeroConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setNumero(10);
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestLogradouroCepConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setCep("xxxxx-xxx");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestLogradouroCidadeConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setCidade("Recife");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestLogradouroEstadoConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setEstado("PE");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestNumeroCepConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setNumero(10);
		endereco.setCep("xxxxx-xxx");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}

	@Test
	public void validarCamposEnderecoTestNumeroCidadeConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setNumero(10);
		endereco.setCidade("Recife");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestNumeroEstadoConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setNumero(10);
		endereco.setEstado("PE");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestCepCidadeConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setCep("xxxxx-xxx");
		endereco.setCidade("Recife");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestCepEstadoConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setCep("xxxxx-xxx");
		endereco.setEstado("PE");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestCidadeEstadoConfigurado(){
		Endereco endereco = new Endereco();
		endereco.setCidade("Recife");
		endereco.setEstado("PE");
		Assert.assertFalse(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
	
	@Test
	public void validarCamposEnderecoTestAceitou(){
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua X");
		endereco.setNumero(10);
		endereco.setCep("xxxxx-xxx");
		endereco.setCidade("Recife");
		endereco.setEstado("PE");
		Assert.assertTrue(EnderecoValidator.validarCamposEndereco(endereco).isValido());
	}
}