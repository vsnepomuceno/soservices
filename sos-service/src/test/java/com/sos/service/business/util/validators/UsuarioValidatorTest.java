package com.sos.service.business.util.validators;

import org.junit.Assert;
import org.junit.Test;

import com.sos.entities.Usuario;

public class UsuarioValidatorTest {

	@Test
	public void validarCamposUsuarioCamposVazios(){
		Usuario usuario = new Usuario();
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposNomeConfigurado(){
		Usuario usuario = new Usuario();
		usuario.setNome("Diogo Peixoto");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposEmailConfigurado(){
		Usuario usuario = new Usuario();
		usuario.setEmail("diogopeixoto@diogopeixoto.com");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposSenhaConfigurada(){
		Usuario usuario = new Usuario();
		usuario.setSenha("12345");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposSenhaVazia(){
		Usuario usuario = new Usuario();
		usuario.setNome("Diogo Peixoto");
		usuario.setEmail("diogopeixoto@diogopeixoto.com");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposEmailVazio(){
		Usuario usuario = new Usuario();
		usuario.setNome("Diogo Peixoto");
		usuario.setSenha("12345");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposNomeVazio(){
		Usuario usuario = new Usuario();
		usuario.setEmail("diogopeixoto@diogopeixoto.com");
		usuario.setSenha("12345");
		
		Assert.assertFalse(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
	
	@Test
	public void validarCamposUsuarioCamposNomeAceitou(){
		Usuario usuario = new Usuario();
		usuario.setNome("Diogo Peixoto");
		usuario.setEmail("diogopeixoto@diogopeixoto.com");
		usuario.setSenha("12345");
		
		Assert.assertTrue(UsuarioValidator.validarCamposUsuario(usuario).isValido());
	}
}