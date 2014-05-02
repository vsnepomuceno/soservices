	package com.sos.service.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.TipoServico;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.business.util.validators.TipoServicoValidator;
import com.sos.service.repository.TipoServicoRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class TipoServicoServiceImpl implements TipoServicoService {

	private final String TIPO_SERVICO_NAO_ENCONTRADO = "exception.tipo_servico_id_nao_encontrado";
	private final String TIPO_SERVICO_NOME_EXISTENTE = "exception.tipo_servico_nome_existente";
	
	@Autowired
	TipoServicoRepository tipoServicoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public TipoServico findByCodigo(Long codigo) throws ServiceException {
		TipoServico tipoServico = tipoServicoRepository.findOne(codigo);
		if(tipoServico == null){
			String mensagem = MessageUtil.getMessageFromBundle(TIPO_SERVICO_NAO_ENCONTRADO);
			throw new ServiceException(MessageFormat.format(mensagem, codigo));
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
		ResultadoValidacao resultadoValidacao = TipoServicoValidator.validarCamposTipoServico(tipoServico);
		
		if(resultadoValidacao.isValido()){
			verificarNomeTipoServicoUnique(tipoServico, false);
			tipoServicoRepository.save(tipoServico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(TipoServico tipoServico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = TipoServicoValidator.validarCamposTipoServico(tipoServico);
		
		if(resultadoValidacao.isValido()){
			verificarNomeTipoServicoUnique(tipoServico, true);
			tipoServicoRepository.save(tipoServico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void delete(TipoServico tipoServico) throws ServiceException {
		tipoServicoRepository.delete(tipoServico);
	}
	
	private void verificarNomeTipoServicoUnique(TipoServico tipoServico, boolean editar) throws ServiceException{
		String nome = tipoServico.getNome();
		TipoServico tipoServicoPesquisado = tipoServicoRepository.findByNome(nome);
		if(tipoServicoPesquisado != null){
			if((editar && !tipoServicoPesquisado.getId().equals(tipoServico.getId())) || !editar){
				String mensagem = MessageUtil.getMessageFromBundle(TIPO_SERVICO_NOME_EXISTENTE);
				List<String> msgs = new ArrayList<String>();
				msgs.add(MessageFormat.format(mensagem, nome));
				throw new ServiceException(msgs);
			}
		}
	}
}