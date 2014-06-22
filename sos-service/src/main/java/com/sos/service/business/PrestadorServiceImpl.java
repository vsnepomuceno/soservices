package com.sos.service.business;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;
import com.sos.service.business.util.validators.PrestadorValidator;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.PrestadorRepository;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class PrestadorServiceImpl implements PrestadorService {

	private final String PRESTADOR_NAO_ENCONTRADO = "exception.prestador_nao_encontrado";
	private final String PRESTADOR_TIPO_SERVICO_NAO_ENCONTRADO = "exception.prestador_tipo_servIco_nao_encontrado";
	private final String PRESTADOR_CPF_EXISTENTE = "exception.prestador_cpf_existente";
	private final String TIPO_SERVICO_NULO = "exception.tipo_servico_nulo";

	@Autowired
	PrestadorRepository prestadorRepository;
	@Autowired
	GoogleMapsService googleMapsService;

	@Override
	@Transactional(readOnly=true)
	public Prestador findByCodigo(Long codigo) throws ServiceException {
		Prestador prestador = prestadorRepository.findOne(codigo);
		if (prestador == null) {
			throw new ServiceException(MessageUtil.getMessageFromBundle(PRESTADOR_NAO_ENCONTRADO));
		}
		return prestador;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Prestador> findByServicosTipoServico(TipoServico tipoServico) throws ServiceException {
		List<Prestador> prestadores = null;
		if(tipoServico != null){
			prestadores = prestadorRepository.findByServicosTipoServico(tipoServico); 
			if(prestadores == null || prestadores.isEmpty()){
				String mensagem = MessageUtil.getMessageFromBundle(PRESTADOR_TIPO_SERVICO_NAO_ENCONTRADO);
				throw new ServiceException(MessageFormat.format(mensagem, tipoServico.getNome()));
			}
		}else{
			throw new ServiceException(MessageUtil.getMessageFromBundle(TIPO_SERVICO_NULO));
		}
		return prestadores; 
	}

	@Override
	@Transactional(readOnly=true)
	public List<Prestador> findAllSortByName() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "nome");
		return prestadorRepository.findAll(sort);
	}

	@Override
	@Transactional
	public void create(Prestador prestador) throws ServiceException {
		ResultadoValidacao resultadoValidacao = PrestadorValidator.validarCamposPrestador(prestador);

		if (resultadoValidacao.isValido()) {
			String cpf = prestador.getCpf();
			Prestador prestadorPesquisado = prestadorRepository.findByCpf(cpf);

			if (prestadorPesquisado == null ) {
				salvarPrestador(prestador);
			} else {
				String mensagem = MessageUtil.getMessageFromBundle(PRESTADOR_CPF_EXISTENTE);
				throw new ServiceException(MessageFormat.format(mensagem, cpf));
			}
		} else {
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Prestador prestador) throws ServiceException {
		ResultadoValidacao resultadoValidacao = PrestadorValidator.validarCamposPrestador(prestador);

		if (resultadoValidacao.isValido()) {
			String email = prestador.getEmail();
			Prestador prestadorPesquisado = prestadorRepository.findByEmail(email);

			if (prestadorPesquisado == null || prestadorPesquisado.getId().equals(prestador.getId())) {
				salvarPrestador(prestador);
			} else {
				String mensagem = MessageUtil.getMessageFromBundle(PRESTADOR_CPF_EXISTENTE);
				throw new ServiceException(MessageFormat.format(mensagem, email));
			}
		} else {
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}
	
	@Override
	@Transactional
	public void delete(Prestador prestador) throws ServiceException {
		prestadorRepository.delete(prestador);
	}

	@Override
	@Transactional(readOnly=true)
	public Prestador findByCPF(String cpf) throws ServiceException {
		return prestadorRepository.findByCpf(cpf);
	}
	
	private void salvarPrestador(Prestador prestador){
		googleMapsService.configurarLatLongEndereco(prestador.getEndereco());
		prestadorRepository.save(prestador);
	}

	@Override
	public Prestador findByEmail(String email) throws ServiceException {
		return prestadorRepository.findByEmail(email);
	}
}