package br.com.vagas.pontus.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pagamento.class)
public abstract class Pagamento_ {

	public static volatile SingularAttribute<Pagamento, Long> id;
	public static volatile SingularAttribute<Pagamento, Date> dataPagamento;
	public static volatile SingularAttribute<Pagamento, EPagamento> tipoPagamento;
	public static volatile SingularAttribute<Pagamento, Empresa> empresa;

}

