package br.com.softon.dtec.entity;

import java.util.Date;

public class ControleSimulacao {

	/**
	*     CD_IDENTF_SIMULA
	*/
	private Long codIdentificadorSimulacao;

	/**
	*     CD_LOTE
	*/
	private Long codLote;

	/**
	*     DT_FIM_ANLSE
	*/
	private Date dataFimAnalise;

	/**
	*     DT_INICIO_ANLSE
	*/
	private Date dataInicioAnalise;

	/**
	*     DT_SIMULA
	*/
	private Date dataSimulacao;

	/**
	*     QT_CLIE_PROCES
	*/
	private Long qtdClientesProcessados;

	/**
	*     QT_CLIE_SUSP
	*/
	private Long qtdClientesSuspeitos;

	/**
	*     VL_PONTO_CORTE
	*/
	private Integer valorPontoCorte;
	

	public ControleSimulacao() {
		super();
	}
	
	public ControleSimulacao(Long codIdentificadorSimulacao, Long codLote,
			Date dataFimAnalise, Date dataInicioAnalise, Date dataSimulacao,
			Long qtdClientesProcessados, Long qtdClientesSuspeitos,
			Integer valorPontoCorte) {
		super();
		this.codIdentificadorSimulacao = codIdentificadorSimulacao;
		this.codLote = codLote;
		this.dataFimAnalise = dataFimAnalise;
		this.dataInicioAnalise = dataInicioAnalise;
		this.dataSimulacao = dataSimulacao;
		this.qtdClientesProcessados = qtdClientesProcessados;
		this.qtdClientesSuspeitos = qtdClientesSuspeitos;
		this.valorPontoCorte = valorPontoCorte;
	}

	/**
	 * @return the codIdentificadorSimulacao
	 */
	public Long getCodIdentificadorSimulacao() {
		return codIdentificadorSimulacao;
	}

	/**
	 * @return the codLote
	 */
	public Long getCodLote() {
		return codLote;
	}

	/**
	 * @return the dataFimAnalise
	 */
	public Date getDataFimAnalise() {
		return dataFimAnalise;
	}

	/**
	 * @return the dataInicioAnalise
	 */
	public Date getDataInicioAnalise() {
		return dataInicioAnalise;
	}

	/**
	 * @return the dataSimulacao
	 */
	public Date getDataSimulacao() {
		return dataSimulacao;
	}

	/**
	 * @return the qtdClientesProcessados
	 */
	public Long getQtdClientesProcessados() {
		return qtdClientesProcessados;
	}

	/**
	 * @return the qtdClientesSuspeitos
	 */
	public Long getQtdClientesSuspeitos() {
		return qtdClientesSuspeitos;
	}

	/**
	 * @param qtdClientesProcessados the qtdClientesProcessados to set
	 */
	public void setQtdClientesProcessados(Long qtdClientesProcessados) {
		this.qtdClientesProcessados = qtdClientesProcessados;
	}

	/**
	 * @param qtdClientesSuspeitos the qtdClientesSuspeitos to set
	 */
	public void setQtdClientesSuspeitos(Long qtdClientesSuspeitos) {
		this.qtdClientesSuspeitos = qtdClientesSuspeitos;
	}

	/**
	 * @return the valorPontoCorte
	 */
	public Integer getValorPontoCorte() {
		return valorPontoCorte;
	}
	
}