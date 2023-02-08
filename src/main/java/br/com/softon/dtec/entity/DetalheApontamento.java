package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class DetalheApontamento {
	
	/**
	 * CD_CLASSE_REGRA - Código do conceito de regra, pondendo ser 0-nulo, 1-leve, 2-média, 3-grave, 4-gravíssima.
	 */
	private final Short codigoClasseRegra;
	
	/**
	 * CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF.
	 * Se cd_tp_identf_clie = 2, isto é CPF, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CPF de 9 dígitos, 
	 * completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda.
	 * Se cd_tp_identf_clie = 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CNPJ de 8 dígitos, 
	 * completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito 
	 * verificador com 2 dígitos, completando também com zero a esquerda.
	 */
	private	final String codDocIdentificacaoCliente;
	
	/**
	 * CD_LOTE - Número do lote, referente ao apontamento da regra
	 */
	private final Long codigoLote;
	
	/**
	 * CD_REGRA - Código da regra DTEC do módulo de análise.
	 */
	private final Short codigoRegra;
	
	/**
	 * CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID.
	 */
	private final Byte codTipoIdentificacaoCliente;	
	
	/**
	 * CD_VERSAO_SISTEMA - Código que identifica a versão do sistema, em relação a Lei de Lavagem de Dinheiro.
	 */
	private final Byte codigoVersaoSistema;
	
	/**
	 * DS_INF_ANLSE - Informações e parâmetros utilizados na análise da regra. O dados estão no formato Json.
	 */
	private final String descricaoInformacoesAnalise;
	
	/**
	 * DT_APONTAMENTO - Data de apontamento do cliente desta ocorrência
	 */
	private final Timestamp dataApontamento;
	
	/**
	 * DT_DISP_REGRA - Data de disparo desta ocorrência
	 */
	private final Timestamp dataDisparoRegra;
	
	/**
	 * Boolean que mostrará se existe regra gravíssima...
	 */
	private final Boolean isGravissima;
	
	/**
	 * Valor da pontuação da regra no momento do disparo...
	 */
	private final Short vlPonto;
	

	public DetalheApontamento(Short codigoClasseRegra,
			String codDocIdentificacaoCliente, Long codigoLote,
			Short codigoRegra,
			Byte codTipoIdentificacaoCliente,
			Byte codigoVersaoSistema, String descricaoInformacoesAnalise,
			Timestamp dataDisparoRegra, Timestamp dataApontamento, Boolean isGravissima, Short vlPonto) {
		super();
		this.codigoClasseRegra = codigoClasseRegra;
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codigoLote = codigoLote;
		this.codigoRegra = codigoRegra;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.codigoVersaoSistema = codigoVersaoSistema;
		this.descricaoInformacoesAnalise = descricaoInformacoesAnalise;
		this.dataDisparoRegra = dataDisparoRegra;
		this.dataApontamento = dataApontamento;
		this.isGravissima = isGravissima;
		this.vlPonto = vlPonto;
	}


	/**
	 * @return the isGravissima
	 */
	public Boolean getIsGravissima() {
		return isGravissima;
	}


	public Short getCodigoClasseRegra() {
		return codigoClasseRegra;
	}


	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}


	public Long getCodigoLote() {
		return codigoLote;
	}


	public Short getCodigoRegra() {
		return codigoRegra;
	}


	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}


	public Byte getCodigoVersaoSistema() {
		return codigoVersaoSistema;
	}


	public String getDescricaoInformacoesAnalise() {
		return descricaoInformacoesAnalise;
	}


	public Timestamp getDataDisparoRegra() {
		return dataDisparoRegra;
	}


	public Timestamp getDataApontamento() {
		return dataApontamento;
	}


	public Short getVlPonto() {
		return vlPonto;
	}

}
