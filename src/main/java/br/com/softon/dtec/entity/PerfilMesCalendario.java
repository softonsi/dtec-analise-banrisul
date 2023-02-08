package br.com.softon.dtec.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PerfilMesCalendario {

	/**
	* CD_ANO_MES - Código que identifica o ano e mês do perfil, sendo MMAAAA
	*/
	private final Integer codAnoMes;

	/**
	* CD_IDENTF_PERFIL - Código que identifica o perfil, mantido pelo sistema.
	*/
	private final Short codIdentificacaoPerfil;

	/**
	* CD_VARIAVEL_PRIMEIRA - Primeira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelPrimeira;

	/**
	* CD_VARIAVEL_QUARTA - Quarta informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelQuarta;

	/**
	* CD_VARIAVEL_SEGUNDA - Segunda informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelSegunda;

	/**
	* CD_VARIAVEL_TERCEIRA - Terceira informação que compõem a chave do perfil, mantido pelo sistema.
	*/
	private final String codVariavelTerceira;

	/**
	* DT_PERFIL - Data do perfil
	*/
	private final Timestamp dataPerfil;

	/**
	* QT_TOTAL - Quantidade total de transações, de acordo com o período de recuo.
	*/
	private final Integer qtdTotal;

	/**
	* VL_DESVIO_DIARIO - Valor do desvio padrão diário.
	*/
	private final Double valorDesvioDiario;

	/**
	* VL_MEDIO_DIARIO - Valor médio diário.
	*/
	private final Double valorMedioDiario;

	/**
	* VL_TOTAL - Valor total de transações, de acordo com o período de recuo.
	*/
	private final Double valorTotal;

	
	public PerfilMesCalendario(Integer codAnoMes, 
			Short codIdentificaPerfil,
			String codVariavelPrimeira, 
			String codVariavelSegunda,
			String codVariavelTerceira,
			String codVariavelQuarta,
			Double valorTotal,			 
			Integer qtdTotal,
			Double valorMedioDiario,
			Double valorDesvioDiario,Timestamp dataPerfil ) {
		super();
		this.codAnoMes = codAnoMes;
		this.codIdentificacaoPerfil = codIdentificaPerfil;
		this.codVariavelPrimeira = codVariavelPrimeira;
		this.codVariavelQuarta = codVariavelQuarta;
		this.codVariavelSegunda = codVariavelSegunda;
		this.codVariavelTerceira = codVariavelTerceira;
		this.dataPerfil = dataPerfil;
		this.qtdTotal = qtdTotal;
		this.valorDesvioDiario = valorDesvioDiario;
		this.valorMedioDiario = valorMedioDiario;
		this.valorTotal = valorTotal;
	}
	
	/*---------------Método de apoio às regras---------------*/

//	public boolean verificarPerfil13(ObjetoAnalise trans, Double valorPercPerfil) {
//		if ( new Short( (short) 13 ).equals( this.codIdentificacaoPerfil ) 
//			&& trans.getCodCartao() != null && trans.getCodCartao().equals( codVariavelPrimeira ) 
//			&& trans.getCodPaisOperacaoString() != null && trans.getCodPaisOperacaoString().equals( codVariavelSegunda ) 
//			&& trans.getSiglaUFAgenciaOperacao() != null && trans.getSiglaUFAgenciaOperacao().equals( codVariavelTerceira.toString() )
//			&& !getCodAnoMesString().equals( getMesAnoCorrente( "yyyyMM" ) ) 
//			&& getValorMedioDiario() != null
//			&& trans.getValorOperacao() != null && trans.getValorOperacao() > (getValorMedioDiario() * (valorPercPerfil / 100) )
//			) {
//			return true;
//		}
//		
//		return false;
//	}
	
	/**
	 * @return String da data corrente no formato passado.
	 */
	public static String getMesAnoCorrente(String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(new Date());
	}
	
	/**
	 * @return String da data do mês ano anterior o atual
	 */
	public static String getMesAnoAnterior() {
        Calendar c1 = Calendar.getInstance();
        
        c1.add(Calendar.MONTH, -1);
        
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(c1.getTime());
	}

	/**
	 * @return the codAnoMes em String
	 */
	public String getCodAnoMesString() {
		return codAnoMes.toString();
	}
	
	/*-------------------------------------------------------*/

	/**
	 * @return the codAnoMes
	 */
	public Integer getCodAnoMes() {
		return codAnoMes;
	}

	/**
	 * @return the codIdentificaPerfil
	 */
	public Short getCodIdentificacaoPerfil() {
		return codIdentificacaoPerfil;
	}

	/**
	 * @return the codVariavelPrimeira
	 */
	public String getCodVariavelPrimeira() {
		return codVariavelPrimeira;
	}

	/**
	 * @return the codVariavelQuarta
	 */
	public String getCodVariavelQuarta() {
		return codVariavelQuarta;
	}

	/**
	 * @return the codVariavelSegunda
	 */
	public String getCodVariavelSegunda() {
		return codVariavelSegunda;
	}

	/**
	 * @return the codVariavelTerceira
	 */
	public String getCodVariavelTerceira() {
		return codVariavelTerceira;
	}

	/**
	 * @return the dataPerfil
	 */
	public Timestamp getDataPerfil() {
		return dataPerfil;
	}

	/**
	 * @return the qtdTotal
	 */
	public Integer getQtdTotal() {
		return qtdTotal;
	}

	/**
	 * @return the valorDesvioDiario
	 */
	public Double getValorDesvioDiario() {
		return valorDesvioDiario;
	}

	/**
	 * @return the valorMedioDiario
	 */
	public Double getValorMedioDiario() {
		return valorMedioDiario;
	}

	/**
	 * @return the valorTotal
	 */
	public Double getValorTotal() {
		return valorTotal;
	}
	
}
