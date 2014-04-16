package com.sos.service.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Usuario;
import com.sos.service.repository.PapelRepository;
import com.sos.service.repository.UsuarioRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PapelRepository papelRepository;

	@Override
	@Transactional(readOnly=true)
	public Usuario findByCodigo(final Long codigo) {
		return usuarioRepository.findOne(codigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByLogin(final String login) {
		return usuarioRepository.findByLogin(login);
	}

	@Override
	@Transactional
	public void cadastrarUsuario(Usuario usuario) throws ServiceException {
		Usuario usuarioPesquisado = usuarioRepository.findByLogin(usuario.getLogin());
		if(usuarioPesquisado == null){
			usuario.setDataCadastro(new Date());
			usuario.setAtivo(true);
			usuario.setLogin(usuario.getLogin().toLowerCase().trim());
			usuarioRepository.save(usuario);
		}else{
			throw new ServiceException(MessageUtil.getMessageFromBundle("exception.operador_login_existente"));
		}
	}

	@Override
	@Transactional
	public void editarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
}