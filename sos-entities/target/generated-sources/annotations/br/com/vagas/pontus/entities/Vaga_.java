package br.com.vagas.pontus.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vaga.class)
public abstract class Vaga_ {

	public static volatile SingularAttribute<Vaga, AreaProfissional> areaProfissional;
	public static volatile SetAttribute<Vaga, TransacaoAprovada> transacoesAprovadas;
	public static volatile SetAttribute<Vaga, EEscolaridade> escolaridades;
	public static volatile SingularAttribute<Vaga, String> atribuicoesResponsabilidades;
	public static volatile SingularAttribute<Vaga, ESexo> sexo;
	public static volatile SingularAttribute<Vaga, Integer> numeroSMS;
	public static volatile SingularAttribute<Vaga, String> cargo;
	public static volatile SingularAttribute<Vaga, Long> id;
	public static volatile SingularAttribute<Vaga, Date> dataExpiracao;
	public static volatile SingularAttribute<Vaga, Integer> quantidade;
	public static volatile SingularAttribute<Vaga, Double> salarioInicial;
	public static volatile SingularAttribute<Vaga, Double> salarioFinal;
	public static volatile SingularAttribute<Vaga, Date> dataConfirmacao;
	public static volatile SingularAttribute<Vaga, Boolean> confidencial;
	public static volatile SingularAttribute<Vaga, EPerfil> perfil;
	public static volatile SingularAttribute<Vaga, Boolean> auditoriaPontus;
	public static volatile SingularAttribute<Vaga, Boolean> confirmacaoEmpresa;
	public static volatile SingularAttribute<Vaga, ERegimeContratacao> regimeContratacao;
	public static volatile SingularAttribute<Vaga, Date> dataCadastro;
	public static volatile SingularAttribute<Vaga, String> conhecimentosEspecificos;
	public static volatile SetAttribute<Vaga, Beneficio> beneficios;
	public static volatile SingularAttribute<Vaga, Boolean> renovou;
	public static volatile SetAttribute<Vaga, TransacaoSMS> transacoesSMS;
	public static volatile SingularAttribute<Vaga, Boolean> enviarSms;
	public static volatile SingularAttribute<Vaga, Boolean> liberada;
	public static volatile SingularAttribute<Vaga, String> loginCadastrou;
	public static volatile SingularAttribute<Vaga, NivelHierarquico> nivelHierarquico;
	public static volatile SingularAttribute<Vaga, Cidade> cidade;
	public static volatile SingularAttribute<Vaga, String> ipConfirmacao;
	public static volatile SingularAttribute<Vaga, EExperiencia> experiencia;
	public static volatile SingularAttribute<Vaga, Empresa> empresa;
	public static volatile SingularAttribute<Vaga, Boolean> ativa;
	public static volatile SingularAttribute<Vaga, Integer> numeroEmails;
	public static volatile SingularAttribute<Vaga, String> emailContato;

}

