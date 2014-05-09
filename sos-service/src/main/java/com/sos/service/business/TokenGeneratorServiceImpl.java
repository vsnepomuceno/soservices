package com.sos.service.business;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.repository.TokenGeneratorRepository;
import com.sos.service.repository.UsuarioRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

public class TokenGeneratorServiceImpl implements TokenGeneratorService {

	private final String TOKEN_NAO_ECONTRADO = "exception.token_nao_encontrado";
	private final String USUARIO_NAO_ECONTRADO = "exception.usuario_nao_encontrado";
	
	@Autowired
	TokenGeneratorRepository tokenGeneratorRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Token findByApiKeyAndUsuarioId(String apiKey, Long id) throws ServiceException {
		Token token = tokenGeneratorRepository.findByApiKeyAndUsuarioId(apiKey, id);
		if(token == null){
			throw new ServiceException(MessageUtil.getMessageFromBundle(TOKEN_NAO_ECONTRADO));
		}
		return token;
	}

	@Override
	@Transactional
	public Token create(Usuario usuario) throws ServiceException {
		usuario = usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if(usuario != null){
			String keySource = usuario.getNome() + new Date() + UUID.randomUUID();
			byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
			String apiKey = new String(tokenByte);

			Token token = new Token();
			token.setUsuario(usuario);
			token.setApiKey(apiKey.substring(0, 49));
			
			return tokenGeneratorRepository.save(token);
		}else{
			throw new ServiceException(MessageUtil.getMessageFromBundle(USUARIO_NAO_ECONTRADO));
		}
	}

	@Override
	@Transactional
	public void deleteAllByUsuario(Usuario usuario) throws ServiceException {
		usuario = usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if(usuario != null){
			List<Token> tokens = tokenGeneratorRepository.findByUsuario(usuario);
			tokenGeneratorRepository.deleteInBatch(tokens);
		}
	}
	
	@Override
	@Transactional
	public void delete(Token token) throws ServiceException {
		tokenGeneratorRepository.delete(token);
	}
}