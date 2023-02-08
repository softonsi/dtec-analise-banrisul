package br.com.softon.dtec.entity;

import java.util.Date;

public class Contrato {
	
	/**
	* CD_ANO_FABRIC - Ano de fabricação do bem, objeto do contrato.
	*/
	private Short codAnoFabricacao;

	/**
	* CD_ANO_MODELO - Ano do modelo do bem, objeto do contrato.
	*/
	private Short codAnoModelo;

	/**
	* CD_CANAL_VENDA_CONTRATO - Código que identifica o canal de venda do contrato. Domínio a ser definido.
	*/
	private String codCanalVendaContrato;

	/**
	* CD_CONTRATO - Código identificador do contrato firmado entre o cliente e o banco.
	*/
	private String codContrato;

	/**
	* CD_CONVENIO - Código do convênio, utilizado em consignado.
	*/
	private String codConvenio;

	/**
	* CD_CPF_CNTAT - Número do CPF do vendedor (contato) do banco.
	*/
	private Long codCPFContato;

	/**
	* CD_CPF_CTR_CNTAT - Código de controle do cpf do vendedor (contato) do banco.
	*/
	private Byte codCPFControleContato;

	/**
	* CD_CPF_OFFICER - Código do CPF do officer. Pessoa responsável pelo contrato.
	*/
	private Long codCPFOfficer;	

	/**
	* CD_DOC_IDENTF_BNEFC - Número do documento de identificação, de acordo com o campo CD_TP_IDENTF_CLIE./nSe cd_tp_identf_clie = 2, isto é CPF, armazenar no campo CD_TP_IDENTF_CLIE o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda./nSe cd_tp_identf_clie = 3, isto é CNPJ, armazenar no campo CD_TP_IDENTF_CLIE o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda./n
	*/
	private String codDocIdentificacaoBeneficiario;

	/**
	* CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_IDENTF_CLIE./nSe cd_tp_identf_clie = 2, isto é CPF, armazenar no campo CD_TP_IDENTF_CLIE o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda./nSe cd_tp_identf_clie = 3, isto é CNPJ, armazenar no campo CD_TP_IDENTF_CLIE o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda./n
	*/
	private String codDocIdentificacaoCliente;

	/**
	* CD_FILIAL_BCO - Código da filial do banco, onde o contrato foi firmado.
	*/
	private Long codFilialBanco;

	/**
	* CD_GRP_CONSORCIO - Código do grupo do consórcio. Domínio a ser definido.
	*/
	private String codGrupoConsorcio;

	/**
	* CD_LOTE - Número do lote de processamento.
	*/
	private Long codLote;

	/**
	* CD_MARCA - Código da marca do bem, do objeto do contrato.
	*/
	private Short codMarca;

	/**
	* CD_MODELO - Código do modelo do bem, objeto do contrato.
	*/
	private Integer codModelo;

	/**
	* CD_OFFICER - Código do officer, responsável pelo contrato.
	*/
	private Integer codOfficer;

	/**
	* CD_ORGAO - Código do órgão, utilizado em consignado.
	*/
	private Integer codOrgao;

	/**
	* CD_ORIG_CONTRATO - Código do local/loja onde o contrato foi fechado.
	*/
	private Long codOrigemContrato;

	/**
	* CD_PPSTA - Código da proposta objeto do contrato.
	*/
	private String codProposta;

	/**
	* CD_PRODUTO - Código identificador do produto, ex: veículos, crédito pessoal, consignado, cartão, cdc, entre outros. Este campo tem integridade com a tabela TB_PRODUTO.
	*/
	private String codProduto;

	/**
	* CD_SGMTO_CONTRATO - Código do segmento do contrato. Esta campo tem integridade com a tabela TB_SGMTO_CONTRATO.
	*/
	private Long codSegmentoContrato;

	/**
	* CD_TP_CONVENIO - Tipo de convênio.
	*/
	private String codTipoConvenio;	

	/**
	* CD_TP_IDENTF_BNEFC - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private Byte codTipoIdentificacaoBeneficiario;
	
	/**
	* CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private Byte codTipoIdentificacaoCliente;

	/**
	* CD_TP_PLANO - Código do tipo de plano.
	*/
	private String codTipoPlano;

	/**
	* DT_BASE_CONTRATO - Data base do contrato.
	*/
	private Date dataBaseContrato;

	/**
	* DT_CAD_CONTRATO - Data do cadastro do contrato.
	*/
	private Date dataCadastroContrato;

	/**
	* DT_CANCEL_CONTRATO - Data de cancelamento do contrato.
	*/
	private Date dataCancelamentoContrato;

	/**
	* DT_LIB_CONTRATO - Data de liberação do contrato.
	*/
	private Date dataLiberacaoContrato;

	/**
	* DT_LIQUID_CONTRATO - Data de liquidação do contrato.
	*/
	private Date dataLiquidacaoContrato;

	/**
	* DT_PRIM_PGTO_CONTRATO - Data do primeiro pagamento.
	*/
	private Date dataPrimeiroPagamentoContrato;

	/**
	* DT_PRIM_VCTO_CONTRATO - Data do primeiro vencimento.
	*/
	private Date dataPrimeiroVencimentoContrato;

	/**
	* DT_VENCTO_CONTRATO - Data de vencimento do contrato.
	*/
	private Date dataVencimentoContrato;

	/**
	* FL_AVERB - Código que identifica se o cliente possui ou não averbação. Sendo 0-Não possui averbação e 1-Possui averbação.
	*/
	private Byte flagAverbacao;

	/**
	* FL_BEM_ALIENADO - Código que identifica se o bem foi alienado, sendo 1-alienado e 0-não alienado.
	*/
	private Byte flagBemAlienado;

	/**
	* FL_BEM_APREENDD - Código que identifica se o bem foi apreendido. sendo 1-apreendido e 0-não apreendido.
	*/
	private Byte flagBemApreendido;

	/**
	* FL_CANCEL_CONTRATO - Código que identifica se o contrato foi cancelado. Sendo 1-cancelado e 0-não cancelado.
	*/
	private Byte flagContratoCancelado;

	/**
	* FL_DUT - Código que identifica se o dut foi entregue, sendo 1-entregue, 0-não entregue.
	*/
	private Byte flagDUT;

	/**
	* FL_GARNT_ALIEN - Código que identifica se a garantia do contrato está alienada. Sendo 0-Não alienada e 1-Alienada.
	*/
	private Byte flagGarantiaAlienada;

	/**
	* FL_RFINAM - Código que identifica se o contrato se refere a um refinanciamento, sendo: 0-Não é refinanciamento e 1-É refinanciamento.
	*/
	private Byte flagRefinanciamento;

	/**
	* FL_TAR_CAD_FINCD - Código que identifica tarifa de cadastro financiada, sendo 1-sim e 0-não.
	*/
	private Byte flagTarifaCadastroFinanciada;

	/**
	* NM_CLIE - Nome do Cliente.
	*/
	private String nomeCliente;

	/**
	* NM_OFFICER - Nome do officer. Pessoa responsável pelo contrato.
	*/
	private String nomeOfficer;

	/**
	* NM_OPERD_CONTRATO - Nome do operador do contrato.
	*/
	private String nomeOperadorContrato;

	/**
	* NM_SIST_ORIG - Nome do sistema de origem da transação/contrato.
	*/
	private String nomeSistemaOrigem;

	/**
	* QT_COTA_CONSORCIO - Cota do consórcio.
	*/
	private Integer qtdCotaConsorcio;

	/**
	* QT_DIA_CARENCIA_CONTRATO - Quantidade de dias de carência do contrato.
	*/
	private Short qtdDiaCarenciaContrato;

	/**
	* QT_MES_CONTT - Quantidade de prestações (meses) contratadas.
	*/
	private Short qtdPrestacoesContratadas;

	/**
	* QT_MES_PAGO - Quantidade de prestações (meses) pagas.
	*/
	private Short qtdPrestacoesPagas;

	/**
	* VL_FINCD_CONTRATO - Valor do contrato financiado.
	*/
	private Double valorFinanciadoContrato;

	/**
	* VL_IOF_CONTRATO - Valor do IOF do contrato.
	*/
	private Double valorIOFContrato;

	/**
	* VL_LIQUID - Valor líquido do contrato.
	*/
	private Double valorLiquidoContrato;

	/**
	* VL_OPER_CONTRATO - Valor da operação do contrato.
	*/
	private Double valorOperacaoContrato;

	/**
	* VL_PCELA_CONTRATO - Valor da parcela do contrato.
	*/
	private Double valorParcelaContrato;

	/**
	* VL_PRINC_CONTRATO - Valor principal do contrato.
	*/
	private Double valorPrincipalContrato;

	/**
	* VL_REBATE_PGTO - Valor do rebate pago.
	*/
	private Double valorRebatePago;

	/**
	* VL_SEGURO_CONTRATO - Valor do seguro do contrato.
	*/
	private Double valorSeguroContrato;

	/**
	* VL_TAR_CAD_CONTRATO - Valor da tarifa de cadastro do contrato.
	*/
	private Double valorTarifaCadastroContrato;

	/**
	* VL_TAXA_CONTRATO - Valor da taxa do contrato.
	*/
	private Double valorTaxaContrato;

	/**
	* VL_TAXA_EFETIVO - Valor da taxa efetiva.
	*/
	private Double valorTaxaEfetivo;

	/**
	* VL_TAXA_OPER_CONTRATO - Valor da taxa de operação do contrato.
	*/
	private Double valorTaxaOperacaoContrato;

	/**
	* VL_TAXA_REBATE - Valor da taxa de rebate.
	*/
	private Double valorTaxaRebate;

	public Contrato(Short codAnoFabricacao, Short codAnoModelo,
			String codCanalVendaContrato, String codContrato,
			String codConvenio, Long codCPFContato, Byte codCPFControleContato,
			Long codCPFOfficer, String codDocIdentificacaoCliente,
			String codDocIdentificacaoBeneficiario,
			Long codFilialBanco, String codGrupoConsorcio, Long codLote,
			Short codMarca, Integer codModelo, Integer codOfficer,
			Integer codOrgao, Long codOrigemContrato, String codProposta,
			String codProduto, Long codSegmentoContrato,
			String codTipoConvenio, Byte codTipoIdentificacaoCliente,
			Byte codTipoIdentificacaoBeneficiario,
			String codTipoPlano, Date dataBaseContrato,
			Date dataCadastroContrato, Date dataCancelamentoContrato,
			Date dataLiberacaoContrato, Date dataLiquidacaoContrato,
			Date dataPrimeiroPagamentoContrato,
			Date dataPrimeiroVencimentoContrato, Date dataVencimentoContrato,
			Byte flagAverbacao, Byte flagBemAlienado, Byte flagBemApreendido,
			Byte flagContratoCancelado, Byte flagDUT,
			Byte flagGarantiaAlienada, Byte flagRefinanciamento,
			Byte flagTarifaCadastroFinanciada, String nomeCliente,
			String nomeOfficer, String nomeOperadorContrato,
			String nomeSistemaOrigem, String qtdCotaConsorcio,
			Short qtdDiaCarenciaContrato, Short qtdPrestacoesContratadas,
			Short qtdPrestacoesPagas, Double valorFinanciadoContrato,
			Double valorIOFContrato, Double valorLiquidoContrato,
			Double valorOperacaoContrato, Double valorParcelaContrato,
			Double valorPrincipalContrato, Double valorRebatePago,
			Double valorSeguroContrato, Double valorTarifaCadastroContrato,
			Double valorTaxaContrato, Double valorTaxaEfetivo,
			Double valorTaxaOperacaoContrato, Double valorTaxaRebate) {
		super();
		this.codAnoFabricacao = codAnoFabricacao;
		this.codAnoModelo = codAnoModelo;
		this.codCanalVendaContrato = codCanalVendaContrato;
		this.codContrato = codContrato;
		this.codConvenio = codConvenio;
		this.codCPFContato = codCPFContato;
		this.codCPFControleContato = codCPFControleContato;
		this.codCPFOfficer = codCPFOfficer;
		this.codDocIdentificacaoBeneficiario = codDocIdentificacaoBeneficiario;
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codFilialBanco = codFilialBanco;
		this.codGrupoConsorcio = codGrupoConsorcio;
		this.codLote = codLote;
		this.codMarca = codMarca;
		this.codModelo = codModelo;
		this.codOfficer = codOfficer;
		this.codOrgao = codOrgao;
		this.codOrigemContrato = codOrigemContrato;
		this.codProposta = codProposta;
		this.codProduto = codProduto;
		this.codSegmentoContrato = codSegmentoContrato;
		this.codTipoConvenio = codTipoConvenio;
		this.codTipoIdentificacaoBeneficiario = codTipoIdentificacaoBeneficiario;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.codTipoPlano = codTipoPlano;
		this.dataBaseContrato = dataBaseContrato;
		this.dataCadastroContrato = dataCadastroContrato;
		this.dataCancelamentoContrato = dataCancelamentoContrato;
		this.dataLiberacaoContrato = dataLiberacaoContrato;
		this.dataLiquidacaoContrato = dataLiquidacaoContrato;
		this.dataPrimeiroPagamentoContrato = dataPrimeiroPagamentoContrato;
		this.dataPrimeiroVencimentoContrato = dataPrimeiroVencimentoContrato;
		this.dataVencimentoContrato = dataVencimentoContrato;
		this.flagAverbacao = flagAverbacao;
		this.flagBemAlienado = flagBemAlienado;
		this.flagBemApreendido = flagBemApreendido;
		this.flagContratoCancelado = flagContratoCancelado;
		this.flagDUT = flagDUT;
		this.flagGarantiaAlienada = flagGarantiaAlienada;
		this.flagRefinanciamento = flagRefinanciamento;
		this.flagTarifaCadastroFinanciada = flagTarifaCadastroFinanciada;
		this.nomeCliente = nomeCliente;
		this.nomeOfficer = nomeOfficer;
		this.nomeOperadorContrato = nomeOperadorContrato;
		this.nomeSistemaOrigem = nomeSistemaOrigem;
		// Paleativo segundo ordem do sr. Luiz no dia 05/04/2017
		try {
			this.qtdCotaConsorcio = Integer.parseInt(qtdCotaConsorcio);
		} catch (NumberFormatException e) {
			this.qtdCotaConsorcio = null;
		}
		this.qtdDiaCarenciaContrato = qtdDiaCarenciaContrato;
		this.qtdPrestacoesContratadas = qtdPrestacoesContratadas;
		this.qtdPrestacoesPagas = qtdPrestacoesPagas;
		this.valorFinanciadoContrato = valorFinanciadoContrato;
		this.valorIOFContrato = valorIOFContrato;
		this.valorLiquidoContrato = valorLiquidoContrato;
		this.valorOperacaoContrato = valorOperacaoContrato;
		this.valorParcelaContrato = valorParcelaContrato;
		this.valorPrincipalContrato = valorPrincipalContrato;
		this.valorRebatePago = valorRebatePago;
		this.valorSeguroContrato = valorSeguroContrato;
		this.valorTarifaCadastroContrato = valorTarifaCadastroContrato;
		this.valorTaxaContrato = valorTaxaContrato;
		this.valorTaxaEfetivo = valorTaxaEfetivo;
		this.valorTaxaOperacaoContrato = valorTaxaOperacaoContrato;
		this.valorTaxaRebate = valorTaxaRebate;
	}

	/**
	 * @return the codDocIdentificacaoBeneficiario
	 */
	public String getCodDocIdentificacaoBeneficiario() {
		return codDocIdentificacaoBeneficiario;
	}

	/**
	 * @return the codTipoIdentificacaoBeneficiario
	 */
	public Byte getCodTipoIdentificacaoBeneficiario() {
		return codTipoIdentificacaoBeneficiario;
	}

	/**
	 * @return the codAnoFabricacao
	 */
	public Short getCodAnoFabricacao() {
		return codAnoFabricacao;
	}

	/**
	 * @return the codAnoModelo
	 */
	public Short getCodAnoModelo() {
		return codAnoModelo;
	}

	/**
	 * @return the codCanalVendaContrato
	 */
	public String getCodCanalVendaContrato() {
		return codCanalVendaContrato;
	}

	/**
	 * @return the codContrato
	 */
	public String getCodContrato() {
		return codContrato;
	}

	/**
	 * @return the codConvenio
	 */
	public String getCodConvenio() {
		return codConvenio;
	}

	/**
	 * @return the codCPFContato
	 */
	public Long getCodCPFContato() {
		return codCPFContato;
	}

	/**
	 * @return the codCPFControleContato
	 */
	public Byte getCodCPFControleContato() {
		return codCPFControleContato;
	}

	/**
	 * @return the codCPFOfficer
	 */
	public Long getCodCPFOfficer() {
		return codCPFOfficer;
	}

	/**
	 * @return the codDocIdentificacaoCliente
	 */
	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}

	/**
	 * @return the codFilialBanco
	 */
	public Long getCodFilialBanco() {
		return codFilialBanco;
	}

	/**
	 * @return the codGrupoConsorcio
	 */
	public String getCodGrupoConsorcio() {
		return codGrupoConsorcio;
	}

	/**
	 * @return the codLote
	 */
	public Long getCodLote() {
		return codLote;
	}

	/**
	 * @return the codMarca
	 */
	public Short getCodMarca() {
		return codMarca;
	}

	/**
	 * @return the codModelo
	 */
	public Integer getCodModelo() {
		return codModelo;
	}

	/**
	 * @return the codOfficer
	 */
	public Integer getCodOfficer() {
		return codOfficer;
	}

	/**
	 * @return the codOrgao
	 */
	public Integer getCodOrgao() {
		return codOrgao;
	}

	/**
	 * @return the codOrigemContrato
	 */
	public Long getCodOrigemContrato() {
		return codOrigemContrato;
	}

	/**
	 * @return the codProposta
	 */
	public String getCodProposta() {
		return codProposta;
	}

	/**
	 * @return the codProduto
	 */
	public String getCodProduto() {
		return codProduto;
	}

	/**
	 * @return the codSegmentoContrato
	 */
	public Long getCodSegmentoContrato() {
		return codSegmentoContrato;
	}

	/**
	 * @return the codTipoConvenio
	 */
	public String getCodTipoConvenio() {
		return codTipoConvenio;
	}

	/**
	 * @return the codTipoIdentificacaoCliente
	 */
	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}

	/**
	 * @return the codTipoPlano
	 */
	public String getCodTipoPlano() {
		return codTipoPlano;
	}

	/**
	 * @return the dataBaseContrato
	 */
	public Date getDataBaseContrato() {
		return dataBaseContrato;
	}

	/**
	 * @return the dataCadastroContrato
	 */
	public Date getDataCadastroContrato() {
		return dataCadastroContrato;
	}

	/**
	 * @return the dataCancelamentoContrato
	 */
	public Date getDataCancelamentoContrato() {
		return dataCancelamentoContrato;
	}

	/**
	 * @return the dataLiberacaoContrato
	 */
	public Date getDataLiberacaoContrato() {
		return dataLiberacaoContrato;
	}

	/**
	 * @return the dataLiquidacaoContrato
	 */
	public Date getDataLiquidacaoContrato() {
		return dataLiquidacaoContrato;
	}

	/**
	 * @return the dataPrimeiroPagamentoContrato
	 */
	public Date getDataPrimeiroPagamentoContrato() {
		return dataPrimeiroPagamentoContrato;
	}

	/**
	 * @return the dataPrimeiroVencimentoContrato
	 */
	public Date getDataPrimeiroVencimentoContrato() {
		return dataPrimeiroVencimentoContrato;
	}

	/**
	 * @return the dataVencimentoContrato
	 */
	public Date getDataVencimentoContrato() {
		return dataVencimentoContrato;
	}

	/**
	 * @return the flagAverbacao
	 */
	public Byte getFlagAverbacao() {
		return flagAverbacao;
	}

	/**
	 * @return the flagBemAlienado
	 */
	public Byte getFlagBemAlienado() {
		return flagBemAlienado;
	}

	/**
	 * @return the flagBemApreendido
	 */
	public Byte getFlagBemApreendido() {
		return flagBemApreendido;
	}

	/**
	 * @return the flagContratoCancelado
	 */
	public Byte getFlagContratoCancelado() {
		return flagContratoCancelado;
	}

	/**
	 * @return the flagDUT
	 */
	public Byte getFlagDUT() {
		return flagDUT;
	}

	/**
	 * @return the flagGarantiaAlienada
	 */
	public Byte getFlagGarantiaAlienada() {
		return flagGarantiaAlienada;
	}

	/**
	 * @return the flagRefinanciamento
	 */
	public Byte getFlagRefinanciamento() {
		return flagRefinanciamento;
	}

	/**
	 * @return the flagTarifaCadastroFinanciada
	 */
	public Byte getFlagTarifaCadastroFinanciada() {
		return flagTarifaCadastroFinanciada;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}

	/**
	 * @return the nomeOfficer
	 */
	public String getNomeOfficer() {
		return nomeOfficer;
	}

	/**
	 * @return the nomeOperadorContrato
	 */
	public String getNomeOperadorContrato() {
		return nomeOperadorContrato;
	}

	/**
	 * @return the nomeSistemaOrigem
	 */
	public String getNomeSistemaOrigem() {
		return nomeSistemaOrigem;
	}

	/**
	 * @return the qtdCotaConsorcio
	 */
	public Integer getQtdCotaConsorcio() {
		return qtdCotaConsorcio;
	}

	/**
	 * @return the qtdDiaCarenciaContrato
	 */
	public Short getQtdDiaCarenciaContrato() {
		return qtdDiaCarenciaContrato;
	}

	/**
	 * @return the qtdPrestacoesContratadas
	 */
	public Short getQtdPrestacoesContratadas() {
		return qtdPrestacoesContratadas;
	}

	/**
	 * @return the qtdPrestacoesPagas
	 */
	public Short getQtdPrestacoesPagas() {
		return qtdPrestacoesPagas;
	}

	/**
	 * @return the valorFinanciadoContrato
	 */
	public Double getValorFinanciadoContrato() {
		return valorFinanciadoContrato;
	}

	/**
	 * @return the valorIOFContrato
	 */
	public Double getValorIOFContrato() {
		return valorIOFContrato;
	}

	/**
	 * @return the valorLiquidoContrato
	 */
	public Double getValorLiquidoContrato() {
		return valorLiquidoContrato;
	}

	/**
	 * @return the valorOperacaoContrato
	 */
	public Double getValorOperacaoContrato() {
		return valorOperacaoContrato;
	}

	/**
	 * @return the valorParcelaContrato
	 */
	public Double getValorParcelaContrato() {
		return valorParcelaContrato;
	}

	/**
	 * @return the valorPrincipalContrato
	 */
	public Double getValorPrincipalContrato() {
		return valorPrincipalContrato;
	}

	/**
	 * @return the valorRebatePago
	 */
	public Double getValorRebatePago() {
		return valorRebatePago;
	}

	/**
	 * @return the valorSeguroContrato
	 */
	public Double getValorSeguroContrato() {
		return valorSeguroContrato;
	}

	/**
	 * @return the valorTarifaCadastroContrato
	 */
	public Double getValorTarifaCadastroContrato() {
		return valorTarifaCadastroContrato;
	}

	/**
	 * @return the valorTaxaContrato
	 */
	public Double getValorTaxaContrato() {
		return valorTaxaContrato;
	}

	/**
	 * @return the valorTaxaEfetivo
	 */
	public Double getValorTaxaEfetivo() {
		return valorTaxaEfetivo;
	}

	/**
	 * @return the valorTaxaOperacaoContrato
	 */
	public Double getValorTaxaOperacaoContrato() {
		return valorTaxaOperacaoContrato;
	}

	/**
	 * @return the valorTaxaRebate
	 */
	public Double getValorTaxaRebate() {
		return valorTaxaRebate;
	}
	
}
