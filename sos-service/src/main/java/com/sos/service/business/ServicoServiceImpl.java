package com.sos.service.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Prestador;
import com.sos.entities.Servico;
import com.sos.entities.TipoServico;
import com.sos.service.business.util.DistanciaUtil;
import com.sos.service.business.util.FiltroServicos;
import com.sos.service.business.util.validators.PrestadorValidator;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.business.util.validators.ServicoValidator;
import com.sos.service.repository.PrestadorRepository;
import com.sos.service.repository.ServicoRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class ServicoServiceImpl implements ServicoService {

	@Autowired
	ServicoRepository servicoRepository;

	@Autowired
	PrestadorRepository prestadorRepository;

	private static final String SERVICO_NAO_ENCONTRADO = "exception.servico_id_nao_encontrado";
	
	@Override
	@Transactional(readOnly=true)
	public Servico findByCodigo(Long codigo) throws ServiceException {
		Servico servico = servicoRepository.findOne(codigo);
		if(servico == null){
			String mensagem = MessageUtil.getMessageFromBundle(SERVICO_NAO_ENCONTRADO);
			throw new ServiceException(MessageFormat.format(mensagem, codigo));
		}
		return servico;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findAllSortByValor() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "valor");
		return servicoRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findAllSortByDescricao() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "descricao");
		return servicoRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByDescricao(String descricao) {
		return servicoRepository.findByDescricao(descricao);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByTipoServico(TipoServico tipoServico) {
		return servicoRepository.findByTipoServico(tipoServico);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByFiltroServico(FiltroServicos filtro) throws ServiceException {
		List<Prestador> prestadores = null;
		List<Prestador> prestadoresFiltrados = null;
		List<Servico> servicos = new ArrayList<Servico>();
		ResultadoValidacao resultadoValidacao = PrestadorValidator.validarFiltroPrestador(filtro);
		if(resultadoValidacao.isValido()){
			prestadores = prestadorRepository.findByServicosTipoServico(filtro.getTIpoServico());
			prestadoresFiltrados = new ArrayList<Prestador>();
			for (Prestador prestador : prestadores) {
				if(DistanciaUtil.verificarPertenceRaioDistancia(prestador, filtro.getDistancia(), filtro.getLatitude(), filtro.getLongitude())){
					prestadoresFiltrados.add(prestador);
				}
			}
			
			for (Prestador prestador : prestadoresFiltrados) {
				for (Servico servico : prestador.getServicos()) {
					if(servico.getTipoServico().equals(filtro.getTIpoServico())){
						servicos.add(servico);
					}
				}
			}
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
		return servicos;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByValor(double valor) {
		return servicoRepository.findByValor(valor);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByPrestadorEmail(String email) {
		return servicoRepository.findByPrestadorEmail(email);
	}

	@Override
	@Transactional
	public void create(Servico servico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = ServicoValidator.validarCamposServico(servico);
		
		if(resultadoValidacao.isValido()){
			servicoRepository.save(servico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Servico servico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = ServicoValidator.validarCamposServico(servico);
		
		if(resultadoValidacao.isValido()){
			servicoRepository.save(servico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void delete(Servico servico) throws ServiceException {
		servicoRepository.delete(servico);
	}
}