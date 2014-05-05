package com.sos.service.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Usuario;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.UsuarioRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class UsuarioServiceImpl implements UsuarioSevice {

	private final String USUARIO_NAO_ENCONTRADO = "exception.usuario_nao_encontrado";
	private final String USUARIO_NOME_EXISTENTE = "exception.usuario_nome_existente";
	private final String USUARIO_NOME_OBRIGATORIO = "exception.usuario_nome_obrigatorio";
	private final String USUARIO_EMAIL_OBRIGATORIO = "exception.usuario_email_obrigatorio";
	private final String USUARIO_SENHA_OBRIGATORIO = "exception.usuario_senha_obrigatorio";

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly=true)
	public Usuario findByCodigo(Long codigo) throws ServiceException {
		Usuario usuario = usuarioRepository.findOne(codigo);
		if (usuario == null) {
			throw new ServiceException(MessageUtil.getMessageFromBundle(USUARIO_NAO_ENCONTRADO));
		}
		return usuario;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAllSortByName() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "nome");
		return usuarioRepository.findAll(sort);
	}

	@Override
	@Transactional
	public void create(Usuario usuario) throws ServiceException {
		ResultadoValidacao resultadoValidacao = validarUsuario(usuario, false);

		if (resultadoValidacao.isValido()) {
			String nome = usuario.getNome();
			Usuario usuarioPesquisado = usuarioRepository.findByNome(nome);

			if (usuarioPesquisado == null) {
				usuarioRepository.save(usuario);
			} else {
				String mensagem = MessageUtil.getMessageFromBundle(USUARIO_NOME_EXISTENTE);
				throw new ServiceException(MessageFormat.format(mensagem, nome));
			}
		} else {
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Usuario usuario) throws ServiceException {
		usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Usuario usuario) throws ServiceException {
		usuarioRepository.delete(usuario);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByNome(String nome) throws ServiceException {
		return usuarioRepository.findByNome(nome);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByEmail(String email) throws ServiceException {
		return usuarioRepository.findByEmail(email);
	}
	
	private ResultadoValidacao validarUsuario(Usuario usuario, boolean editar){
		boolean valido = true;
		List<String> msgs = new ArrayList<String>();
		if(usuario.getNome() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_NOME_OBRIGATORIO));
		}
		
		if(usuario.getEmail() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_EMAIL_OBRIGATORIO));
		}
		
		if(usuario.getSenha() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_SENHA_OBRIGATORIO));
		}
		return new ResultadoValidacao(valido, msgs);
	}
}