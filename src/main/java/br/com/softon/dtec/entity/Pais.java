package br.com.softon.dtec.entity;

public class Pais {

	/**
	* CD_PAIS - Código do país.
	*/
	private final Short codPais;

	/**
	* FL_ELEV_RELAC - Flag que indica se o Brasil possui elevado número de transações financeiras e comerciais com o país.
	*/
	private final Byte flagElevadoRelacionamento;

	/**
	* FL_FONTE_NARCO - Indica se o pais é fonte de narcotráfico, sendo: 1 - Sim e 0 - Não.
	*/
	private final Byte flagFonteNarcotrafico;

	/**
	* FL_FRONT - Indica se região faz fronteira com o Brasil. Sendo 1-Faz fronteira e 0-Não faz fronteira.
	*/
	private final Byte flagRegiaoFronteira;

	/**
	* FL_FRONT_COMUM - Flag que indica se o Brasil possui fronteiras comuns com o país.
	*/
	private final Byte flagFronteiraComum;

	/**
	* FL_PARAIS_FISCAL - Indica se o país é considerado paraíso fiscal, sendo 1-paraíso fiscal, 0-não é paraíso fiscal
	*/
	private final Byte flagParaisoFiscal;

	/**
	* FL_PROXMDD_ETNICA - Flag que indica se o Brasil possui proximidade étnica com o país.
	*/
	private final Byte flagProximidadeEtnica;

	/**
	* FL_PROXMDD_LINGTCA - Flag que indica se o Brasil possui proximidade Lingüística com o país.
	*/
	private final Byte flagProximidadeLinguistica;

	/**
	* FL_PROXMDD_POLITICA - Flag que indica se o Brasil possui proximidade política com o país.
	*/
	private final Byte flagProximidadePolitica;

	/**
	* FL_SUSP - Indica se o país é considerado suspeito, sendo 1-sim e 0-não.
	*/
	private final Byte flagSuspeito;

	/**
	* NM_PAIS - Nome país.
	*/
	private final String nomePais;

	public Pais(Short codPais, Byte flagElevadoRelacionamento,
			Byte flagFonteNarcotrafico, Byte flagRegiaoFronteira,
			Byte flagFronteiraComum, Byte flagParaisoFiscal,
			Byte flagProximidadeEtnica, Byte flagProximidadeLinguistica,
			Byte flagProximidadePolitica, Byte flagSuspeito, String nomePais) {
		super();
		this.codPais = codPais;
		this.flagElevadoRelacionamento = flagElevadoRelacionamento;
		this.flagFonteNarcotrafico = flagFonteNarcotrafico;
		this.flagRegiaoFronteira = flagRegiaoFronteira;
		this.flagFronteiraComum = flagFronteiraComum;
		this.flagParaisoFiscal = flagParaisoFiscal;
		this.flagProximidadeEtnica = flagProximidadeEtnica;
		this.flagProximidadeLinguistica = flagProximidadeLinguistica;
		this.flagProximidadePolitica = flagProximidadePolitica;
		this.flagSuspeito = flagSuspeito;
		this.nomePais = nomePais;
	}

	/**
	 * @return the codPais
	 */
	public Short getCodPais() {
		return codPais;
	}

	/**
	 * @return the flagElevadoRelacionamento
	 */
	public Byte getFlagElevadoRelacionamento() {
		return flagElevadoRelacionamento;
	}

	/**
	 * @return the flagFonteNarcotrafico
	 */
	public Byte getFlagFonteNarcotrafico() {
		return flagFonteNarcotrafico;
	}

	/**
	 * @return the flagRegiaoFronteira
	 */
	public Byte getFlagRegiaoFronteira() {
		return flagRegiaoFronteira;
	}

	/**
	 * @return the flagFronteiraComum
	 */
	public Byte getFlagFronteiraComum() {
		return flagFronteiraComum;
	}

	/**
	 * @return the flagParaisoFiscal
	 */
	public Byte getFlagParaisoFiscal() {
		return flagParaisoFiscal;
	}

	/**
	 * @return the flagProximidadeEtnica
	 */
	public Byte getFlagProximidadeEtnica() {
		return flagProximidadeEtnica;
	}

	/**
	 * @return the flagProximidadeLinguistica
	 */
	public Byte getFlagProximidadeLinguistica() {
		return flagProximidadeLinguistica;
	}

	/**
	 * @return the flagProximidadePolitica
	 */
	public Byte getFlagProximidadePolitica() {
		return flagProximidadePolitica;
	}

	/**
	 * @return the flagSuspeito
	 */
	public Byte getFlagSuspeito() {
		return flagSuspeito;
	}

	/**
	 * @return the nomePais
	 */
	public String getNomePais() {
		return nomePais;
	}
	
}
