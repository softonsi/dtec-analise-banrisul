package br.com.softon.dtec.entity;

import java.util.Date;

public class MediaRamoAtividade {

	/**
	* CD_RAMO_ATIVID - Código do ramo de atividade da empresa, baseado no CNAE 2.0, compost por Seção, divisão, 
	* Grupo, Classe e Subclasse (onde seção é igual a setor)
	*/
	private String codRamoAtividade;

	/**
	* DT_CALCULO - Data de cálculo da média mensal, dos últimos 12 meses, do ramo de atividade
	*/
	private Date dataCalculo;

	/**
	* VL_MED_CREDITO - Valor médio mensal de crédito, nos últimos 12 meses, por ramo de atividade.
	*/
	private Double valorMedioCredito;

	/**
	* VL_MED_DEBITO - Valor médio mensal de débito, nos últimos 12 meses, por ramo de atividade.
	*/
	private Double valorMedioDebito;
	

	public MediaRamoAtividade(String codRamoAtividade, Date dataCalculo,
			Double valorMedioCredito, Double valorMedioDebito) {
		super();
		this.codRamoAtividade = codRamoAtividade;
		this.dataCalculo = dataCalculo;
		this.valorMedioCredito = valorMedioCredito;
		this.valorMedioDebito = valorMedioDebito;
	}

	/**
	 * @return the codOcupacao
	 */
	public String getCodRamoAtividade() {
		return codRamoAtividade;
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
