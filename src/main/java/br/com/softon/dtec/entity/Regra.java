package br.com.softon.dtec.entity;

import java.sql.Timestamp;

import br.com.softon.dtec.Configuracao;

public class Regra implements Configuracao {
	
	/**
	 * CD_CLASSE_REGRA
	 */
	private Short codigoClasseRegra;
	
	/**
	 * CD_SISCOAF
	 */
	private String codigoSiscoaf;
	
	/**
	 * CD_REGRA
	 */
	private Short codigoRegra;
	
	/**
	 * CD_TP_REGRA - Indica se a regra é 1-Estática ou 2-Dinâmica
	 */
	private Byte codigoTipoRegra;
	
	/**
	 * CD_TP_PROCES - Indica se o processamento da regra é 1-Diário ou 2-Mensal
	 */
	private Byte codigoTipoProcesso;
	
	/**
	 * CD_USU
	 */
	private String codigoUsuario;
	
	/**
	 * CD_VERSAO_SISTEMA
	 */
	private Byte codigoVersaoSistema;
	
	/**
	 * DS_ERRO_REGRA
	 */
	private String descricaoErroRegra;
	
	/**
	 * DS_REGRA
	 */
	private String descricaoRegra;
	
	/**
	 * DT_REGRA
	 */
	private Timestamp dataRegra;
	
	/**
	 * DT_REGRA_DINAMICA
	 */
	private Timestamp dataRegraDinamica;
	
	/**
	 * FL_ACUM_REGRA
	 */
	private Byte flagAcumulaRegra;
	
	/**
	 * FL_REGRA_ATIVA
	 */
	private Byte flagRegraAtiva;
	
	/**
	 * NM_REGRA
	 */
	private String nomeRegra;
	
	/**
	 * TX_REGRA_DINAMICA
	 */
	private String textoRegraDinamica;
	
	/**
	 * VL_PONTO
	 */
	private Byte valorPontuacao;
	
	/**
	 * Modelo usado pela Rede Neural
	 */
    private final String nomeModeloNeural = MODELO_NEURAL;    
    
    public Regra(Short codigoRegra, Byte codigoVersaoSistema) {
		super();
		this.codigoVersaoSistema = codigoVersaoSistema;
		this.codigoRegra = codigoRegra;
	}

	public Regra(Short codigoClasseRegra, String codigoEnquadramentoRegra,
			Short codigoRegra, Byte codigoTipoRegra, Byte codigoTipoProcesso, 
			String codigoUsuario,
			Byte codigoVersaoSistema, String descricaoErroRegra,
			String descricaoRegra, Timestamp dataRegra,
			Timestamp dataRegraDinamica, Byte flagAcumulaRegra,
			Byte flagRegraAtiva, String nomeRegra,
			String textoRegraDinamica, Byte valorPontuacao) {
		super();
		this.codigoClasseRegra = codigoClasseRegra;
		this.codigoSiscoaf = codigoEnquadramentoRegra;
		this.codigoRegra = codigoRegra;
		this.codigoTipoRegra = codigoTipoRegra;
		this.codigoTipoProcesso = codigoTipoProcesso;
		this.codigoUsuario = codigoUsuario;
		this.codigoVersaoSistema = codigoVersaoSistema;
		this.descricaoErroRegra = descricaoErroRegra;
		this.descricaoRegra = descricaoRegra;
		this.dataRegra = dataRegra;
		this.dataRegraDinamica = dataRegraDinamica;
		this.flagAcumulaRegra = flagAcumulaRegra;
		this.flagRegraAtiva = flagRegraAtiva;
		this.nomeRegra = nomeRegra;
		this.textoRegraDinamica = textoRegraDinamica;
		this.valorPontuacao = valorPontuacao;
	}



	public Short getCodigoClasseRegra() {
		return codigoClasseRegra;
	}



	public String getCodigoSiscoaf() {
		return codigoSiscoaf;
	}



	public Short getCodigoRegra() {
		return codigoRegra;
	}



	public Byte getCodigoTipoRegra() {
		return codigoTipoRegra;
	}



	public Byte getCodigoTipoProcesso() {
		return codigoTipoProcesso;
	}



	public String getCodigoUsuario() {
		return codigoUsuario;
	}



	public Byte getCodigoVersaoSistema() {
		return codigoVersaoSistema;
	}



	public String getDescricaoErroRegra() {
		return descricaoErroRegra;
	}



	public String getDescricaoRegra() {
		return descricaoRegra;
	}



	public Timestamp getDataRegra() {
		return dataRegra;
	}



	public Timestamp getDataRegraDinamica() {
		return dataRegraDinamica;
	}



	public Byte getFlagAcumulaRegra() {
		return flagAcumulaRegra;
	}



	public Byte getFlagRegraAtiva() {
		return flagRegraAtiva;
	}



	public String getNomeRegra() {
		return nomeRegra;
	}



	public String getTextoRegraDinamica() {
		return textoRegraDinamica;
	}



	public Byte getValorPontuacao() {
		return valorPontuacao;
	}



	public String getNomeModeloNeural() {
		return nomeModeloNeural;
	}

}