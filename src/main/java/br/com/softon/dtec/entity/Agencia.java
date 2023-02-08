package br.com.softon.dtec.entity;

public class Agencia {

	/**
	* CD_AG - Código da Agência do Banco.
	*/
	private Integer codAgencia;

	/**
	* CD_BANCO - Número do Banco.
	*/
	private String codBanco;

	/**
	* CD_DIG_AG - Dígito da agência do banco.
	*/
	private String codDigitoAgencia;

	/**
	* CD_SUREG - Código da superintendência regional a qual a Agência é subordinada.
	*/
	private Integer codSureg;

	/**
	* CD_USU_RESP - Código do usuário responsável pela Agênica.
	*/
	private String codUsuarioResponsavel;

	/**
	* FL_FRONT - Indica se região faz fronteira com o Brasil. Sendo 1-Faz fronteira e 0-Não faz fronteira.
	*/
	private Byte flagFronteira;

	/**
	* FL_LOCAL_PUBLIC - Indica se é local público, sendo 1-local público e 0-não é local público.
	*/
	private Byte flagLocalPublico;

	/**
	* FL_REG_BX_RENDA - Indica se a região é de baixa renda. Sendo 1-Baixa renda e 0-Não é de baixa renda.
	*/
	private Byte flagRegiaBaixaRenda;

	/**
	* FL_REG_CRIME - Indica se a região tem alta criminalidade, sendo 1-sim e 0-não.
	*/
	private Byte flagRegiaoCrime;

	/**
	* NM_AG - Nome da Agência bancária.
	*/
	private String nomeAgencia;

	/**
	* NM_CID - Nome da cidade.
	*/
	private String nomeCidade;

	/**
	* SG_UF_AG - Código do Estado (UF).
	*/
	private String siglaUF;

	public Agencia(Integer codAgencia, String codBanco,
			String codDigitoAgencia, Integer codSureg,
			String codUsuarioResponsavel, Byte flagFronteira,
			Byte flagLocalPublico, Byte flagRegiaBaixaRenda,
			Byte flagRegiaoCrime, String nomeAgencia, String nomeCidade,
			String siglaUF) {
		super();
		this.codAgencia = codAgencia;
		this.codBanco = codBanco;
		this.codDigitoAgencia = codDigitoAgencia;
		this.codSureg = codSureg;
		this.codUsuarioResponsavel = codUsuarioResponsavel;
		this.flagFronteira = flagFronteira;
		this.flagLocalPublico = flagLocalPublico;
		this.flagRegiaBaixaRenda = flagRegiaBaixaRenda;
		this.flagRegiaoCrime = flagRegiaoCrime;
		this.nomeAgencia = nomeAgencia;
		this.nomeCidade = nomeCidade;
		this.siglaUF = siglaUF;
	}

	/**
	 * @return the codAgencia
	 */
	public Integer getCodAgencia() {
		return codAgencia;
	}

	/**
	 * @return the codBanco
	 */
	public String getCodBanco() {
		return codBanco;
	}

	/**
	 * @return the codDigitoAgencia
	 */
	public String getCodDigitoAgencia() {
		return codDigitoAgencia;
	}

	/**
	 * @return the codSureg
	 */
	public Integer getCodSureg() {
		return codSureg;
	}

	/**
	 * @return the codUsuarioResponsavel
	 */
	public String getCodUsuarioResponsavel() {
		return codUsuarioResponsavel;
	}

	/**
	 * @return the flagFronteira
	 */
	public Byte getFlagFronteira() {
		return flagFronteira;
	}

	/**
	 * @return the flagLocalPublico
	 */
	public Byte getFlagLocalPublico() {
		return flagLocalPublico;
	}

	/**
	 * @return the flagRegiaBaixaRenda
	 */
	public Byte getFlagRegiaBaixaRenda() {
		return flagRegiaBaixaRenda;
	}

	/**
	 * @return the flagRegiaoCrime
	 */
	public Byte getFlagRegiaoCrime() {
		return flagRegiaoCrime;
	}

	/**
	 * @return the nomeAgencia
	 */
	public String getNomeAgencia() {
		return nomeAgencia;
	}

	/**
	 * @return the nomeCidade
	 */
	public String getNomeCidade() {
		return nomeCidade;
	}

	/**
	 * @return the siglaUF
	 */
	public String getSiglaUF() {
		return siglaUF;
	}

}
