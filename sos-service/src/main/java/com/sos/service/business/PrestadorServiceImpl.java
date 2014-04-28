package com.sos.service.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sos.entities.Prestador;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.PrestadorRepositorio;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Service
public class PrestadorServiceImpl implements PrestadorService {

	private final String PRESTADOR_NAO_ENCONTRADO = "exception.prestador_nao_encontrado";
	private final String PRESTADOR_CPF_EXISTENTE = "exception.prestador_cpf_existente";
	private final String PRESTADOR_CPF_OBRIGATORIO = "exception.prestador_cpf_obrigatorio";
	private final String PRESTADOR_ENDERECO_OBRIGATORIO = "exception.prestador_endereco_obrigatorio";
	private final String PRESTADOR_TELEFONE_OBRIGATORIO = "exception.prestador_telefone_obrigatorio";

	@Autowired
	PrestadorRepositorio prestadorRepository;

	@Override
	public Prestador findByCodigo(Long codigo) throws ServiceException {
		Prestador prestador = prestadorRepository.findOne(codigo);
		if (prestador == null) {
			throw new ServiceException(
					MessageUtil.getMessageFromBundle(PRESTADOR_NAO_ENCONTRADO));
		}
		return prestador;
	}

	@Override
	public List<Prestador> findAllSortByName() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "nome");
		return prestadorRepository.findAll(sort);
	}

	@Override
	public void create(Prestador prestador) throws ServiceException {
		ResultadoValidacao resultadoValidacao = validarPrestador(prestador, false);

		if (resultadoValidacao.isValido()) {
			String cpf = prestador.getCpf();
			Prestador prestadorPesquisado = prestadorRepository
					.findByCpf(cpf);

			if (prestadorPesquisado == null) {
				prestadorRepository.save(prestador);
			} else {
				String mensagem = MessageUtil
						.getMessageFromBundle(PRESTADOR_CPF_EXISTENTE);
				throw new ServiceException(MessageFormat.format(mensagem, cpf));
			}
		} else {
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	public void update(Prestador prestador) throws ServiceException {
		prestadorRepository.save(prestador);
	}

	@Override
	public void delete(Prestador prestador) throws ServiceException {
		prestadorRepository.delete(prestador);
	}

	@Override
	public Prestador findByCPF(String cpf) throws ServiceException {
		return prestadorRepository.findByCpf(cpf);
	}

	

	
	private ResultadoValidacao validarPrestador(Prestador prestador, boolean editar){
		boolean valido = true;
		List<String> msgs = new ArrayList<String>();
		if(prestador.getCpf()== null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(PRESTADOR_CPF_OBRIGATORIO));
		}
		
		if(prestador.getEndereco() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(PRESTADOR_ENDERECO_OBRIGATORIO));
		}
		
		if(prestador.getTelefone() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(PRESTADOR_TELEFONE_OBRIGATORIO));
		}
		return new ResultadoValidacao(valido, msgs);
	}

}
