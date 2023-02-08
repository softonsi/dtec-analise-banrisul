package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class PerfilInformacao {

	/**
	* CD_IDENTF_PERFIL - Código que identifica o perfil, mantido pelo sistema.
	*/
	private final Short codIdentificacaoPerfil;

	/**
	* CD_VARIAVEL_PRIMEIRA - Primeira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelPrimeira;

	/**
	* CD_VARIAVEL_QUARTA - Quarta informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelQuarta;

	/**
	* CD_VARIAVEL_SEGUNDA - Segunda informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelSegunda;

	/**
	* CD_VARIAVEL_TERCEIRA - Terceira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelTerceira;

	/**
	* DT_PERFIL - Data do perfil
	*/
	private final Timestamp dataPerfil;

	/**
	* QT_TOTAL - Quantidade total de transações, de acordo com o período de recuo.
	*/
	private final Integer qtdTotal;

	/**
	* VL_DESVIO_DIARIO - Valor do desvio padrão diário.
	*/
	private final Double valorDesvioDiario;

	/**
	* VL_MEDIO_DIARIO - Valor médio diário.
	*/
	private final Double valorMedioDiario;

	/**
	* VL_TOTAL - Valor total de transações, de acordo com o período de recuo.
	*/
	private final Double valorTotal;

	public PerfilInformacao(Short codIdentificacaoPerfil,
			String codVariavelPrimeira, String codVariavelQuarta,
			String codVariavelSegunda, String codVariavelTerceira,
			Timestamp dataPerfil, Integer qtdTotal, Double valorDesvioDiario,
			Double valorMedioDiario, Double valorTotal) {
		super();
		this.codIdentificacaoPerfil = codIdentificacaoPerfil;
		this.codVariavelPrimeira = codVariavelPrimeira;
		this.codVariavelQuarta = codVariavelQuarta;
		this.codVariavelSegunda = codVariavelSegunda;
		this.codVariavelTerceira = codVariavelTerceira;
		this.dataPerfil = dataPerfil;
		this.qtdTotal = qtdTotal;
		this.valorDesvioDiario = valorDesvioDiario;
		this.valorMedioDiario = valorMedioDiario;
		this.valorTotal = valorTotal;
	}

	/**
	 * @return the codIdentificacaoPerfil
	 */
	public Short getCodIdentificacaoPerfil() {
		return codIdentificacaoPerfil;
	}

	/**
	 * @return the codVariavelPrimeira
	 */
	public String getCodVariavelPrimeira() {
		return codVariavelPrimeira;
	}

	/**
	 * @return the codVariavelQuarta
	 */
	public String getCodVariavelQuarta() {
		return codVariavelQuarta;
	}

	/**
	 * @return the codVariavelSegunda
	 */
	public String getCodVariavelSegunda() {
		return codVariavelSegunda;
	}

	/**
	 * @return the codVariavelTerceira
	 */
	public String getCodVariavelTerceira() {
		return codVariavelTerceira;
	}

	/**
	 * @return the dataPerfil
	 */
	public Timestamp getDataPerfil() {
		return dataPerfil;
	}

	/**
	 * @return the qtdTotal
	 */
	public Integer getQtdTotal() {
		return qtdTotal;
	}

	/**
	 * @return the valorDesvioDiario
	 */
	public Double getValorDesvioDiario() {
		return valorDesvioDiario;
	}

	/**
	 * @return the valorMedioDiario
	 */
	public Double getValorMedioDiario() {
		return valorMedioDiario;
	}

	/**
	 * @return the valorTotal
	 */
	public Double getValorTotal() {
		return valorTotal;
	}
	
}
