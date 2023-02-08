package br.com.softon.dtec.entity;


public class TransmissaoOrdem {

	/**
	* CD_DOC_IDENTF_REPRE
	*/
	private String codDocIdentificacaoRepresentante;

	/**
	* CD_TP_IDENTF_REPRE
	*/
	private Byte codTipoIdentificacaoRepresentante;

	/**
	* CD_DOC_IDENTF_CLIE
	*/
	private String codDocIdentificacaoCliente;

	/**
	* CD_TP_IDENTF_CLIE
	*/
	private Byte codTipoIdentificacaoCliente;

	/**
	* CD_TP_IDENTF_CLIE
	*/
	private Integer qtRepresentacao;
	

	public TransmissaoOrdem(String codDocIdentificacaoRepresentante,
			Byte codTipoIdentificacaoRepresentante,
			String codDocIdentificacaoCliente, Byte codTipoIdentificacaoCliente, Integer qtRepresentacao) {
		super();
		this.codDocIdentificacaoRepresentante = codDocIdentificacaoRepresentante;
		this.codTipoIdentificacaoRepresentante = codTipoIdentificacaoRepresentante;
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.qtRepresentacao = qtRepresentacao;
	}


	/**
	 * @return the codDocIdentificacaoRepresentante
	 */
	public String getCodDocIdentificacaoRepresentante() {
		return codDocIdentificacaoRepresentante;
	}


	/**
	 * @return the codTipoIdentificacaoRepresentante
	 */
	public Byte getCodTipoIdentificacaoRepresentante() {
		return codTipoIdentificacaoRepresentante;
	}


	/**
	 * @return the codDocIdentificacaoCliente
	 */
	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}


	/**
	 * @return the codTipoIdentificacaoCliente
	 */
	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}


	/**
	 * @return the qtRepresentacao
	 */
	public Integer getQtRepresentacao() {
		return qtRepresentacao;
	}
	
}
