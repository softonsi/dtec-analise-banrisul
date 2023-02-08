package br.com.softon.dtec.entity;

import java.util.Date;

public class MediaOcupacao {

	/**
	* CD_OCUP - Código de ocupação do cliente, isto é da profissão. Integridade com a tabela TB_OCUP.
	*/
	private Long codOcupacao;

	/**
	* DT_CALCULO - Data de cálculo da média mensal, dos últimos 12 meses, da ocupação
	*/
	private Date dataCalculo;

	/**
	* VL_MED_CREDITO - Valor médio mensal de crédito, nos últimos 12 meses, por ocupação.
	*/
	private Double valorMedioCredito;

	/**
	* VL_MED_DEBITO - Valor médio mensal de débito, nos últimos 12 meses, por ocupação.
	*/
	private Double valorMedioDebito;
	

	public MediaOcupacao(Long codOcupacao, Date dataCalculo,
			Double valorMedioCredito, Double valorMedioDebito) {
		super();
		this.codOcupacao = codOcupacao;
		this.dataCalculo = dataCalculo;
		this.valorMedioCredito = valorMedioCredito;
		this.valorMedioDebito = valorMedioDebito;
	}

	/**
	 * @return the codOcupacao
	 */
	public Long getCodOcupacao() {
		return codOcupacao;
	}

	/**
	 * @return the dataCalculo
	 */
	public Date getDataCalculo() {
		return dataCalculo;
	}

	/**
	 * @return the valorMedioCredito
	 */
	public Double getValorMedioCredito() {
		return valorMedioCredito;
	}

	/**
	 * @return the valorMedioDebito
	 */
	public Double getValorMedioDebito() {
		return valorMedioDebito;
	}

}
