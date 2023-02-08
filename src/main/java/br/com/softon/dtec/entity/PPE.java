package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class PPE {

	/**
	* CD_DOC_IDENTF - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF./nSe cd_tp_identf= 2, isto é CPF, armazenar no campo CD_DOC_IDENTF o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda./nSe cd_tp_identf= 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda./n
	*/
	private final String codDocIdentificacaoCliente;

	/**
	* CD_FONTE_INF - Fonte de informação PPE para pessoas estrangeiras, sendo 1- Declaração expressa do cliente a respeito da sua classificação. 2- Informações publicamente disponíveis e 3- Bases de dados comerciais sobre PPE.
	*/
	private final Byte codFonteinformacao;

	/**
	* CD_PAIS_ORIG - Código do pais de origem do PPE.
	*/
	private final Short codPaisOrigem;

	/**
	* CD_SEQ_PPE - Código seqüencial que identifica a pessoa politicamente exposta.
	*/
	private final Integer codSequencialPPE;

	/**
	* CD_TP_IDENTF - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private final Byte codTipoIdentificacaoCliente;

	/**
	* DS_PPE_GRAU_ENVOLV - Grau de envolvimento com pessoa politicamente exposta, sendo: ministro, deputado, esposa de senador, etc.
	*/
	private final String descricaoGrauEnvolvimentoPPE;

	/**
	* DT_ATULZ_PPE - Data de atualização da pessoa como PPE.
	*/
	private final Timestamp dataAtualizacaoPPE;

	/**
	* NM_PPE - Nome da pessoa politicamente exposta
	*/
	private final String nomePPE;

	/**
	* NU_PASSRTE - Número do passaporte.
	*/
	private final String numeroPassaporte;

	/**
	* SG_ORG_INTERNL - Sigla do organismo internacional.
	*/
	private final String siglaOrganismoInternacional;
	

	public PPE(String codDocIdentificacao, Byte codFonteinformacao,
			Short codPaisOrigem, Integer codSequencialPPE,
			Byte codTipoIdentificacao, String descricaoGrauEnvolvimentoPPE,
			Timestamp dataAtualizacaoPPE, String nomePPE,
			String numeroPassaporte, String siglaOrganismoInternacional) {
		super();
		this.codDocIdentificacaoCliente = codDocIdentificacao;
		this.codFonteinformacao = codFonteinformacao;
		this.codPaisOrigem = codPaisOrigem;
		this.codSequencialPPE = codSequencialPPE;
		this.codTipoIdentificacaoCliente = codTipoIdentificacao;
		this.descricaoGrauEnvolvimentoPPE = descricaoGrauEnvolvimentoPPE;
		this.dataAtualizacaoPPE = dataAtualizacaoPPE;
		this.nomePPE = nomePPE;
		this.numeroPassaporte = numeroPassaporte;
		this.siglaOrganismoInternacional = siglaOrganismoInternacional;
	}


	/**
	 * @return the codDocIdentificacaoCliente
	 */
	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}


	/**
	 * @return the codFonteinformacao
	 */
	public Byte getCodFonteinformacao() {
		return codFonteinformacao;
	}


	/**
	 * @return the codPaisOrigem
	 */
	public Short getCodPaisOrigem() {
		return codPaisOrigem;
	}


	/**
	 * @return the codSequencialPPE
	 */
	public Integer getCodSequencialPPE() {
		return codSequencialPPE;
	}


	/**
	 * @return the codTipoIdentificacaoCliente
	 */
	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}


	/**
	 * @return the descricaoGrauEnvolvimentoPPE
	 */
	public String getDescricaoGrauEnvolvimentoPPE() {
		return descricaoGrauEnvolvimentoPPE;
	}


	/**
	 * @return the dataAtualizacaoPPE
	 */
	public Timestamp getDataAtualizacaoPPE() {
		return dataAtualizacaoPPE;
	}


	/**
	 * @return the nomePPE
	 */
	public String getNomePPE() {
		return nomePPE;
	}


	/**
	 * @return the numeroPassaporte
	 */
	public String getNumeroPassaporte() {
		return numeroPassaporte;
	}


	/**
	 * @return the siglaOrganismoInternacional
	 */
	public String getSiglaOrganismoInternacional() {
		return siglaOrganismoInternacional;
	}
	
}
