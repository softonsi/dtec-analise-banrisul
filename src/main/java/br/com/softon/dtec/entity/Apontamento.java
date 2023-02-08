package br.com.softon.dtec.entity;

import java.sql.Timestamp;
import java.util.Queue;

public class Apontamento {
	
	/**CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF*/
	private final String codDocIdentificacaoCliente;

	/**CD_LOTE - Número do lote, referente ao inicio do apontamento.*/
	private final Long codigoLote;
	
	/**CD_IDENTF_SIMULA - Chave do registro quando utilizado para fins de simulação*/
	private Long codigoIdentfSimula;

	/**CD_STTUS_EVENTO_ATUAL - Código do status do evento. Este campo tem integridade com a tabela TB_STTUS_EVENTO.*/
	private final Byte codigoStatusEventoAtual;

	/**CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n*/
	private final Byte codTipoIdentificacaoCliente;

	/**DT_APONTAMENTO - Data e hora do apontamento.*/
	private final Timestamp dataApontamento;

	/**DT_ATUALZ_CALCULO - Data do Cálculo ou da atualização do apontamento*/
	private Timestamp dataAtualizacaoCalculo;

	/**DT_ULTM_PARECER - Data do último parecer atribuído ao cliente*/
	private Timestamp dataUltimoParecer;

	/**FL_CARENCIA - Apresenta a conclusão da Análise de Carência./nO módulo de análise verifica se existe apontamento finalizado, onde exatamente as mesmas regras tenham sido apontadas dentro de período de carência. Se existir o campo FL_CARÊNCIA é atualizado para 1 (Sim). Isto é, aquele tipo de apontamento está dentro do período de carência, caso contrário, o campo é atualizado para 0 (Não). O default é nulo, isto é, a análise de carência não foi realizada./nO período de carência é um parâmetro definido pelo usuário e armazenado na tabela de parâmetros globais./n/n/n*/
	private final Byte flagCarencia;

	/**FL_PONTO_CORTE - Apresenta a conclusão da análise de Ponto de Corte./nPara a análise do ponto de corte, a pontuação das regras é calculada ou recalculada, considerando a parametrização atual, na tabela de Regras. Caso a pontuação total esteja abaixo do ponto de corte, o campo FL_PONTO_CORTE é atualizado para 1 (Sim), isto é, o apontamento está dentro do ponto de corte, caso esteja acima, é atualizado para 0 (Não), isto é, o apontamento não está mais dentro. /nO valor do ponto de corte é um parâmetro definido pelo usuário e armazenado na tabela de parâmetros globais./n*/
	private final Byte flagPontoCorte;

	/**FL_SUSP_LD - Para os apontamentos com status de análise diferente de "Finalizado" e com o flag FL_CARENCIA = 0 e FL_PONTO_CORTE = 0, o cliente é considerado suspeito de Lavagem de Dinheiro pelo sistema e o campo FL_SUSP_LD é atualizado para 1 (Sim), default é 0 (Não).*/
	private Byte flagSuspeitoLD;

	/**VL_APONTAMENTO - Valor de apontamento do cliente. Este valor é o somatório das pontuações das regras apontadas para o cliente e registradas nas tabelas TB_CLIE_REGRA e TB_TRANS_REGRA*/
	private Short valorApontamento;

	/**VL_PONTO_CORTE - Valor do ponto de corte, utilizado no momento do apontamento.*/
	private final Short valorPontoCorte;
	
	public Apontamento(String codDocIdentificacaoCliente, 
			Byte codTipoIdentificacaoCliente, Timestamp dataApontamento,
			Short valorApontamento, Short valorPontoCorte,
			Byte flagSuspeitoLD,
			Long codigoLote, Long codigoIdentfSimula
			) {
		super();
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codigoLote = codigoLote;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.flagSuspeitoLD = flagSuspeitoLD;
		this.valorApontamento = valorApontamento;
		this.valorPontoCorte = valorPontoCorte;
		this.flagPontoCorte = null;
		this.flagCarencia = null;
		this.dataApontamento = dataApontamento;
		this.codigoStatusEventoAtual = null;
		this.codigoIdentfSimula = codigoIdentfSimula;
	}

	public Apontamento(String codDocIdentificacaoCliente, Long codigoLote,
			Byte codigoStatusEventoAtual, Byte codTipoIdentificacaoCliente,
			Timestamp dataApontamento, Timestamp dataAtualizacaoCalculo,
			Byte flagCarencia, Byte flagPontoCorte, Byte flagSuspeitoLD,
			Short valorApontamento, Short valorPontoCorte) {
		super();
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codigoLote = codigoLote;
		this.codigoStatusEventoAtual = codigoStatusEventoAtual;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.dataApontamento = dataApontamento;
		this.dataAtualizacaoCalculo = dataAtualizacaoCalculo;
		this.flagCarencia = flagCarencia;
		this.flagPontoCorte = flagPontoCorte;
		this.flagSuspeitoLD = flagSuspeitoLD;
		this.valorApontamento = valorApontamento;
		this.valorPontoCorte = valorPontoCorte;
	}

	public Apontamento(String codDocIdentificacaoCliente, Byte codTipoIdentificacaoCliente,
			Timestamp dataApontamento) {
		super();
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codigoLote = null;
		this.codigoStatusEventoAtual = null;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.dataApontamento = dataApontamento;
		this.dataAtualizacaoCalculo = null;
		this.flagCarencia = null;
		this.flagPontoCorte = null;
		this.flagSuspeitoLD = null;
		this.valorApontamento = null;
		this.valorPontoCorte = null;
	}
	
	// métodos de apoio ao gerador de apontamentos...
	
	private Queue<TransacaoAlertada> listaTransacaoAlertada;

	public Queue<TransacaoAlertada> getListaTransacaoAlertada() {
		return listaTransacaoAlertada;
	}

	public void setListaTransacaoAlertada(Queue<TransacaoAlertada> listaTransacaoAlertada) {
		this.listaTransacaoAlertada = listaTransacaoAlertada;
	}
	
	private Boolean novoApontamento = false;	

	/**
	 * @return the novoApontamento
	 */
	public Boolean getNovoApontamento() {
		return novoApontamento;
	}

	/**
	 * @param novoApontamento the novoApontamento to set
	 */
	public void setNovoApontamento(Boolean novoApontamento) {
		this.novoApontamento = novoApontamento;
	}

	
	/**
	 * Este atributo deverá ser atualizado quando houver apontamento já registrado
	 * @param dataAtualizacaoCalculo the dataAtualizacaoCalculo to set
	 */
	public void setDataAtualizacaoCalculo(Timestamp dataAtualizacaoCalculo) {
		this.dataAtualizacaoCalculo = dataAtualizacaoCalculo;
	}

	/**
	 * @return the dataUltimoParecer
	 */
	public Timestamp getDataUltimoParecer() {
		return dataUltimoParecer;
	}

	/**
	 * @param dataUltimoParecer the dataUltimoParecer to set
	 */
	public void setDataUltimoParecer(Timestamp dataUltimoParecer) {
		this.dataUltimoParecer = dataUltimoParecer;
	}

	/**
	 * Este atributo deverá ser atualizado quando houver apontamento já registrado
	 * @param valorApontamento the valorApontamento to set
	 */
	public void setValorApontamento(Short valorApontamento) {
		this.valorApontamento = valorApontamento;
	}

	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}

	public Long getCodigoLote() {
		return codigoLote;
	}

	public Byte getCodigoStatusEventoAtual() {
		return codigoStatusEventoAtual;
	}

	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}

	public Timestamp getDataApontamento() {
		return dataApontamento;
	}

	public Timestamp getDataAtualizacaoCalculo() {
		return dataAtualizacaoCalculo;
	}

	public Byte getFlagCarencia() {
		return flagCarencia;
	}

	public Byte getFlagPontoCorte() {
		return flagPontoCorte;
	}

	public Byte getFlagSuspeitoLD() {
		return flagSuspeitoLD;
	}

	public Short getValorApontamento() {
		return valorApontamento;
	}

	public Short getValorPontoCorte() {
		return valorPontoCorte;
	}

	public void setFlagSuspeitoLD(boolean flagSuspeitoLD) {
		this.flagSuspeitoLD = (byte) (flagSuspeitoLD ? 1 : 0);
	}

	public Long getCodigoIdentfSimula() {
		return codigoIdentfSimula;
	}

}
