package br.com.softon.dtec.entity;

public class VariavelPerfil {

	/**
	* CD_IDENTF_PERFIL - Código que identifica o perfil, mantido pelo sistema.
	*/
	private final Short codIdentificacaoPerfil;

	/**
	* DS_PERFIL - Descrição do Perfil.
	*/
	private final String dsPerfil;

	/**
	* NM_VARIAVEL_PRIMEIRA - Primeira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String nmVariavelPrimeira;

	/**
	* NM_VARIAVEL_QUARTA - Quarta informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String nmVariavelQuarta;

	/**
	* NM_VARIAVEL_SEGUNDA - Segunda informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String nmVariavelSegunda;

	/**
	* NM_VARIAVEL_TERCEIRA - Terceira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String nmVariavelTerceira;

	/**
	* NM_FILTRO_ADICIONAL - Filtros adicionais para compor o Perfil.
	*/
	private final String nmFiltroAdicional;

	/**
	* QT_DIA_RECUO - Quantidade de dias de recuo.
	*/
	private final Short qtDiaRecuo;

	/**
	* CD_TP_PERFIL - Tipo do Perfil (Se Perfil Informação ou Perfil Mês Calendário).
	*/
	private final Byte cdTpPerfil;

	/**
	* FL_ATIVO - Se o perfil esta ativo.
	*/
	private final Byte flAtivo;

	public VariavelPerfil(Short codIdentificacaoPerfil, String dsPerfil, String codVariavelPrimeira,
			String codVariavelQuarta, String codVariavelSegunda, String codVariavelTerceira, String nmFiltroAdicional,
			Short qtDiaRecuo, Byte cdTpPerfil, Byte flAtivo) {
		super();
		this.codIdentificacaoPerfil = codIdentificacaoPerfil;
		this.dsPerfil = dsPerfil;
		this.nmVariavelPrimeira = codVariavelPrimeira;
		this.nmVariavelQuarta = codVariavelQuarta;
		this.nmVariavelSegunda = codVariavelSegunda;
		this.nmVariavelTerceira = codVariavelTerceira;
		this.nmFiltroAdicional = nmFiltroAdicional;
		this.qtDiaRecuo = qtDiaRecuo;
		this.cdTpPerfil = cdTpPerfil;
		this.flAtivo = flAtivo;
	}

	/**
	 * @return the codIdentificacaoPerfil
	 */
	public Short getCodIdentificacaoPerfil() {
		return codIdentificacaoPerfil;
	}

	/**
	 * @return the dsPerfil
	 */
	public String getDsPerfil() {
		return dsPerfil;
	}

	/**
	 * @return the nmVariavelPrimeira
	 */
	public String getNmVariavelPrimeira() {
		return nmVariavelPrimeira;
	}

	/**
	 * @return the nmVariavelQuarta
	 */
	public String getNmVariavelQuarta() {
		return nmVariavelQuarta;
	}

	/**
	 * @return the nmVariavelSegunda
	 */
	public String getNmVariavelSegunda() {
		return nmVariavelSegunda;
	}

	/**
	 * @return the nmVariavelTerceira
	 */
	public String getNmVariavelTerceira() {
		return nmVariavelTerceira;
	}

	/**
	 * @return the nmFiltroAdicional
	 */
	public String getNmFiltroAdicional() {
		return nmFiltroAdicional;
	}

	/**
	 * @return the qtDiaRecuo
	 */
	public Short getQtDiaRecuo() {
		return qtDiaRecuo;
	}

	/**
	 * @return the cdTpPerfil
	 */
	public Byte getCdTpPerfil() {
		return cdTpPerfil;
	}

	/**
	 * @return the flAtivo
	 */
	public Byte getFlAtivo() {
		return flAtivo;
	}

}
