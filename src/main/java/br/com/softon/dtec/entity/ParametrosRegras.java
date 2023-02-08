package br.com.softon.dtec.entity;

import java.util.Arrays;
import java.util.List;

public class ParametrosRegras {

	/**
	* CD_REGRA - Código da regra DTEC do módulo de análise.
	*/
	private final Integer codigoRegra;

	/**
	* CD_SEQ_REGRA - Código sequencial, que dá unicidade as parâmetros de uma regra.
	*/
	private final Integer codigoSequenciaRegra;

	/**
	* CD_VERSAO_SISTEMA - Código que identifica a versão do sistema, em relação a Lei de Lavagem de Dinheiro.
	*/
	private final Byte codigoVersaoSistema;

	/**
	* DS_CAMPO_PARAM - Descrição do parâmetro da regra.
	*/
	private final String descricaoCampo;

	/**
	* DS_PARAM - Texto que descreve o parâmetro da regra.
	*/
	private final String descricaoParametro;

	/**
	* NM_CAMPO_PARAM - Nome do parametro.
	*/
	private final String nomeCampo;

	/**
	* VL_LIM_FIM - Valor final da faixa, quando o tipo do parâmetro for faixa. Caso contrário nulo.
	*/
	private final Double valorFinalFaixa;

	/**
	* VL_LIM_INIC - Valor inicial da faixa, quando o tipo do parâmetro for faixa. Caso contrário nulo.
	*/
	private final Double valorInicioFaixa;

	/**
	* CD_TP_ATRIB - Tipo do atributo.
	*/
	private final Byte codigoTipoAtributo;

	/**
	* VL_PARAM - Valor do parâmetro.
	*/
	private final Double valorParametro;


	/**
	 * Método que retorna parametros de lista
	 */
	public List<String> getLista() {
		if(descricaoParametro != null) {
			return Arrays.asList(descricaoParametro.split(","));
		} else			
			return null;
	}
	
	

	public ParametrosRegras(Integer codigoRegra, Integer codigoSequenciaRegra,
			Byte codigoVersaoSistema, String descricaoCampo,
			String descricaoParametro, String nomeCampo, Double valorFinalFaixa,
			Double valorInicioFaixa, Byte codigoTipoAtributo, Double valorParametro) {
		super();
		this.codigoRegra = codigoRegra;
		this.codigoSequenciaRegra = codigoSequenciaRegra;
		this.codigoVersaoSistema = codigoVersaoSistema;
		this.descricaoCampo = descricaoCampo;
		this.descricaoParametro = descricaoParametro;
		this.nomeCampo = nomeCampo;
		this.valorFinalFaixa = valorFinalFaixa;
		this.valorInicioFaixa = valorInicioFaixa;
		this.codigoTipoAtributo = codigoTipoAtributo;
		this.valorParametro = valorParametro;
	}

	/**
	 * @return the codigoRegra
	 */
	public Integer getCodigoRegra() {
		return codigoRegra;
	}

	/**
	 * @return the codigoSequenciaRegra
	 */
	public Integer getCodigoSequenciaRegra() {
		return codigoSequenciaRegra;
	}

	/**
	 * @return the codigoVersaoSistema
	 */
	public Byte getCodigoVersaoSistema() {
		return codigoVersaoSistema;
	}

	/**
	 * @return the descricaoCampo
	 */
	public String getDescricaoCampo() {
		return descricaoCampo;
	}

	/**
	 * @return the descricaoParametro
	 */
	public String getDescricaoParametro() {
		return descricaoParametro;
	}

	/**
	 * @return the nomeCampo
	 */
	public String getNomeCampo() {
		return nomeCampo;
	}

	/**
	 * @return the valorFinalFaixa
	 */
	public Double getValorFinalFaixa() {
		return valorFinalFaixa;
	}

	/**
	 * @return the valorInicioFaixa
	 */
	public Double getValorInicioFaixa() {
		return valorInicioFaixa;
	}

	/**
	 * @return the valorParametro
	 */
	public Double getValorParametro() {
		return valorParametro;
	}

	/**
	 * @return the codigoTipoAtributo
	 */
	public Byte getCodigoTipoAtributo() {
		return codigoTipoAtributo;
	}
	
	
}
