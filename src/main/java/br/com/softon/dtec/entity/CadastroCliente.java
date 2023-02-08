package br.com.softon.dtec.entity;


public class CadastroCliente {

	/**
	* CD_CEP_RESID - Código de Endereçamento Postal da residencia do cliente.
	*/
	private Integer codCepResidencia;

	/**
	* CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF./nSe cd_tp_identf_clie = 2, isto é CPF, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda./nSe cd_tp_identf_clie = 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda.
	*/
	private String codDocIdentificacaoCliente;

	/**
	* CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private Byte codTipoIdentificacaoCliente;

	/**
	* CD_TP_PESSOA - Código do tipo de pessoa, se jurídica ou física, sendo: F = física/nJ = jurídica
	*/
	private Character codTipoPessoa;

	/**
	* NM_CLIE - Nome do cliente do banco.
	*/
	private String nomeCliente;

	/**
	* NM_CONJUGE - Nome do cônjuge./nArt.2º. I ¿ Nome do cônjuge, se casado (Resolução 2.025 Art.1º. I.a.7)
	*/
	private String nomeConjuge;

	/**
	* NM_ENDER_RESID - Endereço da residência do cliente do banco.
	*/
	private String nomeEnderecoResidencia;

	/**
	* NM_CID_RESID - Cidade da residência do cliente do banco.
	*/
	private String nomeCidadeResidencia;

	/**
	* SG_UF_RESID - UF da residência do cliente do banco.
	*/
	private String siglaUFResidencia;

	/**
	* CD_PAIS_RESID - Código do país da residência do cliente do banco.
	*/
	private Integer codPaisResidencia;

	/**
	* NM_MAE - Nome da mãe do cliente. Art.2º. I Filiação (Resolução 2.025 Art.1º, I.a.2) (pessoa física)
	*/
	private String nomeMae;

	/**
	* NM_PAI - Nome do pai do cliente. Art.2º. I Filiação (Resolução 2.025 Art.1º, I.a.2) (pessoa física)
	*/
	private String nomePai;

	public CadastroCliente(Integer codCepResidencia,
			String codDocIdentificacaoCliente,
			Byte codTipoIdentificacaoCliente, Character codTipoPessoa,
			String nomeCliente, String nomeConjuge,
			String nomeEnderecoResidencia,
			String nomeCidadeResidencia,
			String siglaUFResidencia, Integer codPaisResidencia, String nomeMae, String nomePai) {
		super();
		this.codCepResidencia = codCepResidencia;
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.codTipoPessoa = codTipoPessoa;
		this.nomeCliente = nomeCliente;
		this.nomeConjuge = nomeConjuge;
		this.nomeEnderecoResidencia = nomeEnderecoResidencia;
		this.nomeCidadeResidencia = nomeCidadeResidencia;
		this.siglaUFResidencia = siglaUFResidencia;
		this.codPaisResidencia = codPaisResidencia;
		this.nomeMae = nomeMae;
		this.nomePai = nomePai;
	}

	/**
	 * @return the codCepResidencia
	 */
	public Integer getCodCepResidencia() {
		return codCepResidencia;
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
	 * @return the codTipoPessoa
	 */
	public Character getCodTipoPessoa() {
		return codTipoPessoa;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}

	/**
	 * @return the nomeCidadeResidencia
	 */
	public String getNomeCidadeResidencia() {
		return nomeCidadeResidencia;
	}

	/**
	 * @return the siglaUFResidencia
	 */
	public String getSiglaUFResidencia() {
		return siglaUFResidencia;
	}

	/**
	 * @return the codPaisResidencia
	 */
	public Integer getCodPaisResidencia() {
		return codPaisResidencia;
	}

	/**
	 * @return the nomeConjuge
	 */
	public String getNomeConjuge() {
		return nomeConjuge;
	}

	/**
	 * @return the nomeEnderecoResidencia
	 */
	public String getNomeEnderecoResidencia() {
		return nomeEnderecoResidencia;
	}

	/**
	 * @return the nomeMae
	 */
	public String getNomeMae() {
		return nomeMae;
	}

	/**
	 * @return the nomePai
	 */
	public String getNomePai() {
		return nomePai;
	}
	
}
