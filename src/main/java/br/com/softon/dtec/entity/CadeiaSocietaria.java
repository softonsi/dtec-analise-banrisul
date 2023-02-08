package br.com.softon.dtec.entity;

public class CadeiaSocietaria {

	/**
	* CD_DOC_IDENTF_SOCIEDD - Número do documento de identificação, de acordo com o campo CD_TP_IDENTF_SOCIEDD
	* Se cd_tp_identf_sociedd= 2, isto é CPF, armazenar no campo CD_DOC_IDENTF_SOCIEDD o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda.
	* Se cd_tp_identf_sociedd= 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF_SOCIEDD o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda
	*/
	private final String codDocIdentificacaoSociedade;

	/**
	* CD_DOC_IDENTF_SOCIO - Número do documento de identificação do sócio, de acordo com o campo CD_TP_IDENTF_SOCIO.
	* Se cd_tp_identf_socio= 2, isto é CPF, armazenar no campo CD_DOC_IDENTF_SOCIO o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda.
	* Se cd_tp_identf_socio= 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF_SOCIO o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda
	*/
	private final String codDocIdentificacaoSocio;

	/**
	* CD_IDTFD_MOVTO - Código que identifica se o sócio é movimentador da conta, sendo 1-Sim e 0-Não.
	*/
	private final Byte codIdentificaMovimentador;

	/**
	* CD_TP_IDENTF_SOCIEDD - Indica que tipo de documento o campo NU_DOC_IDENTF_SOCIEDD contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID.
	*/
	private final Byte codTipoIdentificacaoSociedade;

	/**
	* CD_TP_IDENTF_SOCIO - Indica que tipo de documento o campo NU_DOC_IDENTF_SOCIO contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID.
	*/
	private final Byte codTipoIdentificacaoSocio;

	/**
	* CD_TP_PARTC_SOCIO - Código do tipo de participação societária, sendo 1-diretor, 2-controlador, 3-administrador, 4-outros.
	*/
	private final Short codTipoParticipacaoSocio;

	/**
	* NM_SOCIEDD - Nome da empresa.
	*/
	private final String nomeSociedade;

	/**
	* NM_SOCIO - Nome da pessoa participante da sociedade.
	*/
	private final String nomeSocio;

	
	public CadeiaSocietaria(String codDocIdentificacaoSociedade,
			String codDocIdentificacaoSocio, Byte codIdentificaMovimentador,
			Byte codTipoIdentificacaoSociedade, Byte codTipoIdentificacaoSocio,
			Short codTipoParticipacaoSocio, String nomeSociedade,
			String nomeSocio) {
		super();
		this.codDocIdentificacaoSociedade = codDocIdentificacaoSociedade;
		this.codDocIdentificacaoSocio = codDocIdentificacaoSocio;
		this.codIdentificaMovimentador = codIdentificaMovimentador;
		this.codTipoIdentificacaoSociedade = codTipoIdentificacaoSociedade;
		this.codTipoIdentificacaoSocio = codTipoIdentificacaoSocio;
		this.codTipoParticipacaoSocio = codTipoParticipacaoSocio;
		this.nomeSociedade = nomeSociedade;
		this.nomeSocio = nomeSocio;
	}


	/**
	 * @return the codDocIdentificacaoSociedade
	 */
	public String getCodDocIdentificacaoSociedade() {
		return codDocIdentificacaoSociedade;
	}


	/**
	 * @return the codDocIdentificacaoSocio
	 */
	public String getCodDocIdentificacaoSocio() {
		return codDocIdentificacaoSocio;
	}


	/**
	 * @return the codIdentificaMovimentador
	 */
	public Byte getCodIdentificaMovimentador() {
		return codIdentificaMovimentador;
	}


	/**
	 * @return the codTipoIdentificacaoSociedade
	 */
	public Byte getCodTipoIdentificacaoSociedade() {
		return codTipoIdentificacaoSociedade;
	}


	/**
	 * @return the codTipoIdentificacaoSocio
	 */
	public Byte getCodTipoIdentificacaoSocio() {
		return codTipoIdentificacaoSocio;
	}


	/**
	 * @return the codTipoParticipacaoSocio
	 */
	public Short getCodTipoParticipacaoSocio() {
		return codTipoParticipacaoSocio;
	}


	/**
	 * @return the nomeSociedade
	 */
	public String getNomeSociedade() {
		return nomeSociedade;
	}


	/**
	 * @return the nomeSocio
	 */
	public String getNomeSocio() {
		return nomeSocio;
	}	
	
}
