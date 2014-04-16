package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Empresa.class)
public abstract class Empresa_ {

	public static volatile SetAttribute<Empresa, Vaga> vagas;
	public static volatile SingularAttribute<Empresa, String> telefone2;
	public static volatile SingularAttribute<Empresa, ENacionalidade> nacionalidade;
	public static volatile SingularAttribute<Empresa, Usuario> usuario;
	public static volatile SingularAttribute<Empresa, String> nomeFantasia;
	public static volatile SingularAttribute<Empresa, String> loginCadastro;
	public static volatile SingularAttribute<Empresa, String> razaoSocial;
	public static volatile SetAttribute<Empresa, Pagamento> pagamentos;
	public static volatile SingularAttribute<Empresa, String> cnpj;
	public static volatile SingularAttribute<Empresa, String> cargo;
	public static volatile SingularAttribute<Empresa, Long> id;
	public static volatile SingularAttribute<Empresa, Cidade> cidade;
	public static volatile SingularAttribute<Empresa, String> email;
	public static volatile SingularAttribute<Empresa, String> telefone;
	public static volatile SingularAttribute<Empresa, ESegmento> segmento;
	public static volatile SingularAttribute<Empresa, Integer> creditos;

}

