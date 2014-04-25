package com.sos.service.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.TipoServico;
import com.sos.service.business.util.ResultadoValidacao;
import com.sos.service.repository.TipoServicoRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class TipoServicoServiceImpl implements TipoServicoService {

	private final String TIPO_SERVICO_NAO_ENCONTRADO = "exception.tipo_servico_nao_encontrado";
	private final String TIPO_SERVICO_NOME_EXISTENTE = "exception.tipo_servico_nome_existente";
	private final String TIPO_SERVICO_NOME_OBRIGATORIO = "exception.tipo_servico_nome_obrigatorio";
	private final String TIPO_SERVICO_VALORADO_OBRIGATORIO = "exception.tipo_servico_valorado_obrigatorio";
	
	@Autowired
	TipoServicoRepository tipoServicoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public TipoServico findByCodigo(Long codigo) throws ServiceException {
		TipoServico tipoServico = tipoServicoRepository.findOne(codigo);
		if(tipoServico == null){
			throw new ServiceException(MessageUtil.getMessageFromBundle(TIPO_SERVICO_NAO_ENCONTRADO));
		}
		return tipoServico;
	}

	@Override
	@Transactional(readOnly=true)
	public List<TipoServico> findAllSortByName() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "nome");
		return tipoServicoRepository.findAll(sort);
	}
	
	@Override
	@Transactional(readOnly=true)
	public TipoServico findByNome(String nome) throws ServiceException {
		return tipoServicoRepository.findByNome(nome);
	}

	@Override
	@Transactional
	public void create(TipoServico tipoServico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = validarTipoServico(tipoServico, false);
		
		if(resultadoValidacao.isValido()){
			String nome = tipoServico.getNome();
			TipoServico tipoServicoPesquisado = tipoServicoRepository.findByNome(nome);
			
			if(tipoServicoPesquisado == null){
				tipoServicoRepository.save(tipoServico);
			}else{
				String mensagem = MessageUtil.getMessageFromBundle(TIPO_SERVICO_NOME_EXISTENTE);
				throw new ServiceException(MessageFormat.format(mensagem, nome));
			}
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(TipoServico tipoServico) throws ServiceException {
		tipoServicoRepository.save(tipoServico);
	}

	@Override
	@Transactional
	public void delete(TipoServico tipoServico) throws ServiceException {
		tipoServicoRepository.delete(tipoServico);
	}
	
	private ResultadoValidacao validarTipoServico(TipoServico tipoServico, boolean editar){
		boolean valido = true;
		List<String> msgs = new ArrayList<String>();
		if(tipoServico.getNome() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(TIPO_SERVICO_NOME_OBRIGATORIO));
		}
		
		if(tipoServico.isValorado() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(TIPO_SERVICO_VALORADO_OBRIGATORIO));
		}
		return new ResultadoValidacao(valido, msgs);
	}
}