package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class NaturezaDeclarada {

	/**
	* CD_NATUR_DECLR_OPER - Código que identifica a natureza da operação, declarada no momento da compra de moeda estrangeira.
	*/
	private final Integer codNaturezaDeclarada;

	/**
	* DS_NATUR_DECLR_OPER - Descrição da natureza da operação, declarada no momento da compra de moeda estrangeira.
	*/
	private final String descricaoNaturezaDeclarada;

	/**
	* DT_ATUALZ - Data de atualização dos dados.
	*/
	private final Timestamp dataAtualizacao;

	/**
	* VL_MEDIO_NATUR_OPER - Valor médio da natureza da operação, declarada no momento da compra de moeda estrangeira.
	*/
	private final Double valorMedio;

	public NaturezaDeclarada(Integer codNaturezaDeclarada,
			String descricaoNaturezaDeclarada, Timestamp dataAtualizacao,
			Double valorMedio) {
		super();
		this.codNaturezaDeclarada = codNaturezaDeclarada;
		this.descricaoNaturezaDeclarada = descricaoNaturezaDeclarada;
		this.dataAtualizacao = dataAtualizacao;
		this.valorMedio = valorMedio;
	}

	/**
	 * @return the codNaturezaDeclarada
	 */
	public Integer getCodNaturezaDeclarada() {
		return codNaturezaDeclarada;
	}

	/**
	 * @return the descricaoNaturezaDeclarada
	 */
	public String getDescricaoNaturezaDeclarada() {
		return descricaoNaturezaDeclarada;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public Timestamp getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @return the valorMedio
	 */
	public Double getValorMedio() {
		return valorMedio;
	}
	
}
