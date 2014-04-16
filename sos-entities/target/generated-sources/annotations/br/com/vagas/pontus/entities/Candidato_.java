package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Candidato.class)
public abstract class Candidato_ {

	public static volatile SingularAttribute<Candidato, Usuario> usuario;
	public static volatile SetAttribute<Candidato, TransacaoAprovada> transacoesAprovadas;
	public static volatile SingularAttribute<Candidato, ESexo> sexo;
	public static volatile SetAttribute<Candidato, AreaProfissional> areasProfissionais;
	public static volatile SetAttribute<Candidato, TransacaoSMS> transacoesSMS;
	public static volatile SingularAttribute<Candidato, Long> id;
	public static volatile SingularAttribute<Candidato, Cidade> cidade;
	public static volatile SetAttribute<Candidato, NivelHierarquico> niveisHierarquicos;
	public static volatile SingularAttribute<Candidato, Double> salario;
	public static volatile SingularAttribute<Candidato, String> email;
	public static volatile SingularAttribute<Candidato, EEscolaridade> escolaridade;
	public static volatile SingularAttribute<Candidato, String> telefone;
	public static volatile SingularAttribute<Candidato, String> cpf;

}

