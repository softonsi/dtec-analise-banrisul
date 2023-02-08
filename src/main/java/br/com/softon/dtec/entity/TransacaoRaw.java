package br.com.softon.dtec.entity;

// Generated 16/11/2009 15:49:53 by Hibernate Tools 3.2.5.Beta

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.softon.dtec.utils.DateHandle;

public class TransacaoRaw {

    /**
     * Código de barra do bloqueto.
     */
    private BigInteger codigoBarra;

    /**
     * Número do cartão (midia). Informação adicional que deve ser preenchida para todas
     * as transações que são realizadas com midia presente ou não presente (cartão de
     * débito, cartão de crédito, cartão do cidadão, construcard).
     */
    private String codigoCartao;

    /**
     * Número que representa a classificação dos códigos de transações bancárias da CEF
     * em Natureza (crédito, débito ou evento), serviço (tipo de operação), canal e forma
     * (cheque, espécie, etc), que são interpretadas pelo SISAF.
     */
    private String codigoCodificacaoTransacao;

    /**
     * Código que indica se a transação foi: 1-estornada, 2-cancelada, 3-acertada ou 4-
     * conciliada. Informação adicional que devem ser preenchidas quando a transação for
     * um comando de estorno, cancelamento, acerto ou conciliação.
     */
    private Short codigoComandoAcerto;

    /**
     * Código que identifica a câmara de compensação. Ex: 086-Goiânia. Informação
     * adicional que deve ser preenchida quando a transação for um lançamento
     * envolvendo cheques (compensação, depósito).
     */
    private Long codigoCompensacao;

    /**
     * Número do convênio, obtido em tabela específica.
     */
    private String codigoConvenio;

    /**
     * Código que indica que a transação representa o comando de: 1-estorno, 2-cancelamento, 3-acerto e 4-conciliação.
     */
    private Short codigoDetalheComando;

    /**
     * Identificador do código do cartão, número do cheque, etc.
     */
    private String codigoDocumento;

    /**
     * Código que identifica o fundo de investimento. Informação adicional que deve ser
     * preenchida quando a transação envolver resgates, aplicações e consultas de
     * investimentos.
     */
    private Short codigoFundoInvestimento;

    /**
     * Código identificador da transferência TED.
     */
    private Long codigoIdentificadorTed;

    /**
     * Código identificador do cliente.
     */
    private BigInteger codigoIdentificadorCliente;

    /**
     * Código identificador do cliente origem/destino.
     */
    private BigInteger codigoIdentificadorClienteOrigemDestino;

    /**
     * Código identificador da conta da transação.
     */
    private BigInteger codigoIdentificadorConta;

    /**
     * Número da conta de origem/destino da transação, podendo ser Caixa ou outros.
     */
    private BigInteger codigoIdentificadorContaOrigemDestino;

    /**
     * Modalidade ted, como disponível nos serviços de TED. Ex. TED PAG0108.
     */
    private Long codigoModalidadeTed;

    /**
     * Código que identifica o motivo da contra ordem Ex: 11- 1a. devolução sem fundos.
     * Informação adicional que deve ser preenchida quando a transação for um
     * lançamento envolvendo cheques (compensação, depósito).
     */
    private Byte codigoMotivoContraordem;

    /**
     * Código da placa do veículo relacionado à transação.
     */
    private String codigoPlacaVeiculo;

    /**
     * Código da prefeitura. Informação adicional que deve ser preenchida quando o
     * lançamento for um pagamento de receita municipal.
     */
    private Long codigoPrefeitura;

    /**
     * Código da receita federal. Informação que deve ser preenchida quando a transação
     * for um pagamento de DARF.
     */
    private Long codigoReceitaFederal;

    /**
     * Identificador do Código de Resposta conforme ISO 8583.
     */
    private String codigoRespostaIso;

    /**
     * Número que identifica o serviço, isto é, do tipo de transação realizada pelo cliente.
     * Exemplo: saque, tentativa de saque,  transferencia, doc, compra, etc
     */
    private String codigoServico;

    /**
     * Código que identifica um terminal, utilizado para realização de transações bancárias.
     */
    private String codigoTerminal;

    /**
     * Tipo de Chave. Ex: 1-Assinatura Eletrônica, 2-Senha internet, 3-Senha 4 dígitos, 5-Código de Acesso, 6-CVV2, 7-CVC, 8-TOKEN, etc. Informação adicional que deve
     * ser preenchida se a transação envolver bloqueio/desbloqueio/cancelamento/cadastramento de senha, assinatura eletrônica,
     * cartão, token, senha internet, etc.
     */
    private Short codigoTipoChave;

    /**
     * Código que identifica o tipo de darf.
     */
    private Short codigoTipoDarf;

    /**
     * Indica o tipo de empresa, sendo: 1-empregado Caixa, 2- prestador Caixa, 3-lotérico e 4-CB.
     */
    private Short codigoTipoEmpresa;

    /**
     * Código que identifica o tipo de limite para realização de transações.
     */
    private Short codigoTipoLimite;

    /**
     * Identificador do Tipo de Mensagem conforme ISO 8583.
     */
    private String codigoTipoMensagemIso;

    /**
     * Indica o tipo de negócio do estabelecimento.
     */
    private Short codigoTipoNegocio;

    /**
     * Código que identifica o tipo de pagamento do veículo.
     */
    private Short codigoTipoPagamentoDETRAN;

    /**
     * Código que identifica a transação de início.
     */
    private String codigoTransacaoInicio;

    /**
     * Dia do mês em que a parcela vence.  Informação adicional que deve ser preenchida
     * quando o lançamento for um crédito de empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Byte diaVencimentoParcela;

    /**
     * Detalhes da transação.
     */
    private String detalheTransacao;

    /**
     * Data e hora em que o comando foi efetuado pelo cliente. Fuso horário do local onde
     * foi efetuada a transação.
     */
    private Date dataComandoCliente;

    /**
     * Dia, mês e ano apropriados para o lançamento contábil do valor da Transação.
     */
    private Date dataContabilTransacao;

    /**
     * Data de vencimento (referente a tabela de Pagamento) ou Data de vencimento do
     * bloqueto de cobrança.
     */
    private Date dataVencimentoPagamento;

    /**
     * Indica transação que foi efetivada mediante a digitação do número do cartão de
     * crédito.
     */
    private Boolean indicadorCartaoDigitacao;

    /**
     * Identificação do cartão utilizado na Transação.
     * 001 - Cartão de Débito - Trilha
     * 002 - Cartão de Crédito - Número do Cartão;
     * 003 - Cartão do Cidadão - Número do NIS;
     * 004 - Cartão do INSS - Número do Benefício;
     * 005 - Operação sem cartão no terminal do Caixa - Número da matrícula do funcionário CAIXA;
     * 006 - Operação sem cartão com autorização gerencial - Número da matrícula do funcionário CAIXA com a função de Gerente;
     * 007 - Internet - Endereço IP da estação do Cliente
     */
    private Short indicadorChave;

    /**
     * Indicador do Tipo Chave Secundária.
     * "A" Código de Acesso;
     * "P" Palavra Secreta;
     * "E" Assinatura Eletrônica.
     * "O" Outros.
     */
    private Character indicadorChaveSecundaria;

    /**
     * Indica se a mídia tem chip, sendo 1-sim e 0-não.
     */
    private Boolean indicadorChip;

    /**
     * Indica transação que foi efetivada mediante a digitação do número da AG-OPER-CONTA-DV.
     */
    private Boolean indicadorContaDigitacao;

    /**
     * Indica que o cliente optou por pagamento em débito em conta, sendo 1-sim e 0-não.
     * Informação adicional que deve ser preenchida quando o lançamento for um crédito
     * de empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Boolean indicadorDebitoConta;

    /**
     * Indica se a data/hora da autorização da transação é um dia útil.
     */
    private Boolean indicadorDiaUtil;

    /**
     * Indica que o empréstimo foi efetuado de forma automática. Ex. CDC, Giro Caixa.
     * Sendo 1-Sim e 0-Não.  Informação adicional que deve ser preenchida quando o
     * lançamento for um crédito de empréstimo, lançamento de prestações ou consultas
     * pontuais.
     */
    private Boolean indicadorEmprestimoAutomatico;

    /**
     * Indica se a data/hora da autorização da transação é um feriado.
     */
    private Boolean indicadorFeriado;

    /**
     * Indica se a transação foi identificada como fraude ou não, sendo 1-sim e 0-não.
     */
    private Boolean indicadorFraude;

    /**
     * Indica a natureza da operação
     */
    private Byte indicadorNaturezaOperacao;

    /**
     * Indicador de Transação Batch ou Online. "S" para transações on-line e "N" para
     * transações batch.
     */
    private Character indicadorOrigemTransacao;

    /**
     * Indica a situação do chip.
     */
    private Short indicadorSituacaoChip;

    /**
     * Indica a situação da leitora.
     */
    private Short indicadorSituacaoLeitora;

    /**
     * Indicador de uso de um PIN (Personal Number) (Número de identificação pessoal).
     * Um dos códigos da trilha do cartão (senha) na transação: 0-não, 1-sim.
     * <p/>
     * TODO - Modificar campo no Banco de Dados para aceitar Boolean!
     */
    private String indicadorUsoSenha;

    /**
     * Número da agência da transação.
     */
    private Integer numeroAgencia;

    /**
     * Número da agência da transação, podendo ser Caixa ou outros.
     */
    private Integer numeroAgenciaOrigemDestino;

    /**
     * Número da agência de vinculação do terminal, no caso do lotérico, CAIXA AQUI, ATM e PAE.
     */
    private Integer numeroAgenciaVinculacao;

    /**
     * Número do banco, referente ao pagamento de bloqueto.
     */
    private Short numeroBanco;

    /**
     * Número do banco, referente ao pagamento de bloqueto.
     */
    private Short numeroBancoBloqueto;

    /**
     * Número do banco origem / destino da transação, podendo ser Caixa ou outros.
     */
    private Short numeroBancoOrigemDestino;

    /**
     * Número do canal de atendimento.
     */
    private Short numeroCanal;

    /**
     * Número do cartão de crédito, referente a pagamento avulso mediante a digitação do número do cartão.
     */
    private BigInteger numeroCartaoCreditoAvulso;

    /**
     * Se a posição 32 da trilha for igual a 8, indica que é um Cartão social. Sendo 1-Não é cartão social e 2-Cartão Social.
     */
    private Byte numeroCartaoSocial;

    /**
     * Número do cedente. Indica o credor do título, quem emite o título, visto que possui
     * contrato de cobrança bancária com um banco. O código do cendente pode ser obtido
     * através da decomposição do layout do campo livre dos boletos.
     */
    private Long numeroCedente;

    /**
     * Número do celular beneficiado pela recarga, realizada pelo cliente. Informação que
     * deve ser preenchida para transações de compra de crédito de celular.
     */
    private String numeroCelularBeneficiado;

    /**
     * Número do cep da agência.
     */
    private Integer numeroCepAgencia;

    /**
     * Número do cep da Agência Origem / Destino, podendo ser de outro banco.
     */
    private Integer numeroCepAgenciaOrigemDestino;

    /**
     * Número do cep do cliente.
     */
    private Integer numeroCepCliente;

    /**
     * Número do cep do estabelecimento.
     */
    private Integer numeroCepEstabelecimento;

    /**
     * Número do cep do terminal.
     */
    private Integer numeroCepTerminal;

    /**
     * Número do cheque. Informação adicional que deve ser preenchida quando a
     * transação for um lançamento envolvendo cheques (compensação, depósito).
     */
    private Long numeroCheque;

    /**
     * Número Identificador de Função previsto na Nova Solução de Gestão de Depósitos.
     */
    private String numeroCif;

    /**
     * Número único apra o cliente. Pode ser obtido através da decomposição do campo livre dos maiores cedentes.
     */
    private BigInteger numeroClienteConcessionaria;

    /**
     * Número que identifica a concessionária.
     */
    private Long numeroConcessionaria;

    /**
     * Número da conta da transação.
     */
    private Long numeroConta;

    /**
     * Número da conta de origem / destino da transação, podendo ser Caixa ou outros.
     */
    private Long numeroContaOrigemDestino;

    /**
     * Número do contrato. Informação adicional que deve ser preenchida quando o
     * lançamento for um crédito de empréstimo, lançamento de prestações ou consultas pontuais e pagamento de contrato de habitação.
     */
    private Long numeroContrato;

    /**
     * Número do cpf ou cnpj do contribuinte. Informação que deve ser preenchida quando a transação for um pagamento de DARF.
     */
    private Long numeroCpfCnpjContribuinte;

    /**
     * Número do estabelecimento. Deve ser preenchido para todas as transações, a
     * exceção de transações automáticas. Representa a denominação do local em que
     * foram efetuadas as transações.
     */
    private Long numeroEstabelecimento;

    /**
     * Número final da série de cheques desbloqueados. Informação adicional acerca de desbloqueio de cheques.
     */
    private Long numeroFinalCheque;

    /**
     * Número que identifica a forma na qual a transação foi realizada, exemplo:em espécie, cheque, cartão, etc.
     */
    private Short numeroFormaTransacao;

    /**
     * Número inicial da série de cheques solicitados, no caso de solicitação de cheques ou
     * Número inicial da série de cheques desbloqueados, no caso de desbloqueio de cheques.
     */
    private Long numeroInicialCheque;

    /**
     * Número de lote de processamento do sistema SISAF.
     */
    private Long numeroLote;

    /**
     * Identificador da Modalidade da Transação:
     * 1 - Internet;
     * 2 - Cartão;
     * 3 - Cheque;
     * 4 - Guia de Retirada;
     * 5 - Origem Conta outro Banco.
     */
    private Short numeroModalidadeTransacao;

    /**
     * Identificador da moeda da transação
     */
    private Short numeroMoeda;

    /**
     * Número que identifica a moeda do bloqueto de cobrança.
     */
    private Short numeroMoedaBloqueto;

    /**
     * Identificador do Nível de Risco da Origem / Destino da Transação.
     */
    private Byte numeroNivelRiscoOrigemDestino;

    /**
     * Número identificador da transação de origem (NSU).
     */
    private BigInteger numeroSequencialUnicoOrigem;

    /**
     * Número Seqüencial Único. Identificador da transação no Sistema LTC.
     */
    private BigInteger numeroSequencialUnicoTransacao;

    /**
     * Número do operador que assistiu a realização de uma transação bancária, ou seja
     * transações nos caixas executivos, estações financeiras, lotéricos, correspondentes
     * não bancários, sistemas de apoio, SIAUT, SISPER, etc.
     */
    private BigInteger numeroOperador;

    /**
     * Número que identifica a operadora de telefonia. Informação que deve ser preenchida
     * para transações de compra de crédito de celular.
     */
    private Short numeroOperadoraTelefonia;

    /**
     * Identificador do Código da Operação que depende do Tipo de Operação:
     * - Para B24: Código processamento TECBAN;
     * - Para APV: TD + CL + TIPO;
     * - Para DEC: CF + SCF.
     */
    private Long numeroOrigemTransacao;

    /**
     * Período de referência Mês/Ano que consta nos pagamentos de tributos. Ex: INSS - competência 06/2009.
     * Informação que deve ser preenchida quando a transação for um pagamento de DARF, INSS, etc.
     */
    private Integer numeroPeriodoReferencia;

    /**
     * Número do PIS ou número do NIS. Informação adicional que deve ser preenchida para transações dos produtos sociais.
     */
    private BigInteger numeroPisNis;

    /**
     * Número da prestação do contrato habitacional.  Informação adicional que deve ser
     * preenchida quando a transação for pagamento de contrato de habitação.
     */
    private Short numeroPrestacaoHabitacao;

    /**
     * Número de processamento da transação.
     */
    private BigInteger numeroProcessamento;

    /**
     * Número do produto da transação.
     */
    private Short numeroProduto;

    /**
     * Número do produto de origem / destino da transação, podendo ser Caixa ou outros.
     */
    private Short numeroProdutoOrigemDestino;

    /**
     * Identificador do Canal onde a transação foi efetuada.
     */
    private Long numeroRedeIso;

    /**
     * Número do renavam do veículo relacionado à transação.
     */
    private Long numeroRenavamVeiculo;

    /**
     * Número do sacado. Indica um número utilizado pelo cedente, fora do sistema de
     * cobrança, para identificar o sacado. Esse número pode ser obtido através da
     * decomposição do layout do campo livre dos maiores cedentes. Ex.: para ser usado
     * nos casos de cobrança sem registro.
     */
    private Long numeroSacado;

    /**
     * Número impresso no verso do cheque das DFC. Informação adicional que deve ser
     * preenchida quando a transação for um lançamento envolvendo cheques (compensação, depósito).
     */
    private Long numeroSequencialImpresso;

    /**
     * Número que identifica informações de processamento de um sublote.
     * TODO - Este número não deverá ser maior que 10, portanto deverá ser modificado na Base de Dados para 2 posições.
     * Atualmente esta com 20 posições.
     */
    private Short numeroSublote;

    /**
     * (IC_CARTAO) Indicador do tipo de cartão utilizado em uma transação CAIXA.
     */
    private Short numeroTipoCartao;

    /**
     * Identificador do Tipo de Transação que representa a categoria da Transação.
     * Exemplo: Saque, Transferência, DOC, Compra, Pagamento, Saque de cheque.
     */
    private Short numeroTipoTransacao;

    /**
     * Número que identifica o titular da transação. Exemplo: 1o. titular, 2o. titular, etc.
     */
    private Byte numeroTitular;

    /**
     * Número do titular origem / destino da transação, sendo 1o. titular, 2o. titular, etc.
     */
    private Byte numeroTitularOrigemDestino;

    /**
     * Número da versão da transação.
     */
    private Integer numeroVersao;

    /**
     * Número da versão de origem / destino da transação, podendo ser Caixa ou outros.
     */
    private Integer numeroVersaoOrigemDestino;

    /**
     * Número que identifica a versão do terminal, isto é, a última atualização de cadastro.
     */
    private Integer numeroVersaoTerminal;

    /**
     * Percentual de valor pago em relação ao total de valor liberado.  Informação
     * adicional que deve ser preenchida quando o lançamento for um crédito de
     * empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Double percentualValorAmortizado;

    /**
     * Quantidade disponível para a realização da transação.
     */
    private Integer quantidadeLimiteDisponivel;

    /**
     * Quantidade de parcelas restantes.  Informação adicional que deve ser preenchida
     * quando o lançamento for um crédito de empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Integer quantidadeParcelaRestante;

    /**
     * Quantidade total de parcelas.  Informação adicional que deve ser preenchida quando
     * o lançamento for um crédito de empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Integer quantidadeParcelaTotal;

    /**
     * Quantidade de cheques solicitados. Informação adicional acerca da solicitação de cheques.
     */
    private Integer quantidadeSolicitadaCheque;

    /**
     * Tempo de compensação lançado em depósito. Informação adicional que deve ser
     * preenchida quando a transação for um lançamento envolvendo cheques (compensação, depósito).
     */
    private Integer quantidadeTempoCompensacao;

    /**
     * Sigla do país referente as transações de câmbio. Segundo o SIICO: Abreviatura pela
     * qual o Ministério das Relações Exteriores reconhece o país.
     */
    private String siglaPaisCambio;

    /**
     * Sigla que identifica o país da transação.
     */
    private String siglaPaisOrigemDestino;

    /**
     * Sigla que identifica o sistema.
     */
    private String siglaSistema;

    /**
     * Código da unidade federativa da agência da transação.
     */
    private String siglaUFAgencia;

    /**
     * Código da unidade federativa da agência origem/destino.
     */
    private String siglaUFAgenciaOrigemDestino;

    /**
     * Código da unidade federativa do operador.
     */
    private String siglaUFOperador;

    /**
     * Código da unidade federativa do terminal.
     */
    private String siglaUFTerminal;

    /**
     * Data e hora de acordo com o fuso local.
     */
    private Date dataHoraTransacaoLocal;

    /**
     * Data e hora da transação no servidor.
     */
    private Date dataHoraTransacaoServidor;

    /**
     * Saldo bloqueado a meia noite.
     */
    private Double valorBloqueadoFimDia;

    /**
     * Indica quanto de créditos rotativos o cliente tem, seja CROT ou GIM. No exemplo
     * acima, refere-se ao valor de R$ 2.000,00.
     */
    private Double valorCreditoRotativo;

    /**
     * Valor liberado.  Informação adicional que deve ser preenchida quando o lançamento
     * for um crédito de empréstimo, lançamento de prestações ou consultas pontuais.
     */
    private Double valorLiberado;

    /**
     * Valor disponível para a realização da transação.
     */
    private Double valorLimiteDisponivel;

    /**
     * Valor máximo aplicável a transação.
     */
    private Double valorLimiteOperacao;

    /**
     * Saldo da conta.
     */
    private Double valorSaldoConta;

    /**
     * Saldo disponível é composto pelo saldo próprio do cliente mais o limite do crédito
     * rotativo (CROT ou GIM), que permite efetuar transações financeiras de débito.
     * Ex: um cliente com R$ 1.000,00 de saldo próprio disponível, mais R$ 500,00 de saldo
     * próprio bloqueado, mais  R$ 2.000,00 de crédito rotativo, tem de fato o valor de R$ 3.000,00 de saldo disponível.
     */
    private Double valorSaldoDisponivel;

    /**
     * Indica o saldo total a meia noite.
     */
    private Double valorTotalFimDia;

    /**
     * Valor da transação bancária.
     */
    private Double valorTransacao;

    /**
     * Valor original da transação, podendo ser de câmbio, do darf, da prestação da
     * habitação, concessionária, convênio, bloqueto, etc.
     */
    private Double valorTransacaoOriginal;

    /**
     * Transações de compra permite que seja informado um valor de troco para a
     * transação. O cliente compra X reais em produtos e leva Y reais em dinheiro em um
     * único lançamento em supermercados.
     */
    private Double valorTroco;

    /**
     * *************************Variáveis de apoio à análise********************************
     */

    private transient final Set<String> regras = new HashSet<String>();

    private transient final Map<Short, Double> classesRN = new HashMap<Short, Double>();

    private transient final Map<Short, Double> desvioPerfil = new HashMap<Short, Double>();

    private transient final Map<Short, Double> valorAtributosDesvioCliente = new HashMap<Short, Double>();

    private transient final Map<Short, Double> valorAtributosDesvioConta = new HashMap<Short, Double>();

    private transient final Map<Short, Double> valorAtributosDesvioTerminal = new HashMap<Short, Double>();

    private transient final Map<Short, Double> valorAtributosDesvioLocal = new HashMap<Short, Double>();

    public void addRegra(short coRegra) {
        regras.add(String.valueOf(coRegra));
    }

    public void setClassificacaoRN(short codRegra, double classe) {
        classesRN.put(codRegra, classe);
    }

    public Set<String> getRegras() {
        return regras;
    }

    public Map<Short, Double> getClassesRN() {
        return classesRN;
    }

    public void setDesvioPerfil(short codRegra, double desvio) {
        desvioPerfil.put(codRegra, desvio);
    }

    public Map<Short, Double> getDesvioPerfil() {
        return desvioPerfil;
    }

    public void setValorAtributosDesvioCliente(short atributo, double desvio) {
        this.valorAtributosDesvioCliente.put(atributo, desvio);
    }

    public void setValorAtributosDesvioCliente(Map<Short, Double> valorAtributosDesvioCliente) {
        this.valorAtributosDesvioCliente.putAll(valorAtributosDesvioCliente);
    }

    public Map<Short, Double> getValorAtributosDesvioCliente() {
        return valorAtributosDesvioCliente;
    }

    public void setValorAtributosDesvioConta(short atributo, double desvio) {
        this.valorAtributosDesvioConta.put(atributo, desvio);
    }

    public void setValorAtributosDesvioConta(Map<Short, Double> valorAtributosDesvioConta) {
        this.valorAtributosDesvioConta.putAll(valorAtributosDesvioConta);
    }

    public Map<Short, Double> getValorAtributosDesvioConta() {
        return valorAtributosDesvioConta;
    }

    public void setValorAtributosDesvioTerminal(short atributo, double desvio) {
        this.valorAtributosDesvioTerminal.put(atributo, desvio);
    }

    public void setValorAtributosDesvioTerminal(Map<Short, Double> valorAtributosDesvioTerminal) {
        this.valorAtributosDesvioTerminal.putAll(valorAtributosDesvioTerminal);
    }

    public Map<Short, Double> getValorAtributosDesvioTerminal() {
        return valorAtributosDesvioTerminal;
    }

    public void setValorAtributosDesvioLocal(short atributo, double desvio) {
        this.valorAtributosDesvioLocal.put(atributo, desvio);
    }

    public void setValorAtributosDesvioLocal(Map<Short, Double> valorAtributosDesvioLocal) {
        this.valorAtributosDesvioLocal.putAll(valorAtributosDesvioLocal);
    }

    public Map<Short, Double> getValorAtributosDesvioLocal() {
        return valorAtributosDesvioLocal;
    }

    /***************************************** Fim do trecho de variáveis de apoio à análise ******************************************/

    /**
     * *************************Transações de Subsídio à transação apontada********************************
     */

    private transient final Set<BigInteger> transacoesSubsidio = new HashSet<BigInteger>();

    public void addTransacoesSubsidio(BigInteger trans) {
        transacoesSubsidio.add(trans);
    }

    public void addTransacoesSubsidio(Set<BigInteger> trans) {
        transacoesSubsidio.addAll(trans);
    }

    public Set<BigInteger> getTransacoesSubsidio() {
        return transacoesSubsidio;
    }

    /***************************************** Fim do trecho de transações de subsídio à transação apontada ******************************************/

    /**
     * ************************************** Métodos de data da transação de apoio à análise *****************************************
     */

    public Long getDataMinutosTransacaoServidor() {
        return dataHoraTransacaoServidor.getTime() / 1000 / 60;
    }

    public Double getHrTransacaoServidor() {
        return Double.parseDouble(DateHandle.formatDate(dataHoraTransacaoServidor, "HH.mm"));
    }

    public String getDmaTransacaoServidor() {
        return DateHandle.formatDate(dataHoraTransacaoServidor, "dd-MM-yyyy");
    }

    public TransacaoRaw() {

    }

    /**
     * @param codigoBarra
     * @param codigoCartao
     * @param codigoCodificacaoTransacao
     * @param codigoComandoAcerto
     * @param codigoCompensacao
     * @param codigoConvenio
     * @param codigoDetalheComando
     * @param codigoDocumento
     * @param codigoFundoInvestimento
     * @param codigoIdentificadorTed
     * @param codigoIdentificadorCliente
     * @param codigoIdentificadorClienteOrigemDestino
     * @param codigoIdentificadorConta
     * @param codigoIdentificadorContaOrigemDestino
     * @param codigoModalidadeTed
     * @param codigoMotivoContraordem
     * @param codigoPlacaVeiculo
     * @param codigoPrefeitura
     * @param codigoReceitaFederal
     * @param codigoRespostaIso
     * @param codigoServico
     * @param codigoTerminal
     * @param codigoTipoChave
     * @param codigoTipoDarf
     * @param codigoTipoEmpresa
     * @param codigoTipoLimite
     * @param codigoTipoMensagemIso
     * @param codigoTipoNegocio
     * @param codigoTipoPagamentoDETRAN
     * @param codigoTransacaoInicio
     * @param diaVencimentoParcela
     * @param detalheTransacao
     * @param dataComandoCliente
     * @param dataContabilTransacao
     * @param dataVencimentoPagamento
     * @param indicadorCartaoDigitacao
     * @param indicadorChave
     * @param indicadorChaveSecundaria
     * @param indicadorChip
     * @param indicadorContaDigitacao
     * @param indicadorDebitoConta
     * @param indicadorDiaUtil
     * @param indicadorEmprestimoAutomatico
     * @param indicadorFeriado
     * @param indicadorFraude
     * @param indicadorNaturezaOperacao
     * @param indicadorOrigemTransacao
     * @param indicadorSituacaoChip
     * @param indicadorSituacaoLeitora
     * @param indicadorUsoSenha
     * @param numeroAgencia
     * @param numeroAgenciaOrigemDestino
     * @param numeroAgenciaVinculacao
     * @param numeroBanco
     * @param numeroBancoBloqueto
     * @param numeroBancoOrigemDestino
     * @param numeroCanal
     * @param numeroCartaoCreditoAvulso
     * @param numeroCartaoSocial
     * @param numeroCedente
     * @param numeroCelularBeneficiado
     * @param numeroCepAgencia
     * @param numeroCepAgenciaOrigemDestino
     * @param numeroCepCliente
     * @param numeroCepEstabelecimento
     * @param numeroCepTerminal
     * @param numeroCheque
     * @param numeroCif
     * @param numeroClienteConcessionaria
     * @param numeroConcessionaria
     * @param numeroConta
     * @param numeroContaOrigemDestino
     * @param numeroContrato
     * @param numeroCpfCnpjContribuinte
     * @param numeroEstabelecimento
     * @param numeroFinalCheque
     * @param numeroFormaTransacao
     * @param numeroInicialCheque
     * @param numeroLote
     * @param numeroModalidadeTransacao
     * @param numeroMoeda
     * @param numeroMoedaBloqueto
     * @param numeroNivelRiscoOrigemDestino
     * @param numeroSequencialUnicoOrigem
     * @param numeroSequencialUnicoTransacao
     * @param numeroOperador
     * @param numeroOperadoraTelefonia
     * @param numeroOrigemTransacao
     * @param numeroPeriodoReferencia
     * @param numeroPisNis
     * @param numeroPrestacaoHabitacao
     * @param numeroProcessamento
     * @param numeroProcessamentoCarga
     * @param numeroProduto
     * @param numeroProdutoOrigemDestino
     * @param numeroRedeIso
     * @param numeroRenavamVeiculo
     * @param numeroSacado
     * @param numeroSequencialImpresso
     * @param numeroSublote
     * @param numeroTipoCartao
     * @param numeroTipoTransacao
     * @param numeroTitular
     * @param numeroTitularOrigemDestino
     * @param numeroVersao
     * @param numeroVersaoOrigemDestino
     * @param numeroVersaoTerminal
     * @param percentualValorAmortizado
     * @param quantidadeLimiteDisponivel
     * @param quantidadeParcelaRestante
     * @param quantidadeParcelaTotal
     * @param quantidadeSolicitadaCheque
     * @param quantidadeTempoCompensacao
     * @param siglaPaisCambio
     * @param siglaPaisOrigemDestino
     * @param siglaSistema
     * @param siglaUFAgencia
     * @param siglaUFAgenciaOrigemDestino
     * @param siglaUFOperador
     * @param siglaUFTerminal
     * @param dataHoraTransacaoLocal
     * @param dataHoraTransacaoServidor
     * @param valorBloqueadoFimDia
     * @param valorCreditoRotativo
     * @param valorLiberado
     * @param valorLimiteDisponivel
     * @param valorLimiteOperacao
     * @param valorSaldoConta
     * @param valorSaldoDisponivel
     * @param valorTotalFimDia
     * @param valorTransacao
     * @param valorTransacaoOriginal
     * @param valorTroco
     */
    public TransacaoRaw(BigInteger codigoBarra, String codigoCartao,
            String codigoCodificacaoTransacao, Short codigoComandoAcerto,
            Long codigoCompensacao, String codigoConvenio,
            Short codigoDetalheComando, String codigoDocumento,
            Short codigoFundoInvestimento, Long codigoIdentificadorTed,
            BigInteger codigoIdentificadorCliente,
            BigInteger codigoIdentificadorClienteOrigemDestino,
            BigInteger codigoIdentificadorConta,
            BigInteger codigoIdentificadorContaOrigemDestino,
            Long codigoModalidadeTed, Byte codigoMotivoContraordem,
            String codigoPlacaVeiculo, Long codigoPrefeitura,
            Long codigoReceitaFederal, String codigoRespostaIso,
            String codigoServico, String codigoTerminal, Short codigoTipoChave,
            Short codigoTipoDarf, Short codigoTipoEmpresa,
            Short codigoTipoLimite, String codigoTipoMensagemIso,
            Short codigoTipoNegocio, Short codigoTipoPagamentoDETRAN,
            String codigoTransacaoInicio, Byte diaVencimentoParcela,
            String detalheTransacao, Date dataComandoCliente,
            Date dataContabilTransacao, Date dataVencimentoPagamento,
            Boolean indicadorCartaoDigitacao, Short indicadorChave,
            Character indicadorChaveSecundaria, Boolean indicadorChip,
            Boolean indicadorContaDigitacao, Boolean indicadorDebitoConta,
            Boolean indicadorDiaUtil, Boolean indicadorEmprestimoAutomatico,
            Boolean indicadorFeriado, Boolean indicadorFraude,
            Byte indicadorNaturezaOperacao, Character indicadorOrigemTransacao,
            Short indicadorSituacaoChip, Short indicadorSituacaoLeitora,
            String indicadorUsoSenha, Integer numeroAgencia,
            Integer numeroAgenciaOrigemDestino,
            Integer numeroAgenciaVinculacao, Short numeroBanco, Short numeroBancoBloqueto,
            Short numeroBancoOrigemDestino, Short numeroCanal,
            BigInteger numeroCartaoCreditoAvulso, Byte numeroCartaoSocial,
            Long numeroCedente, String numeroCelularBeneficiado,
            Integer numeroCepAgencia, Integer numeroCepAgenciaOrigemDestino,
            Integer numeroCepCliente, Integer numeroCepEstabelecimento,
            Integer numeroCepTerminal, Long numeroCheque, String numeroCif,
            BigInteger numeroClienteConcessionaria, Long numeroConcessionaria,
            Long numeroConta, Long numeroContaOrigemDestino,
            Long numeroContrato, Long numeroCpfCnpjContribuinte,
            Long numeroEstabelecimento, Long numeroFinalCheque,
            Short numeroFormaTransacao, Long numeroInicialCheque,
            Long numeroLote, Short numeroModalidadeTransacao,
            Short numeroMoeda, Short numeroMoedaBloqueto,
            Byte numeroNivelRiscoOrigemDestino,
            BigInteger numeroSequencialUnicoOrigem,
            BigInteger numeroSequencialUnicoTransacao,
            BigInteger numeroOperador, Short numeroOperadoraTelefonia,
            Long numeroOrigemTransacao, Integer numeroPeriodoReferencia,
            BigInteger numeroPisNis, Short numeroPrestacaoHabitacao,
            BigInteger numeroProcessamento, Short numeroProduto,
            Short numeroProdutoOrigemDestino, Long numeroRedeIso,
            Long numeroRenavamVeiculo, Long numeroSacado,
            Long numeroSequencialImpresso, Short numeroSublote,
            Short numeroTipoCartao, Short numeroTipoTransacao,
            Byte numeroTitular, Byte numeroTitularOrigemDestino,
            Integer numeroVersao, Integer numeroVersaoOrigemDestino,
            Integer numeroVersaoTerminal, Double percentualValorAmortizado,
            Integer quantidadeLimiteDisponivel,
            Integer quantidadeParcelaRestante, Integer quantidadeParcelaTotal,
            Integer quantidadeSolicitadaCheque,
            Integer quantidadeTempoCompensacao, String siglaPaisCambio,
            String siglaPaisOrigemDestino, String siglaSistema,
            String siglaUFAgencia, String siglaUFAgenciaOrigemDestino,
            String siglaUFOperador, String siglaUFTerminal,
            Date dataHoraTransacaoLocal,
            Date dataHoraTransacaoServidor, Double valorBloqueadoFimDia,
            Double valorCreditoRotativo, Double valorLiberado,
            Double valorLimiteDisponivel, Double valorLimiteOperacao,
            Double valorSaldoConta, Double valorSaldoDisponivel,
            Double valorTotalFimDia, Double valorTransacao,
            Double valorTransacaoOriginal, Double valorTroco) {
        this.codigoBarra = codigoBarra;
        this.codigoCartao = codigoCartao;
        this.codigoCodificacaoTransacao = codigoCodificacaoTransacao;
        this.codigoComandoAcerto = codigoComandoAcerto;
        this.codigoCompensacao = codigoCompensacao;
        this.codigoConvenio = codigoConvenio;
        this.codigoDetalheComando = codigoDetalheComando;
        this.codigoDocumento = codigoDocumento;
        this.codigoFundoInvestimento = codigoFundoInvestimento;
        this.codigoIdentificadorTed = codigoIdentificadorTed;
        this.codigoIdentificadorCliente = codigoIdentificadorCliente;
        this.codigoIdentificadorClienteOrigemDestino = codigoIdentificadorClienteOrigemDestino;
        this.codigoIdentificadorConta = codigoIdentificadorConta;
        this.codigoIdentificadorContaOrigemDestino = codigoIdentificadorContaOrigemDestino;
        this.codigoModalidadeTed = codigoModalidadeTed;
        this.codigoMotivoContraordem = codigoMotivoContraordem;
        this.codigoPlacaVeiculo = codigoPlacaVeiculo;
        this.codigoPrefeitura = codigoPrefeitura;
        this.codigoReceitaFederal = codigoReceitaFederal;
        this.codigoRespostaIso = codigoRespostaIso;
        this.codigoServico = codigoServico;
        this.codigoTerminal = codigoTerminal;
        this.codigoTipoChave = codigoTipoChave;
        this.codigoTipoDarf = codigoTipoDarf;
        this.codigoTipoEmpresa = codigoTipoEmpresa;
        this.codigoTipoLimite = codigoTipoLimite;
        this.codigoTipoMensagemIso = codigoTipoMensagemIso;
        this.codigoTipoNegocio = codigoTipoNegocio;
        this.codigoTipoPagamentoDETRAN = codigoTipoPagamentoDETRAN;
        this.codigoTransacaoInicio = codigoTransacaoInicio;
        this.diaVencimentoParcela = diaVencimentoParcela;
        this.detalheTransacao = detalheTransacao;
        this.dataComandoCliente = dataComandoCliente;
        this.dataContabilTransacao = dataContabilTransacao;
        this.dataVencimentoPagamento = dataVencimentoPagamento;
        this.indicadorCartaoDigitacao = indicadorCartaoDigitacao;
        this.indicadorChave = indicadorChave;
        this.indicadorChaveSecundaria = indicadorChaveSecundaria;
        this.indicadorChip = indicadorChip;
        this.indicadorContaDigitacao = indicadorContaDigitacao;
        this.indicadorDebitoConta = indicadorDebitoConta;
        this.indicadorDiaUtil = indicadorDiaUtil;
        this.indicadorEmprestimoAutomatico = indicadorEmprestimoAutomatico;
        this.indicadorFeriado = indicadorFeriado;
        this.indicadorFraude = indicadorFraude;
        this.indicadorNaturezaOperacao = indicadorNaturezaOperacao;
        this.indicadorOrigemTransacao = indicadorOrigemTransacao;
        this.indicadorSituacaoChip = indicadorSituacaoChip;
        this.indicadorSituacaoLeitora = indicadorSituacaoLeitora;
        this.indicadorUsoSenha = indicadorUsoSenha;
        this.numeroAgencia = numeroAgencia;
        this.numeroAgenciaOrigemDestino = numeroAgenciaOrigemDestino;
        this.numeroAgenciaVinculacao = numeroAgenciaVinculacao;
        this.numeroBanco = numeroBanco;
        this.numeroBancoBloqueto = numeroBancoBloqueto;
        this.numeroBancoOrigemDestino = numeroBancoOrigemDestino;
        this.numeroCanal = numeroCanal;
        this.numeroCartaoCreditoAvulso = numeroCartaoCreditoAvulso;
        this.numeroCartaoSocial = numeroCartaoSocial;
        this.numeroCedente = numeroCedente;
        this.numeroCelularBeneficiado = numeroCelularBeneficiado;
        this.numeroCepAgencia = numeroCepAgencia;
        this.numeroCepAgenciaOrigemDestino = numeroCepAgenciaOrigemDestino;
        this.numeroCepCliente = numeroCepCliente;
        this.numeroCepEstabelecimento = numeroCepEstabelecimento;
        this.numeroCepTerminal = numeroCepTerminal;
        this.numeroCheque = numeroCheque;
        this.numeroCif = numeroCif;
        this.numeroClienteConcessionaria = numeroClienteConcessionaria;
        this.numeroConcessionaria = numeroConcessionaria;
        this.numeroConta = numeroConta;
        this.numeroContaOrigemDestino = numeroContaOrigemDestino;
        this.numeroContrato = numeroContrato;
        this.numeroCpfCnpjContribuinte = numeroCpfCnpjContribuinte;
        this.numeroEstabelecimento = numeroEstabelecimento;
        this.numeroFinalCheque = numeroFinalCheque;
        this.numeroFormaTransacao = numeroFormaTransacao;
        this.numeroInicialCheque = numeroInicialCheque;
        this.numeroLote = numeroLote;
        this.numeroModalidadeTransacao = numeroModalidadeTransacao;
        this.numeroMoeda = numeroMoeda;
        this.numeroMoedaBloqueto = numeroMoedaBloqueto;
        this.numeroNivelRiscoOrigemDestino = numeroNivelRiscoOrigemDestino;
        this.numeroSequencialUnicoOrigem = numeroSequencialUnicoOrigem;
        this.numeroSequencialUnicoTransacao = numeroSequencialUnicoTransacao;
        this.numeroOperador = numeroOperador;
        this.numeroOperadoraTelefonia = numeroOperadoraTelefonia;
        this.numeroOrigemTransacao = numeroOrigemTransacao;
        this.numeroPeriodoReferencia = numeroPeriodoReferencia;
        this.numeroPisNis = numeroPisNis;
        this.numeroPrestacaoHabitacao = numeroPrestacaoHabitacao;
        this.numeroProcessamento = numeroProcessamento;
        this.numeroProduto = numeroProduto;
        this.numeroProdutoOrigemDestino = numeroProdutoOrigemDestino;
        this.numeroRedeIso = numeroRedeIso;
        this.numeroRenavamVeiculo = numeroRenavamVeiculo;
        this.numeroSacado = numeroSacado;
        this.numeroSequencialImpresso = numeroSequencialImpresso;
        this.numeroSublote = numeroSublote;
        this.numeroTipoCartao = numeroTipoCartao;
        this.numeroTipoTransacao = numeroTipoTransacao;
        this.numeroTitular = numeroTitular;
        this.numeroTitularOrigemDestino = numeroTitularOrigemDestino;
        this.numeroVersao = numeroVersao;
        this.numeroVersaoOrigemDestino = numeroVersaoOrigemDestino;
        this.numeroVersaoTerminal = numeroVersaoTerminal;
        this.percentualValorAmortizado = percentualValorAmortizado;
        this.quantidadeLimiteDisponivel = quantidadeLimiteDisponivel;
        this.quantidadeParcelaRestante = quantidadeParcelaRestante;
        this.quantidadeParcelaTotal = quantidadeParcelaTotal;
        this.quantidadeSolicitadaCheque = quantidadeSolicitadaCheque;
        this.quantidadeTempoCompensacao = quantidadeTempoCompensacao;
        this.siglaPaisCambio = siglaPaisCambio;
        this.siglaPaisOrigemDestino = siglaPaisOrigemDestino;
        this.siglaSistema = siglaSistema;
        this.siglaUFAgencia = siglaUFAgencia;
        this.siglaUFAgenciaOrigemDestino = siglaUFAgenciaOrigemDestino;
        this.siglaUFOperador = siglaUFOperador;
        this.siglaUFTerminal = siglaUFTerminal;
        this.dataHoraTransacaoLocal = dataHoraTransacaoLocal;
        this.dataHoraTransacaoServidor = dataHoraTransacaoServidor;
        this.valorBloqueadoFimDia = valorBloqueadoFimDia;
        this.valorCreditoRotativo = valorCreditoRotativo;
        this.valorLiberado = valorLiberado;
        this.valorLimiteDisponivel = valorLimiteDisponivel;
        this.valorLimiteOperacao = valorLimiteOperacao;
        this.valorSaldoConta = valorSaldoConta;
        this.valorSaldoDisponivel = valorSaldoDisponivel;
        this.valorTotalFimDia = valorTotalFimDia;
        this.valorTransacao = valorTransacao;
        this.valorTransacaoOriginal = valorTransacaoOriginal;
        this.valorTroco = valorTroco;
    }

    /**
     * @return the codigoBarra
     */
    public BigInteger getCodigoBarra() {
        return codigoBarra;
    }

    /**
     * @return the codigoCartao
     */
    public String getCodigoCartao() {
        return codigoCartao;
    }

    /**
     * @return the codigoCodificacaoTransacao
     */
    public String getCodigoCodificacaoTransacao() {
        return codigoCodificacaoTransacao;
    }

    /**
     * @return the codigoComandoAcerto
     */
    public Short getCodigoComandoAcerto() {
        return codigoComandoAcerto;
    }

    /**
     * @return the codigoCompensacao
     */
    public Long getCodigoCompensacao() {
        return codigoCompensacao;
    }

    /**
     * @return the codigoConvenio
     */
    public String getCodigoConvenio() {
        return codigoConvenio;
    }

    /**
     * @return the codigoDetalheComando
     */
    public Short getCodigoDetalheComando() {
        return codigoDetalheComando;
    }

    /**
     * @return the codigoDocumento
     */
    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    /**
     * @return the codigoFundoInvestimento
     */
    public Short getCodigoFundoInvestimento() {
        return codigoFundoInvestimento;
    }

    /**
     * @return the codigoIdentificadorTed
     */
    public Long getCodigoIdentificadorTed() {
        return codigoIdentificadorTed;
    }

    /**
     * @return the codigoIdentificadorCliente
     */
    public BigInteger getCodigoIdentificadorCliente() {
        return codigoIdentificadorCliente;
    }

    public String getCodigoIdentificadorClienteStr() {
        if (codigoIdentificadorCliente == null) {
            return null;
        }
        return codigoIdentificadorCliente.toString();
    }

    /**
     * @return the codigoIdentificadorClienteOrigemDestino
     */
    public BigInteger getCodigoIdentificadorClienteOrigemDestino() {
        return codigoIdentificadorClienteOrigemDestino;
    }

    /**
     * @return the codigoIdentificadorConta
     */
    public BigInteger getCodigoIdentificadorConta() {
        return codigoIdentificadorConta;
    }

    public String getCodigoIdentificadorContaStr() {
        if (codigoIdentificadorConta == null) {
            return null;
        }
        return codigoIdentificadorConta.toString();
    }

    /**
     * @return the codigoIdentificadorContaOrigemDestino
     */
    public BigInteger getCodigoIdentificadorContaOrigemDestino() {
        return codigoIdentificadorContaOrigemDestino;
    }

    /**
     * @return the codigoModalidadeTed
     */
    public Long getCodigoModalidadeTed() {
        return codigoModalidadeTed;
    }

    /**
     * @return the codigoMotivoContraordem
     */
    public Byte getCodigoMotivoContraordem() {
        return codigoMotivoContraordem;
    }

    /**
     * @return the codigoPlacaVeiculo
     */
    public String getCodigoPlacaVeiculo() {
        return codigoPlacaVeiculo;
    }

    /**
     * @return the codigoPrefeitura
     */
    public Long getCodigoPrefeitura() {
        return codigoPrefeitura;
    }

    /**
     * @return the codigoReceitaFederal
     */
    public Long getCodigoReceitaFederal() {
        return codigoReceitaFederal;
    }

    /**
     * @return the codigoRespostaIso
     */
    public String getCodigoRespostaIso() {
        return codigoRespostaIso;
    }

    /**
     * @return the codigoServico
     */
    public String getCodigoServico() {
        return codigoServico;
    }

    /**
     * @return the codigoTerminal
     */
    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    /**
     * @return the codigoTipoChave
     */
    public Short getCodigoTipoChave() {
        return codigoTipoChave;
    }

    /**
     * @return the codigoTipoDarf
     */
    public Short getCodigoTipoDarf() {
        return codigoTipoDarf;
    }

    /**
     * @return the codigoTipoEmpresa
     */
    public Short getCodigoTipoEmpresa() {
        return codigoTipoEmpresa;
    }

    /**
     * @return the codigoTipoLimite
     */
    public Short getCodigoTipoLimite() {
        return codigoTipoLimite;
    }

    /**
     * @return the codigoTipoMensagemIso
     */
    public String getCodigoTipoMensagemIso() {
        return codigoTipoMensagemIso;
    }

    /**
     * @return the codigoTipoNegocio
     */
    public Short getCodigoTipoNegocio() {
        return codigoTipoNegocio;
    }

    /**
     * @return the codigoTipoPagamentoDETRAN
     */
    public Short getCodigoTipoPagamentoDETRAN() {
        return codigoTipoPagamentoDETRAN;
    }

    /**
     * @return the codigoTransacaoInicio
     */
    public String getCodigoTransacaoInicio() {
        return codigoTransacaoInicio;
    }

    /**
     * @return the diaVencimentoParcela
     */
    public Byte getDiaVencimentoParcela() {
        return diaVencimentoParcela;
    }

    /**
     * @return the detalheTransacao
     */
    public String getDetalheTransacao() {
        return detalheTransacao;
    }

    /**
     * @return the dataComandoCliente
     */
    public Date getDataComandoCliente() {
        return dataComandoCliente;
    }

    /**
     * @return the dataContabilTransacao
     */
    public Date getDataContabilTransacao() {
        return dataContabilTransacao;
    }

    /**
     * @return the dataVencimentoPagamento
     */
    public Date getDataVencimentoPagamento() {
        return dataVencimentoPagamento;
    }

    /**
     * @return the indicadorCartaoDigitacao
     */
    public Boolean getIndicadorCartaoDigitacao() {
        return indicadorCartaoDigitacao;
    }

    /**
     * @return the indicadorChave
     */
    public Short getIndicadorChave() {
        return indicadorChave;
    }

    /**
     * @return the indicadorChaveSecundaria
     */
    public Character getIndicadorChaveSecundaria() {
        return indicadorChaveSecundaria;
    }

    /**
     * @return the indicadorChip
     */
    public Boolean getIndicadorChip() {
        return indicadorChip;
    }

    /**
     * @return the indicadorContaDigitacao
     */
    public Boolean getIndicadorContaDigitacao() {
        return indicadorContaDigitacao;
    }

    /**
     * @return the indicadorDebitoConta
     */
    public Boolean getIndicadorDebitoConta() {
        return indicadorDebitoConta;
    }

    /**
     * @return the indicadorDiaUtil
     */
    public Boolean getIndicadorDiaUtil() {
        return indicadorDiaUtil;
    }

    /**
     * @return the indicadorEmprestimoAutomatico
     */
    public Boolean getIndicadorEmprestimoAutomatico() {
        return indicadorEmprestimoAutomatico;
    }

    /**
     * @return the indicadorFeriado
     */
    public Boolean getIndicadorFeriado() {
        return indicadorFeriado;
    }

    /**
     * @return the indicadorFraude
     */
    public Boolean getIndicadorFraude() {
        return indicadorFraude;
    }

    /**
     * @return the indicadorNaturezaOperacao
     */
    public Byte getIndicadorNaturezaOperacao() {
        return indicadorNaturezaOperacao;
    }

    /**
     * @return the indicadorOrigemTransacao
     */
    public Character getIndicadorOrigemTransacao() {
        return indicadorOrigemTransacao;
    }

    /**
     * @return the indicadorSituacaoChip
     */
    public Short getIndicadorSituacaoChip() {
        return indicadorSituacaoChip;
    }

    /**
     * @return the indicadorSituacaoLeitora
     */
    public Short getIndicadorSituacaoLeitora() {
        return indicadorSituacaoLeitora;
    }

    /**
     * @return the indicadorUsoSenha
     */
    public String getIndicadorUsoSenha() {
        return indicadorUsoSenha;
    }

    /**
     * @return the numeroAgencia
     */
    public Integer getNumeroAgencia() {
        return numeroAgencia;
    }

    /**
     * @return the numeroAgencia formatado
     */
    public String getNumeroAgenciaStr() {
        if (numeroAgencia == null) {
            return null;
        }
        return numeroAgencia.toString();
    }

    /**
     * @return the numeroAgenciaOrigemDestino
     */
    public Integer getNumeroAgenciaOrigemDestino() {
        return numeroAgenciaOrigemDestino;
    }

    /**
     * @return the numeroAgenciaVinculacao
     */
    public Integer getNumeroAgenciaVinculacao() {
        return numeroAgenciaVinculacao;
    }

    /**
     * @return the numeroBanco
     */
    public Short getNumeroBanco() {
        return numeroBanco;
    }

    /**
     * @return the numeroBancoBloqueto
     */
    public Short getNumeroBancoBloqueto() {
        return numeroBancoBloqueto;
    }

    /**
     * @return the numeroBancoOrigemDestino
     */
    public Short getNumeroBancoOrigemDestino() {
        return numeroBancoOrigemDestino;
    }

    /**
     * @return the numeroCanal
     */
    public Short getNumeroCanal() {
        return numeroCanal;
    }

    /**
     * @return the numeroCartaoCreditoAvulso
     */
    public BigInteger getNumeroCartaoCreditoAvulso() {
        return numeroCartaoCreditoAvulso;
    }

    /**
     * @return the numeroCartaoSocial
     */
    public Byte getNumeroCartaoSocial() {
        return numeroCartaoSocial;
    }

    /**
     * @return the numeroCedente
     */
    public Long getNumeroCedente() {
        return numeroCedente;
    }

    /**
     * @return the numeroCelularBeneficiado
     */
    public String getNumeroCelularBeneficiado() {
        return numeroCelularBeneficiado;
    }

    /**
     * @return the numeroCepAgencia
     */
    public Integer getNumeroCepAgencia() {
        return numeroCepAgencia;
    }

    /**
     * Número do cep da agência da conta formatado em String
     * @return the numeroCepAgencia
     */
    public String getNumeroCepAgenciaContaCEP4() {
        if (numeroCepAgencia == null) {
            return null;
        }
        return String.format("%8s", numeroCepAgencia.toString()).replace(" ", "0").substring(0, 4);
    }

    /**
     * @return the numeroCepAgenciaOrigemDestino
     */
    public Integer getNumeroCepAgenciaOrigemDestino() {
        return numeroCepAgenciaOrigemDestino;
    }

    /**
     * @return the numeroCepAgenciaOrigemDestino
     */
    public String getNumeroCepAgenciaContaOrigemDestinoCEP4() {
        if (numeroCepAgenciaOrigemDestino == null) {
            return null;
        }
        return String.format("%8s", numeroCepAgenciaOrigemDestino.toString()).replace(" ", "0").substring(0, 4);
    }

    /**
     * @return the numeroCepCliente
     */
    public Integer getNumeroCepCliente() {
        return numeroCepCliente;
    }

    /**
     * @return the numeroCepEstabelecimento
     */
    public Integer getNumeroCepEstabelecimento() {
        return numeroCepEstabelecimento;
    }

    /**
     * @return the numeroCepTerminal
     */
    public Integer getNumeroCepTerminal() {
        return numeroCepTerminal;
    }

    /**
     * @return the numeroCepTerminal
     */
    public String getNumeroCepTerminalCEP4() {
        if (numeroCepTerminal == null) {
            return null;
        }
        return String.format("%8s", numeroCepTerminal.toString()).replace(" ", "0").substring(0, 4);
    }

    /**
     * @return the numeroCheque
     */
    public Long getNumeroCheque() {
        return numeroCheque;
    }

    /**
     * @return the numeroCif
     */
    public String getNumeroCif() {
        return numeroCif;
    }

    /**
     * @return the numeroClienteConcessionaria
     */
    public BigInteger getNumeroClienteConcessionaria() {
        return numeroClienteConcessionaria;
    }

    /**
     * @return the numeroConcessionaria
     */
    public Long getNumeroConcessionaria() {
        return numeroConcessionaria;
    }

    public String getNumeroConcessionariaStr() {
        if (numeroConcessionaria == null) {
            return null;
        }
        return String.valueOf(numeroConcessionaria);
    }

    /**
     * @return the numeroConta
     */
    public Long getNumeroConta() {
        return numeroConta;
    }

    /**
     * @return the numeroConta formatado
     */
    public String getNumeroContaStr() {
        if (numeroAgencia == null || numeroProduto == null || numeroConta == null) {
            return null;
        }
        return numeroAgencia.toString() + "." + numeroProduto.toString() + "." + numeroConta.toString();
    }

    /**
     * @return the numeroContaOrigemDestino
     */
    public Long getNumeroContaOrigemDestino() {
        return numeroContaOrigemDestino;
    }

    /**
     * @return the numeroContrato
     */
    public Long getNumeroContrato() {
        return numeroContrato;
    }

    /**
     * @return the numeroCpfCnpjContribuinte
     */
    public Long getNumeroCpfCnpjContribuinte() {
        return numeroCpfCnpjContribuinte;
    }

    /**
     * @return the numeroEstabelecimento
     */
    public Long getNumeroEstabelecimento() {
        return numeroEstabelecimento;
    }

    public String getNumeroEstabelecimentoStr() {
        if (numeroEstabelecimento == null) {
            return null;
        }
        return String.valueOf(numeroEstabelecimento);
    }

    /**
     * @return the numeroFinalCheque
     */
    public Long getNumeroFinalCheque() {
        return numeroFinalCheque;
    }

    /**
     * @return the numeroFormaTransacao
     */
    public Short getNumeroFormaTransacao() {
        return numeroFormaTransacao;
    }

    /**
     * @return the numeroInicialCheque
     */
    public Long getNumeroInicialCheque() {
        return numeroInicialCheque;
    }

    /**
     * @return the numeroLote
     */
    public Long getNumeroLote() {
        return numeroLote;
    }

    /**
     * @return the numeroModalidadeTransacao
     */
    public Short getNumeroModalidadeTransacao() {
        return numeroModalidadeTransacao;
    }

    /**
     * @return the numeroMoeda
     */
    public Short getNumeroMoeda() {
        return numeroMoeda;
    }

    /**
     * @return the numeroMoedaBloqueto
     */
    public Short getNumeroMoedaBloqueto() {
        return numeroMoedaBloqueto;
    }

    /**
     * @return the numeroNivelRiscoOrigemDestino
     */
    public Byte getNumeroNivelRiscoOrigemDestino() {
        return numeroNivelRiscoOrigemDestino;
    }

    /**
     * @return the numeroSequencialUnicoOrigem
     */
    public BigInteger getNumeroSequencialUnicoOrigem() {
        return numeroSequencialUnicoOrigem;
    }

    /**
     * @return the numeroSequencialUnicoTransacao
     */
    public BigInteger getNumeroSequencialUnicoTransacao() {
        return numeroSequencialUnicoTransacao;
    }

    /**
     * @return the numeroOperador
     */
    public BigInteger getNumeroOperador() {
        return numeroOperador;
    }

    /**
     * @return the numeroOperador formatado
     */
    public String getNumeroOperadorStr() {
        if (numeroOperador == null) {
            return null;
        }
        return numeroOperador.toString();
    }

    /**
     * @return the numeroOperadoraTelefonia
     */
    public Short getNumeroOperadoraTelefonia() {
        return numeroOperadoraTelefonia;
    }

    /**
     * @return the numeroOrigemTransacao
     */
    public Long getNumeroOrigemTransacao() {
        return numeroOrigemTransacao;
    }

    /**
     * @return the numeroPeriodoReferencia
     */
    public Integer getNumeroPeriodoReferencia() {
        return numeroPeriodoReferencia;
    }

    /**
     * @return the numeroPisNis
     */
    public BigInteger getNumeroPisNis() {
        return numeroPisNis;
    }

    /**
     * @return the numeroPrestacaoHabitacao
     */
    public Short getNumeroPrestacaoHabitacao() {
        return numeroPrestacaoHabitacao;
    }

    /**
     * @return the numeroProcessamento
     */
    public BigInteger getNumeroProcessamento() {
        return numeroProcessamento;
    }

    /**
     * @return the numeroProduto
     */
    public Short getNumeroProduto() {
        return numeroProduto;
    }

    /**
     * @return the numeroProdutoOrigemDestino
     */
    public Short getNumeroProdutoOrigemDestino() {
        return numeroProdutoOrigemDestino;
    }

    /**
     * @return the numeroRedeIso
     */
    public Long getNumeroRedeIso() {
        return numeroRedeIso;
    }

    /**
     * @return the numeroRenavamVeiculo
     */
    public Long getNumeroRenavamVeiculo() {
        return numeroRenavamVeiculo;
    }

    /**
     * @return the numeroSacado
     */
    public Long getNumeroSacado() {
        return numeroSacado;
    }

    /**
     * @return the numeroSequencialImpresso
     */
    public Long getNumeroSequencialImpresso() {
        return numeroSequencialImpresso;
    }

    /**
     * @return the numeroSublote
     */
    public Short getNumeroSublote() {
        return numeroSublote;
    }

    /**
     * @return the numeroTipoCartao
     */
    public Short getNumeroTipoCartao() {
        return numeroTipoCartao;
    }

    /**
     * @return the numeroTipoTransacao
     */
    public Short getNumeroTipoTransacao() {
        return numeroTipoTransacao;
    }

    /**
     * @return the numeroTitular
     */
    public Byte getNumeroTitular() {
        return numeroTitular;
    }

    /**
     * @return the numeroTitularOrigemDestino
     */
    public Byte getNumeroTitularOrigemDestino() {
        return numeroTitularOrigemDestino;
    }

    /**
     * @return the numeroVersao
     */
    public Integer getNumeroVersao() {
        return numeroVersao;
    }

    /**
     * @return the numeroVersaoOrigemDestino
     */
    public Integer getNumeroVersaoOrigemDestino() {
        return numeroVersaoOrigemDestino;
    }

    /**
     * @return the numeroVersaoTerminal
     */
    public Integer getNumeroVersaoTerminal() {
        return numeroVersaoTerminal;
    }

    /**
     * @return the percentualValorAmortizado
     */
    public Double getPercentualValorAmortizado() {
        return percentualValorAmortizado;
    }

    /**
     * @return the quantidadeLimiteDisponivel
     */
    public Integer getQuantidadeLimiteDisponivel() {
        return quantidadeLimiteDisponivel;
    }

    /**
     * @return the quantidadeParcelaRestante
     */
    public Integer getQuantidadeParcelaRestante() {
        return quantidadeParcelaRestante;
    }

    /**
     * @return the quantidadeParcelaTotal
     */
    public Integer getQuantidadeParcelaTotal() {
        return quantidadeParcelaTotal;
    }

    /**
     * @return the quantidadeSolicitadaCheque
     */
    public Integer getQuantidadeSolicitadaCheque() {
        return quantidadeSolicitadaCheque;
    }

    /**
     * @return the quantidadeTempoCompensacao
     */
    public Integer getQuantidadeTempoCompensacao() {
        return quantidadeTempoCompensacao;
    }

    /**
     * @return the siglaPaisCambio
     */
    public String getSiglaPaisCambio() {
        return siglaPaisCambio;
    }

    /**
     * @return the siglaPaisOrigemDestino
     */
    public String getSiglaPaisOrigemDestino() {
        return siglaPaisOrigemDestino;
    }

    /**
     * @return the siglaSistema
     */
    public String getSiglaSistema() {
        return siglaSistema;
    }

    /**
     * @return the siglaUFAgencia
     */
    public String getSiglaUFAgencia() {
        return siglaUFAgencia;
    }

    /**
     * @return the siglaUFAgenciaOrigemDestino
     */
    public String getSiglaUFAgenciaOrigemDestino() {
        return siglaUFAgenciaOrigemDestino;
    }

    /**
     * @return the siglaUFOperador
     */
    public String getSiglaUFOperador() {
        return siglaUFOperador;
    }

    /**
     * @return the siglaUFTerminal
     */
    public String getSiglaUFTerminal() {
        return siglaUFTerminal;
    }

    /**
     * @return the dataHoraTransacaoLocal
     */
    public Date getDataHoraTransacaoLocal() {
        return dataHoraTransacaoLocal;
    }

    /**
     * @return the dataHoraTransacaoServidor
     */
    public Date getDataHoraTransacaoServidor() {
        return dataHoraTransacaoServidor;
    }

    /**
     * @return the valorBloqueadoFimDia
     */
    public Double getValorBloqueadoFimDia() {
        return valorBloqueadoFimDia;
    }

    /**
     * @return the valorCreditoRotativo
     */
    public Double getValorCreditoRotativo() {
        return valorCreditoRotativo;
    }

    /**
     * @return the valorLiberado
     */
    public Double getValorLiberado() {
        return valorLiberado;
    }

    /**
     * @return the valorLimiteDisponivel
     */
    public Double getValorLimiteDisponivel() {
        return valorLimiteDisponivel;
    }

    /**
     * @return the valorLimiteOperacao
     */
    public Double getValorLimiteOperacao() {
        return valorLimiteOperacao;
    }

    /**
     * @return the valorSaldoConta
     */
    public Double getValorSaldoConta() {
        return valorSaldoConta;
    }

    /**
     * @return the valorSaldoDisponivel
     */
    public Double getValorSaldoDisponivel() {
        return valorSaldoDisponivel;
    }

    /**
     * @return the valorTotalFimDia
     */
    public Double getValorTotalFimDia() {
        return valorTotalFimDia;
    }

    /**
     * @return the valorTransacao
     */
    public Double getValorTransacao() {
        return valorTransacao;
    }

    /**
     * @return the valorTransacaoOriginal
     */
    public Double getValorTransacaoOriginal() {
        return valorTransacaoOriginal;
    }

    /**
     * @return the valorTroco
     */
    public Double getValorTroco() {
        return valorTroco;
    }

    /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((codigoBarra == null) ? 0 : codigoBarra.hashCode());
        result = prime * result
                + ((codigoCartao == null) ? 0 : codigoCartao.hashCode());
        result = prime
                * result
                + ((codigoCodificacaoTransacao == null) ? 0
                : codigoCodificacaoTransacao.hashCode());
        result = prime
                * result
                + ((codigoComandoAcerto == null) ? 0 : codigoComandoAcerto
                .hashCode());
        result = prime
                * result
                + ((codigoCompensacao == null) ? 0 : codigoCompensacao
                .hashCode());
        result = prime * result
                + ((codigoConvenio == null) ? 0 : codigoConvenio.hashCode());
        result = prime
                * result
                + ((codigoDetalheComando == null) ? 0 : codigoDetalheComando
                .hashCode());
        result = prime * result
                + ((codigoDocumento == null) ? 0 : codigoDocumento.hashCode());
        result = prime
                * result
                + ((codigoFundoInvestimento == null) ? 0
                : codigoFundoInvestimento.hashCode());
        result = prime
                * result
                + ((codigoIdentificadorCliente == null) ? 0
                : codigoIdentificadorCliente.hashCode());
        result = prime
                * result
                + ((codigoIdentificadorClienteOrigemDestino == null) ? 0
                : codigoIdentificadorClienteOrigemDestino.hashCode());
        result = prime
                * result
                + ((codigoIdentificadorConta == null) ? 0
                : codigoIdentificadorConta.hashCode());
        result = prime
                * result
                + ((codigoIdentificadorContaOrigemDestino == null) ? 0
                : codigoIdentificadorContaOrigemDestino.hashCode());
        result = prime
                * result
                + ((codigoIdentificadorTed == null) ? 0
                : codigoIdentificadorTed.hashCode());
        result = prime
                * result
                + ((codigoModalidadeTed == null) ? 0 : codigoModalidadeTed
                .hashCode());
        result = prime
                * result
                + ((codigoMotivoContraordem == null) ? 0
                : codigoMotivoContraordem.hashCode());
        result = prime
                * result
                + ((codigoPlacaVeiculo == null) ? 0 : codigoPlacaVeiculo
                .hashCode());
        result = prime
                * result
                + ((codigoPrefeitura == null) ? 0 : codigoPrefeitura.hashCode());
        result = prime
                * result
                + ((codigoReceitaFederal == null) ? 0 : codigoReceitaFederal
                .hashCode());
        result = prime
                * result
                + ((codigoRespostaIso == null) ? 0 : codigoRespostaIso
                .hashCode());
        result = prime * result
                + ((codigoServico == null) ? 0 : codigoServico.hashCode());
        result = prime * result
                + ((codigoTerminal == null) ? 0 : codigoTerminal.hashCode());
        result = prime * result
                + ((codigoTipoChave == null) ? 0 : codigoTipoChave.hashCode());
        result = prime * result
                + ((codigoTipoDarf == null) ? 0 : codigoTipoDarf.hashCode());
        result = prime
                * result
                + ((codigoTipoEmpresa == null) ? 0 : codigoTipoEmpresa
                .hashCode());
        result = prime
                * result
                + ((codigoTipoLimite == null) ? 0 : codigoTipoLimite.hashCode());
        result = prime
                * result
                + ((codigoTipoMensagemIso == null) ? 0 : codigoTipoMensagemIso
                .hashCode());
        result = prime
                * result
                + ((codigoTipoNegocio == null) ? 0 : codigoTipoNegocio
                .hashCode());
        result = prime
                * result
                + ((codigoTipoPagamentoDETRAN == null) ? 0
                : codigoTipoPagamentoDETRAN.hashCode());
        result = prime
                * result
                + ((codigoTransacaoInicio == null) ? 0 : codigoTransacaoInicio
                .hashCode());
        result = prime
                * result
                + ((dataComandoCliente == null) ? 0 : dataComandoCliente
                .hashCode());
        result = prime
                * result
                + ((dataContabilTransacao == null) ? 0 : dataContabilTransacao
                .hashCode());
        result = prime
                * result
                + ((dataHoraTransacaoLocal == null) ? 0
                : dataHoraTransacaoLocal.hashCode());
        result = prime
                * result
                + ((dataHoraTransacaoServidor == null) ? 0
                : dataHoraTransacaoServidor.hashCode());
        result = prime
                * result
                + ((dataVencimentoPagamento == null) ? 0
                : dataVencimentoPagamento.hashCode());
        result = prime
                * result
                + ((detalheTransacao == null) ? 0 : detalheTransacao.hashCode());
        result = prime
                * result
                + ((diaVencimentoParcela == null) ? 0 : diaVencimentoParcela
                .hashCode());
        result = prime
                * result
                + ((indicadorCartaoDigitacao == null) ? 0
                : indicadorCartaoDigitacao.hashCode());
        result = prime * result
                + ((indicadorChave == null) ? 0 : indicadorChave.hashCode());
        result = prime
                * result
                + ((indicadorChaveSecundaria == null) ? 0
                : indicadorChaveSecundaria.hashCode());
        result = prime * result
                + ((indicadorChip == null) ? 0 : indicadorChip.hashCode());
        result = prime
                * result
                + ((indicadorContaDigitacao == null) ? 0
                : indicadorContaDigitacao.hashCode());
        result = prime
                * result
                + ((indicadorDebitoConta == null) ? 0 : indicadorDebitoConta
                .hashCode());
        result = prime
                * result
                + ((indicadorDiaUtil == null) ? 0 : indicadorDiaUtil.hashCode());
        result = prime
                * result
                + ((indicadorEmprestimoAutomatico == null) ? 0
                : indicadorEmprestimoAutomatico.hashCode());
        result = prime
                * result
                + ((indicadorFeriado == null) ? 0 : indicadorFeriado.hashCode());
        result = prime * result
                + ((indicadorFraude == null) ? 0 : indicadorFraude.hashCode());
        result = prime
                * result
                + ((indicadorNaturezaOperacao == null) ? 0
                : indicadorNaturezaOperacao.hashCode());
        result = prime
                * result
                + ((indicadorOrigemTransacao == null) ? 0
                : indicadorOrigemTransacao.hashCode());
        result = prime
                * result
                + ((indicadorSituacaoChip == null) ? 0 : indicadorSituacaoChip
                .hashCode());
        result = prime
                * result
                + ((indicadorSituacaoLeitora == null) ? 0
                : indicadorSituacaoLeitora.hashCode());
        result = prime
                * result
                + ((indicadorUsoSenha == null) ? 0 : indicadorUsoSenha
                .hashCode());
        result = prime * result
                + ((numeroAgencia == null) ? 0 : numeroAgencia.hashCode());
        result = prime
                * result
                + ((numeroAgenciaOrigemDestino == null) ? 0
                : numeroAgenciaOrigemDestino.hashCode());
        result = prime
                * result
                + ((numeroAgenciaVinculacao == null) ? 0
                : numeroAgenciaVinculacao.hashCode());
        result = prime
                * result
                + ((numeroBanco == null) ? 0 : numeroBanco
                .hashCode());
        result = prime
                * result
                + ((numeroBancoBloqueto == null) ? 0 : numeroBancoBloqueto
                .hashCode());
        result = prime
                * result
                + ((numeroBancoOrigemDestino == null) ? 0
                : numeroBancoOrigemDestino.hashCode());
        result = prime * result
                + ((numeroCanal == null) ? 0 : numeroCanal.hashCode());
        result = prime
                * result
                + ((numeroCartaoCreditoAvulso == null) ? 0
                : numeroCartaoCreditoAvulso.hashCode());
        result = prime
                * result
                + ((numeroCartaoSocial == null) ? 0 : numeroCartaoSocial
                .hashCode());
        result = prime * result
                + ((numeroCedente == null) ? 0 : numeroCedente.hashCode());
        result = prime
                * result
                + ((numeroCelularBeneficiado == null) ? 0
                : numeroCelularBeneficiado.hashCode());
        result = prime
                * result
                + ((numeroCepAgencia == null) ? 0 : numeroCepAgencia.hashCode());
        result = prime
                * result
                + ((numeroCepAgenciaOrigemDestino == null) ? 0
                : numeroCepAgenciaOrigemDestino.hashCode());
        result = prime
                * result
                + ((numeroCepCliente == null) ? 0 : numeroCepCliente.hashCode());
        result = prime
                * result
                + ((numeroCepEstabelecimento == null) ? 0
                : numeroCepEstabelecimento.hashCode());
        result = prime
                * result
                + ((numeroCepTerminal == null) ? 0 : numeroCepTerminal
                .hashCode());
        result = prime * result
                + ((numeroCheque == null) ? 0 : numeroCheque.hashCode());
        result = prime * result
                + ((numeroCif == null) ? 0 : numeroCif.hashCode());
        result = prime
                * result
                + ((numeroClienteConcessionaria == null) ? 0
                : numeroClienteConcessionaria.hashCode());
        result = prime
                * result
                + ((numeroConcessionaria == null) ? 0 : numeroConcessionaria
                .hashCode());
        result = prime * result
                + ((numeroConta == null) ? 0 : numeroConta.hashCode());
        result = prime
                * result
                + ((numeroContaOrigemDestino == null) ? 0
                : numeroContaOrigemDestino.hashCode());
        result = prime * result
                + ((numeroContrato == null) ? 0 : numeroContrato.hashCode());
        result = prime
                * result
                + ((numeroCpfCnpjContribuinte == null) ? 0
                : numeroCpfCnpjContribuinte.hashCode());
        result = prime
                * result
                + ((numeroEstabelecimento == null) ? 0 : numeroEstabelecimento
                .hashCode());
        result = prime
                * result
                + ((numeroFinalCheque == null) ? 0 : numeroFinalCheque
                .hashCode());
        result = prime
                * result
                + ((numeroFormaTransacao == null) ? 0 : numeroFormaTransacao
                .hashCode());
        result = prime
                * result
                + ((numeroInicialCheque == null) ? 0 : numeroInicialCheque
                .hashCode());
        result = prime * result
                + ((numeroLote == null) ? 0 : numeroLote.hashCode());
        result = prime
                * result
                + ((numeroModalidadeTransacao == null) ? 0
                : numeroModalidadeTransacao.hashCode());
        result = prime * result
                + ((numeroMoeda == null) ? 0 : numeroMoeda.hashCode());
        result = prime
                * result
                + ((numeroMoedaBloqueto == null) ? 0 : numeroMoedaBloqueto
                .hashCode());
        result = prime
                * result
                + ((numeroNivelRiscoOrigemDestino == null) ? 0
                : numeroNivelRiscoOrigemDestino.hashCode());
        result = prime * result
                + ((numeroOperador == null) ? 0 : numeroOperador.hashCode());
        result = prime
                * result
                + ((numeroOperadoraTelefonia == null) ? 0
                : numeroOperadoraTelefonia.hashCode());
        result = prime
                * result
                + ((numeroOrigemTransacao == null) ? 0 : numeroOrigemTransacao
                .hashCode());
        result = prime
                * result
                + ((numeroPeriodoReferencia == null) ? 0
                : numeroPeriodoReferencia.hashCode());
        result = prime * result
                + ((numeroPisNis == null) ? 0 : numeroPisNis.hashCode());
        result = prime
                * result
                + ((numeroPrestacaoHabitacao == null) ? 0
                : numeroPrestacaoHabitacao.hashCode());
        result = prime
                * result
                + ((numeroProcessamento == null) ? 0 : numeroProcessamento
                .hashCode());
        result = prime * result
                + ((numeroProduto == null) ? 0 : numeroProduto.hashCode());
        result = prime
                * result
                + ((numeroProdutoOrigemDestino == null) ? 0
                : numeroProdutoOrigemDestino.hashCode());
        result = prime * result
                + ((numeroRedeIso == null) ? 0 : numeroRedeIso.hashCode());
        result = prime
                * result
                + ((numeroRenavamVeiculo == null) ? 0 : numeroRenavamVeiculo
                .hashCode());
        result = prime * result
                + ((numeroSacado == null) ? 0 : numeroSacado.hashCode());
        result = prime
                * result
                + ((numeroSequencialImpresso == null) ? 0
                : numeroSequencialImpresso.hashCode());
        result = prime
                * result
                + ((numeroSequencialUnicoOrigem == null) ? 0
                : numeroSequencialUnicoOrigem.hashCode());
        result = prime
                * result
                + ((numeroSequencialUnicoTransacao == null) ? 0
                : numeroSequencialUnicoTransacao.hashCode());
        result = prime * result
                + ((numeroSublote == null) ? 0 : numeroSublote.hashCode());
        result = prime
                * result
                + ((numeroTipoCartao == null) ? 0 : numeroTipoCartao.hashCode());
        result = prime
                * result
                + ((numeroTipoTransacao == null) ? 0 : numeroTipoTransacao
                .hashCode());
        result = prime * result
                + ((numeroTitular == null) ? 0 : numeroTitular.hashCode());
        result = prime
                * result
                + ((numeroTitularOrigemDestino == null) ? 0
                : numeroTitularOrigemDestino.hashCode());
        result = prime * result
                + ((numeroVersao == null) ? 0 : numeroVersao.hashCode());
        result = prime
                * result
                + ((numeroVersaoOrigemDestino == null) ? 0
                : numeroVersaoOrigemDestino.hashCode());
        result = prime
                * result
                + ((numeroVersaoTerminal == null) ? 0 : numeroVersaoTerminal
                .hashCode());
        result = prime
                * result
                + ((percentualValorAmortizado == null) ? 0
                : percentualValorAmortizado.hashCode());
        result = prime
                * result
                + ((quantidadeLimiteDisponivel == null) ? 0
                : quantidadeLimiteDisponivel.hashCode());
        result = prime
                * result
                + ((quantidadeParcelaRestante == null) ? 0
                : quantidadeParcelaRestante.hashCode());
        result = prime
                * result
                + ((quantidadeParcelaTotal == null) ? 0
                : quantidadeParcelaTotal.hashCode());
        result = prime
                * result
                + ((quantidadeSolicitadaCheque == null) ? 0
                : quantidadeSolicitadaCheque.hashCode());
        result = prime
                * result
                + ((quantidadeTempoCompensacao == null) ? 0
                : quantidadeTempoCompensacao.hashCode());
        result = prime * result
                + ((siglaPaisCambio == null) ? 0 : siglaPaisCambio.hashCode());
        result = prime
                * result
                + ((siglaPaisOrigemDestino == null) ? 0
                : siglaPaisOrigemDestino.hashCode());
        result = prime * result
                + ((siglaSistema == null) ? 0 : siglaSistema.hashCode());
        result = prime * result
                + ((siglaUFAgencia == null) ? 0 : siglaUFAgencia.hashCode());
        result = prime
                * result
                + ((siglaUFAgenciaOrigemDestino == null) ? 0
                : siglaUFAgenciaOrigemDestino.hashCode());
        result = prime * result
                + ((siglaUFOperador == null) ? 0 : siglaUFOperador.hashCode());
        result = prime * result
                + ((siglaUFTerminal == null) ? 0 : siglaUFTerminal.hashCode());
        result = prime
                * result
                + ((valorBloqueadoFimDia == null) ? 0 : valorBloqueadoFimDia
                .hashCode());
        result = prime
                * result
                + ((valorCreditoRotativo == null) ? 0 : valorCreditoRotativo
                .hashCode());
        result = prime * result
                + ((valorLiberado == null) ? 0 : valorLiberado.hashCode());
        result = prime
                * result
                + ((valorLimiteDisponivel == null) ? 0 : valorLimiteDisponivel
                .hashCode());
        result = prime
                * result
                + ((valorLimiteOperacao == null) ? 0 : valorLimiteOperacao
                .hashCode());
        result = prime * result
                + ((valorSaldoConta == null) ? 0 : valorSaldoConta.hashCode());
        result = prime
                * result
                + ((valorSaldoDisponivel == null) ? 0 : valorSaldoDisponivel
                .hashCode());
        result = prime
                * result
                + ((valorTotalFimDia == null) ? 0 : valorTotalFimDia.hashCode());
        result = prime * result
                + ((valorTransacao == null) ? 0 : valorTransacao.hashCode());
        result = prime
                * result
                + ((valorTransacaoOriginal == null) ? 0
                : valorTransacaoOriginal.hashCode());
        result = prime * result
                + ((valorTroco == null) ? 0 : valorTroco.hashCode());
        return result;
    }

    /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TransacaoRaw)) {
            return false;
        }
        TransacaoRaw other = (TransacaoRaw) obj;
        if (codigoBarra == null) {
            if (other.codigoBarra != null) {
                return false;
            }
        } else if (!codigoBarra.equals(other.codigoBarra)) {
            return false;
        }
        if (codigoCartao == null) {
            if (other.codigoCartao != null) {
                return false;
            }
        } else if (!codigoCartao.equals(other.codigoCartao)) {
            return false;
        }
        if (codigoCodificacaoTransacao == null) {
            if (other.codigoCodificacaoTransacao != null) {
                return false;
            }
        } else if (!codigoCodificacaoTransacao
                .equals(other.codigoCodificacaoTransacao)) {
            return false;
        }
        if (codigoComandoAcerto == null) {
            if (other.codigoComandoAcerto != null) {
                return false;
            }
        } else if (!codigoComandoAcerto.equals(other.codigoComandoAcerto)) {
            return false;
        }
        if (codigoCompensacao == null) {
            if (other.codigoCompensacao != null) {
                return false;
            }
        } else if (!codigoCompensacao.equals(other.codigoCompensacao)) {
            return false;
        }
        if (codigoConvenio == null) {
            if (other.codigoConvenio != null) {
                return false;
            }
        } else if (!codigoConvenio.equals(other.codigoConvenio)) {
            return false;
        }
        if (codigoDetalheComando == null) {
            if (other.codigoDetalheComando != null) {
                return false;
            }
        } else if (!codigoDetalheComando.equals(other.codigoDetalheComando)) {
            return false;
        }
        if (codigoDocumento == null) {
            if (other.codigoDocumento != null) {
                return false;
            }
        } else if (!codigoDocumento.equals(other.codigoDocumento)) {
            return false;
        }
        if (codigoFundoInvestimento == null) {
            if (other.codigoFundoInvestimento != null) {
                return false;
            }
        } else if (!codigoFundoInvestimento
                .equals(other.codigoFundoInvestimento)) {
            return false;
        }
        if (codigoIdentificadorCliente == null) {
            if (other.codigoIdentificadorCliente != null) {
                return false;
            }
        } else if (!codigoIdentificadorCliente
                .equals(other.codigoIdentificadorCliente)) {
            return false;
        }
        if (codigoIdentificadorClienteOrigemDestino == null) {
            if (other.codigoIdentificadorClienteOrigemDestino != null) {
                return false;
            }
        } else if (!codigoIdentificadorClienteOrigemDestino
                .equals(other.codigoIdentificadorClienteOrigemDestino)) {
            return false;
        }
        if (codigoIdentificadorConta == null) {
            if (other.codigoIdentificadorConta != null) {
                return false;
            }
        } else if (!codigoIdentificadorConta
                .equals(other.codigoIdentificadorConta)) {
            return false;
        }
        if (codigoIdentificadorContaOrigemDestino == null) {
            if (other.codigoIdentificadorContaOrigemDestino != null) {
                return false;
            }
        } else if (!codigoIdentificadorContaOrigemDestino
                .equals(other.codigoIdentificadorContaOrigemDestino)) {
            return false;
        }
        if (codigoIdentificadorTed == null) {
            if (other.codigoIdentificadorTed != null) {
                return false;
            }
        } else if (!codigoIdentificadorTed.equals(other.codigoIdentificadorTed)) {
            return false;
        }
        if (codigoModalidadeTed == null) {
            if (other.codigoModalidadeTed != null) {
                return false;
            }
        } else if (!codigoModalidadeTed.equals(other.codigoModalidadeTed)) {
            return false;
        }
        if (codigoMotivoContraordem == null) {
            if (other.codigoMotivoContraordem != null) {
                return false;
            }
        } else if (!codigoMotivoContraordem
                .equals(other.codigoMotivoContraordem)) {
            return false;
        }
        if (codigoPlacaVeiculo == null) {
            if (other.codigoPlacaVeiculo != null) {
                return false;
            }
        } else if (!codigoPlacaVeiculo.equals(other.codigoPlacaVeiculo)) {
            return false;
        }
        if (codigoPrefeitura == null) {
            if (other.codigoPrefeitura != null) {
                return false;
            }
        } else if (!codigoPrefeitura.equals(other.codigoPrefeitura)) {
            return false;
        }
        if (codigoReceitaFederal == null) {
            if (other.codigoReceitaFederal != null) {
                return false;
            }
        } else if (!codigoReceitaFederal.equals(other.codigoReceitaFederal)) {
            return false;
        }
        if (codigoRespostaIso == null) {
            if (other.codigoRespostaIso != null) {
                return false;
            }
        } else if (!codigoRespostaIso.equals(other.codigoRespostaIso)) {
            return false;
        }
        if (codigoServico == null) {
            if (other.codigoServico != null) {
                return false;
            }
        } else if (!codigoServico.equals(other.codigoServico)) {
            return false;
        }
        if (codigoTerminal == null) {
            if (other.codigoTerminal != null) {
                return false;
            }
        } else if (!codigoTerminal.equals(other.codigoTerminal)) {
            return false;
        }
        if (codigoTipoChave == null) {
            if (other.codigoTipoChave != null) {
                return false;
            }
        } else if (!codigoTipoChave.equals(other.codigoTipoChave)) {
            return false;
        }
        if (codigoTipoDarf == null) {
            if (other.codigoTipoDarf != null) {
                return false;
            }
        } else if (!codigoTipoDarf.equals(other.codigoTipoDarf)) {
            return false;
        }
        if (codigoTipoEmpresa == null) {
            if (other.codigoTipoEmpresa != null) {
                return false;
            }
        } else if (!codigoTipoEmpresa.equals(other.codigoTipoEmpresa)) {
            return false;
        }
        if (codigoTipoLimite == null) {
            if (other.codigoTipoLimite != null) {
                return false;
            }
        } else if (!codigoTipoLimite.equals(other.codigoTipoLimite)) {
            return false;
        }
        if (codigoTipoMensagemIso == null) {
            if (other.codigoTipoMensagemIso != null) {
                return false;
            }
        } else if (!codigoTipoMensagemIso.equals(other.codigoTipoMensagemIso)) {
            return false;
        }
        if (codigoTipoNegocio == null) {
            if (other.codigoTipoNegocio != null) {
                return false;
            }
        } else if (!codigoTipoNegocio.equals(other.codigoTipoNegocio)) {
            return false;
        }
        if (codigoTipoPagamentoDETRAN == null) {
            if (other.codigoTipoPagamentoDETRAN != null) {
                return false;
            }
        } else if (!codigoTipoPagamentoDETRAN
                .equals(other.codigoTipoPagamentoDETRAN)) {
            return false;
        }
        if (codigoTransacaoInicio == null) {
            if (other.codigoTransacaoInicio != null) {
                return false;
            }
        } else if (!codigoTransacaoInicio.equals(other.codigoTransacaoInicio)) {
            return false;
        }
        if (dataComandoCliente == null) {
            if (other.dataComandoCliente != null) {
                return false;
            }
        } else if (!dataComandoCliente.equals(other.dataComandoCliente)) {
            return false;
        }
        if (dataContabilTransacao == null) {
            if (other.dataContabilTransacao != null) {
                return false;
            }
        } else if (!dataContabilTransacao.equals(other.dataContabilTransacao)) {
            return false;
        }
        if (dataHoraTransacaoLocal == null) {
            if (other.dataHoraTransacaoLocal != null) {
                return false;
            }
        } else if (!dataHoraTransacaoLocal.equals(other.dataHoraTransacaoLocal)) {
            return false;
        }
        if (dataHoraTransacaoServidor == null) {
            if (other.dataHoraTransacaoServidor != null) {
                return false;
            }
        } else if (!dataHoraTransacaoServidor
                .equals(other.dataHoraTransacaoServidor)) {
            return false;
        }
        if (dataVencimentoPagamento == null) {
            if (other.dataVencimentoPagamento != null) {
                return false;
            }
        } else if (!dataVencimentoPagamento
                .equals(other.dataVencimentoPagamento)) {
            return false;
        }
        if (detalheTransacao == null) {
            if (other.detalheTransacao != null) {
                return false;
            }
        } else if (!detalheTransacao.equals(other.detalheTransacao)) {
            return false;
        }
        if (diaVencimentoParcela == null) {
            if (other.diaVencimentoParcela != null) {
                return false;
            }
        } else if (!diaVencimentoParcela.equals(other.diaVencimentoParcela)) {
            return false;
        }
        if (indicadorCartaoDigitacao == null) {
            if (other.indicadorCartaoDigitacao != null) {
                return false;
            }
        } else if (!indicadorCartaoDigitacao
                .equals(other.indicadorCartaoDigitacao)) {
            return false;
        }
        if (indicadorChave == null) {
            if (other.indicadorChave != null) {
                return false;
            }
        } else if (!indicadorChave.equals(other.indicadorChave)) {
            return false;
        }
        if (indicadorChaveSecundaria == null) {
            if (other.indicadorChaveSecundaria != null) {
                return false;
            }
        } else if (!indicadorChaveSecundaria
                .equals(other.indicadorChaveSecundaria)) {
            return false;
        }
        if (indicadorChip == null) {
            if (other.indicadorChip != null) {
                return false;
            }
        } else if (!indicadorChip.equals(other.indicadorChip)) {
            return false;
        }
        if (indicadorContaDigitacao == null) {
            if (other.indicadorContaDigitacao != null) {
                return false;
            }
        } else if (!indicadorContaDigitacao
                .equals(other.indicadorContaDigitacao)) {
            return false;
        }
        if (indicadorDebitoConta == null) {
            if (other.indicadorDebitoConta != null) {
                return false;
            }
        } else if (!indicadorDebitoConta.equals(other.indicadorDebitoConta)) {
            return false;
        }
        if (indicadorDiaUtil == null) {
            if (other.indicadorDiaUtil != null) {
                return false;
            }
        } else if (!indicadorDiaUtil.equals(other.indicadorDiaUtil)) {
            return false;
        }
        if (indicadorEmprestimoAutomatico == null) {
            if (other.indicadorEmprestimoAutomatico != null) {
                return false;
            }
        } else if (!indicadorEmprestimoAutomatico
                .equals(other.indicadorEmprestimoAutomatico)) {
            return false;
        }
        if (indicadorFeriado == null) {
            if (other.indicadorFeriado != null) {
                return false;
            }
        } else if (!indicadorFeriado.equals(other.indicadorFeriado)) {
            return false;
        }
        if (indicadorFraude == null) {
            if (other.indicadorFraude != null) {
                return false;
            }
        } else if (!indicadorFraude.equals(other.indicadorFraude)) {
            return false;
        }
        if (indicadorNaturezaOperacao == null) {
            if (other.indicadorNaturezaOperacao != null) {
                return false;
            }
        } else if (!indicadorNaturezaOperacao
                .equals(other.indicadorNaturezaOperacao)) {
            return false;
        }
        if (indicadorOrigemTransacao == null) {
            if (other.indicadorOrigemTransacao != null) {
                return false;
            }
        } else if (!indicadorOrigemTransacao
                .equals(other.indicadorOrigemTransacao)) {
            return false;
        }
        if (indicadorSituacaoChip == null) {
            if (other.indicadorSituacaoChip != null) {
                return false;
            }
        } else if (!indicadorSituacaoChip.equals(other.indicadorSituacaoChip)) {
            return false;
        }
        if (indicadorSituacaoLeitora == null) {
            if (other.indicadorSituacaoLeitora != null) {
                return false;
            }
        } else if (!indicadorSituacaoLeitora
                .equals(other.indicadorSituacaoLeitora)) {
            return false;
        }
        if (indicadorUsoSenha == null) {
            if (other.indicadorUsoSenha != null) {
                return false;
            }
        } else if (!indicadorUsoSenha.equals(other.indicadorUsoSenha)) {
            return false;
        }
        if (numeroAgencia == null) {
            if (other.numeroAgencia != null) {
                return false;
            }
        } else if (!numeroAgencia.equals(other.numeroAgencia)) {
            return false;
        }
        if (numeroAgenciaOrigemDestino == null) {
            if (other.numeroAgenciaOrigemDestino != null) {
                return false;
            }
        } else if (!numeroAgenciaOrigemDestino
                .equals(other.numeroAgenciaOrigemDestino)) {
            return false;
        }
        if (numeroAgenciaVinculacao == null) {
            if (other.numeroAgenciaVinculacao != null) {
                return false;
            }
        } else if (!numeroAgenciaVinculacao
                .equals(other.numeroAgenciaVinculacao)) {
            return false;
        }
        if (numeroBanco == null) {
            if (other.numeroBanco != null) {
                return false;
            }
        } else if (!numeroBanco.equals(other.numeroBanco)) {
            return false;
        }
        if (numeroBancoBloqueto == null) {
            if (other.numeroBancoBloqueto != null) {
                return false;
            }
        } else if (!numeroBancoBloqueto.equals(other.numeroBancoBloqueto)) {
            return false;
        }
        if (numeroBancoOrigemDestino == null) {
            if (other.numeroBancoOrigemDestino != null) {
                return false;
            }
        } else if (!numeroBancoOrigemDestino
                .equals(other.numeroBancoOrigemDestino)) {
            return false;
        }
        if (numeroCanal == null) {
            if (other.numeroCanal != null) {
                return false;
            }
        } else if (!numeroCanal.equals(other.numeroCanal)) {
            return false;
        }
        if (numeroCartaoCreditoAvulso == null) {
            if (other.numeroCartaoCreditoAvulso != null) {
                return false;
            }
        } else if (!numeroCartaoCreditoAvulso
                .equals(other.numeroCartaoCreditoAvulso)) {
            return false;
        }
        if (numeroCartaoSocial == null) {
            if (other.numeroCartaoSocial != null) {
                return false;
            }
        } else if (!numeroCartaoSocial.equals(other.numeroCartaoSocial)) {
            return false;
        }
        if (numeroCedente == null) {
            if (other.numeroCedente != null) {
                return false;
            }
        } else if (!numeroCedente.equals(other.numeroCedente)) {
            return false;
        }
        if (numeroCelularBeneficiado == null) {
            if (other.numeroCelularBeneficiado != null) {
                return false;
            }
        } else if (!numeroCelularBeneficiado
                .equals(other.numeroCelularBeneficiado)) {
            return false;
        }
        if (numeroCepAgencia == null) {
            if (other.numeroCepAgencia != null) {
                return false;
            }
        } else if (!numeroCepAgencia.equals(other.numeroCepAgencia)) {
            return false;
        }
        if (numeroCepAgenciaOrigemDestino == null) {
            if (other.numeroCepAgenciaOrigemDestino != null) {
                return false;
            }
        } else if (!numeroCepAgenciaOrigemDestino
                .equals(other.numeroCepAgenciaOrigemDestino)) {
            return false;
        }
        if (numeroCepCliente == null) {
            if (other.numeroCepCliente != null) {
                return false;
            }
        } else if (!numeroCepCliente.equals(other.numeroCepCliente)) {
            return false;
        }
        if (numeroCepEstabelecimento == null) {
            if (other.numeroCepEstabelecimento != null) {
                return false;
            }
        } else if (!numeroCepEstabelecimento
                .equals(other.numeroCepEstabelecimento)) {
            return false;
        }
        if (numeroCepTerminal == null) {
            if (other.numeroCepTerminal != null) {
                return false;
            }
        } else if (!numeroCepTerminal.equals(other.numeroCepTerminal)) {
            return false;
        }
        if (numeroCheque == null) {
            if (other.numeroCheque != null) {
                return false;
            }
        } else if (!numeroCheque.equals(other.numeroCheque)) {
            return false;
        }
        if (numeroCif == null) {
            if (other.numeroCif != null) {
                return false;
            }
        } else if (!numeroCif.equals(other.numeroCif)) {
            return false;
        }
        if (numeroClienteConcessionaria == null) {
            if (other.numeroClienteConcessionaria != null) {
                return false;
            }
        } else if (!numeroClienteConcessionaria
                .equals(other.numeroClienteConcessionaria)) {
            return false;
        }
        if (numeroConcessionaria == null) {
            if (other.numeroConcessionaria != null) {
                return false;
            }
        } else if (!numeroConcessionaria.equals(other.numeroConcessionaria)) {
            return false;
        }
        if (numeroConta == null) {
            if (other.numeroConta != null) {
                return false;
            }
        } else if (!numeroConta.equals(other.numeroConta)) {
            return false;
        }
        if (numeroContaOrigemDestino == null) {
            if (other.numeroContaOrigemDestino != null) {
                return false;
            }
        } else if (!numeroContaOrigemDestino
                .equals(other.numeroContaOrigemDestino)) {
            return false;
        }
        if (numeroContrato == null) {
            if (other.numeroContrato != null) {
                return false;
            }
        } else if (!numeroContrato.equals(other.numeroContrato)) {
            return false;
        }
        if (numeroCpfCnpjContribuinte == null) {
            if (other.numeroCpfCnpjContribuinte != null) {
                return false;
            }
        } else if (!numeroCpfCnpjContribuinte
                .equals(other.numeroCpfCnpjContribuinte)) {
            return false;
        }
        if (numeroEstabelecimento == null) {
            if (other.numeroEstabelecimento != null) {
                return false;
            }
        } else if (!numeroEstabelecimento.equals(other.numeroEstabelecimento)) {
            return false;
        }
        if (numeroFinalCheque == null) {
            if (other.numeroFinalCheque != null) {
                return false;
            }
        } else if (!numeroFinalCheque.equals(other.numeroFinalCheque)) {
            return false;
        }
        if (numeroFormaTransacao == null) {
            if (other.numeroFormaTransacao != null) {
                return false;
            }
        } else if (!numeroFormaTransacao.equals(other.numeroFormaTransacao)) {
            return false;
        }
        if (numeroInicialCheque == null) {
            if (other.numeroInicialCheque != null) {
                return false;
            }
        } else if (!numeroInicialCheque.equals(other.numeroInicialCheque)) {
            return false;
        }
        if (numeroLote == null) {
            if (other.numeroLote != null) {
                return false;
            }
        } else if (!numeroLote.equals(other.numeroLote)) {
            return false;
        }
        if (numeroModalidadeTransacao == null) {
            if (other.numeroModalidadeTransacao != null) {
                return false;
            }
        } else if (!numeroModalidadeTransacao
                .equals(other.numeroModalidadeTransacao)) {
            return false;
        }
        if (numeroMoeda == null) {
            if (other.numeroMoeda != null) {
                return false;
            }
        } else if (!numeroMoeda.equals(other.numeroMoeda)) {
            return false;
        }
        if (numeroMoedaBloqueto == null) {
            if (other.numeroMoedaBloqueto != null) {
                return false;
            }
        } else if (!numeroMoedaBloqueto.equals(other.numeroMoedaBloqueto)) {
            return false;
        }
        if (numeroNivelRiscoOrigemDestino == null) {
            if (other.numeroNivelRiscoOrigemDestino != null) {
                return false;
            }
        } else if (!numeroNivelRiscoOrigemDestino
                .equals(other.numeroNivelRiscoOrigemDestino)) {
            return false;
        }
        if (numeroOperador == null) {
            if (other.numeroOperador != null) {
                return false;
            }
        } else if (!numeroOperador.equals(other.numeroOperador)) {
            return false;
        }
        if (numeroOperadoraTelefonia == null) {
            if (other.numeroOperadoraTelefonia != null) {
                return false;
            }
        } else if (!numeroOperadoraTelefonia
                .equals(other.numeroOperadoraTelefonia)) {
            return false;
        }
        if (numeroOrigemTransacao == null) {
            if (other.numeroOrigemTransacao != null) {
                return false;
            }
        } else if (!numeroOrigemTransacao.equals(other.numeroOrigemTransacao)) {
            return false;
        }
        if (numeroPeriodoReferencia == null) {
            if (other.numeroPeriodoReferencia != null) {
                return false;
            }
        } else if (!numeroPeriodoReferencia
                .equals(other.numeroPeriodoReferencia)) {
            return false;
        }
        if (numeroPisNis == null) {
            if (other.numeroPisNis != null) {
                return false;
            }
        } else if (!numeroPisNis.equals(other.numeroPisNis)) {
            return false;
        }
        if (numeroPrestacaoHabitacao == null) {
            if (other.numeroPrestacaoHabitacao != null) {
                return false;
            }
        } else if (!numeroPrestacaoHabitacao
                .equals(other.numeroPrestacaoHabitacao)) {
            return false;
        }
        if (numeroProcessamento == null) {
            if (other.numeroProcessamento != null) {
                return false;
            }
        } else if (!numeroProcessamento.equals(other.numeroProcessamento)) {
            return false;
        }
        if (numeroProduto == null) {
            if (other.numeroProduto != null) {
                return false;
            }
        } else if (!numeroProduto.equals(other.numeroProduto)) {
            return false;
        }
        if (numeroProdutoOrigemDestino == null) {
            if (other.numeroProdutoOrigemDestino != null) {
                return false;
            }
        } else if (!numeroProdutoOrigemDestino
                .equals(other.numeroProdutoOrigemDestino)) {
            return false;
        }
        if (numeroRedeIso == null) {
            if (other.numeroRedeIso != null) {
                return false;
            }
        } else if (!numeroRedeIso.equals(other.numeroRedeIso)) {
            return false;
        }
        if (numeroRenavamVeiculo == null) {
            if (other.numeroRenavamVeiculo != null) {
                return false;
            }
        } else if (!numeroRenavamVeiculo.equals(other.numeroRenavamVeiculo)) {
            return false;
        }
        if (numeroSacado == null) {
            if (other.numeroSacado != null) {
                return false;
            }
        } else if (!numeroSacado.equals(other.numeroSacado)) {
            return false;
        }
        if (numeroSequencialImpresso == null) {
            if (other.numeroSequencialImpresso != null) {
                return false;
            }
        } else if (!numeroSequencialImpresso
                .equals(other.numeroSequencialImpresso)) {
            return false;
        }
        if (numeroSequencialUnicoOrigem == null) {
            if (other.numeroSequencialUnicoOrigem != null) {
                return false;
            }
        } else if (!numeroSequencialUnicoOrigem
                .equals(other.numeroSequencialUnicoOrigem)) {
            return false;
        }
        if (numeroSequencialUnicoTransacao == null) {
            if (other.numeroSequencialUnicoTransacao != null) {
                return false;
            }
        } else if (!numeroSequencialUnicoTransacao
                .equals(other.numeroSequencialUnicoTransacao)) {
            return false;
        }
        if (numeroSublote == null) {
            if (other.numeroSublote != null) {
                return false;
            }
        } else if (!numeroSublote.equals(other.numeroSublote)) {
            return false;
        }
        if (numeroTipoCartao == null) {
            if (other.numeroTipoCartao != null) {
                return false;
            }
        } else if (!numeroTipoCartao.equals(other.numeroTipoCartao)) {
            return false;
        }
        if (numeroTipoTransacao == null) {
            if (other.numeroTipoTransacao != null) {
                return false;
            }
        } else if (!numeroTipoTransacao.equals(other.numeroTipoTransacao)) {
            return false;
        }
        if (numeroTitular == null) {
            if (other.numeroTitular != null) {
                return false;
            }
        } else if (!numeroTitular.equals(other.numeroTitular)) {
            return false;
        }
        if (numeroTitularOrigemDestino == null) {
            if (other.numeroTitularOrigemDestino != null) {
                return false;
            }
        } else if (!numeroTitularOrigemDestino
                .equals(other.numeroTitularOrigemDestino)) {
            return false;
        }
        if (numeroVersao == null) {
            if (other.numeroVersao != null) {
                return false;
            }
        } else if (!numeroVersao.equals(other.numeroVersao)) {
            return false;
        }
        if (numeroVersaoOrigemDestino == null) {
            if (other.numeroVersaoOrigemDestino != null) {
                return false;
            }
        } else if (!numeroVersaoOrigemDestino
                .equals(other.numeroVersaoOrigemDestino)) {
            return false;
        }
        if (numeroVersaoTerminal == null) {
            if (other.numeroVersaoTerminal != null) {
                return false;
            }
        } else if (!numeroVersaoTerminal.equals(other.numeroVersaoTerminal)) {
            return false;
        }
        if (percentualValorAmortizado == null) {
            if (other.percentualValorAmortizado != null) {
                return false;
            }
        } else if (!percentualValorAmortizado
                .equals(other.percentualValorAmortizado)) {
            return false;
        }
        if (quantidadeLimiteDisponivel == null) {
            if (other.quantidadeLimiteDisponivel != null) {
                return false;
            }
        } else if (!quantidadeLimiteDisponivel
                .equals(other.quantidadeLimiteDisponivel)) {
            return false;
        }
        if (quantidadeParcelaRestante == null) {
            if (other.quantidadeParcelaRestante != null) {
                return false;
            }
        } else if (!quantidadeParcelaRestante
                .equals(other.quantidadeParcelaRestante)) {
            return false;
        }
        if (quantidadeParcelaTotal == null) {
            if (other.quantidadeParcelaTotal != null) {
                return false;
            }
        } else if (!quantidadeParcelaTotal.equals(other.quantidadeParcelaTotal)) {
            return false;
        }
        if (quantidadeSolicitadaCheque == null) {
            if (other.quantidadeSolicitadaCheque != null) {
                return false;
            }
        } else if (!quantidadeSolicitadaCheque
                .equals(other.quantidadeSolicitadaCheque)) {
            return false;
        }
        if (quantidadeTempoCompensacao == null) {
            if (other.quantidadeTempoCompensacao != null) {
                return false;
            }
        } else if (!quantidadeTempoCompensacao
                .equals(other.quantidadeTempoCompensacao)) {
            return false;
        }
        if (siglaPaisCambio == null) {
            if (other.siglaPaisCambio != null) {
                return false;
            }
        } else if (!siglaPaisCambio.equals(other.siglaPaisCambio)) {
            return false;
        }
        if (siglaPaisOrigemDestino == null) {
            if (other.siglaPaisOrigemDestino != null) {
                return false;
            }
        } else if (!siglaPaisOrigemDestino.equals(other.siglaPaisOrigemDestino)) {
            return false;
        }
        if (siglaSistema == null) {
            if (other.siglaSistema != null) {
                return false;
            }
        } else if (!siglaSistema.equals(other.siglaSistema)) {
            return false;
        }
        if (siglaUFAgencia == null) {
            if (other.siglaUFAgencia != null) {
                return false;
            }
        } else if (!siglaUFAgencia.equals(other.siglaUFAgencia)) {
            return false;
        }
        if (siglaUFAgenciaOrigemDestino == null) {
            if (other.siglaUFAgenciaOrigemDestino != null) {
                return false;
            }
        } else if (!siglaUFAgenciaOrigemDestino
                .equals(other.siglaUFAgenciaOrigemDestino)) {
            return false;
        }
        if (siglaUFOperador == null) {
            if (other.siglaUFOperador != null) {
                return false;
            }
        } else if (!siglaUFOperador.equals(other.siglaUFOperador)) {
            return false;
        }
        if (siglaUFTerminal == null) {
            if (other.siglaUFTerminal != null) {
                return false;
            }
        } else if (!siglaUFTerminal.equals(other.siglaUFTerminal)) {
            return false;
        }
        if (valorBloqueadoFimDia == null) {
            if (other.valorBloqueadoFimDia != null) {
                return false;
            }
        } else if (!valorBloqueadoFimDia.equals(other.valorBloqueadoFimDia)) {
            return false;
        }
        if (valorCreditoRotativo == null) {
            if (other.valorCreditoRotativo != null) {
                return false;
            }
        } else if (!valorCreditoRotativo.equals(other.valorCreditoRotativo)) {
            return false;
        }
        if (valorLiberado == null) {
            if (other.valorLiberado != null) {
                return false;
            }
        } else if (!valorLiberado.equals(other.valorLiberado)) {
            return false;
        }
        if (valorLimiteDisponivel == null) {
            if (other.valorLimiteDisponivel != null) {
                return false;
            }
        } else if (!valorLimiteDisponivel.equals(other.valorLimiteDisponivel)) {
            return false;
        }
        if (valorLimiteOperacao == null) {
            if (other.valorLimiteOperacao != null) {
                return false;
            }
        } else if (!valorLimiteOperacao.equals(other.valorLimiteOperacao)) {
            return false;
        }
        if (valorSaldoConta == null) {
            if (other.valorSaldoConta != null) {
                return false;
            }
        } else if (!valorSaldoConta.equals(other.valorSaldoConta)) {
            return false;
        }
        if (valorSaldoDisponivel == null) {
            if (other.valorSaldoDisponivel != null) {
                return false;
            }
        } else if (!valorSaldoDisponivel.equals(other.valorSaldoDisponivel)) {
            return false;
        }
        if (valorTotalFimDia == null) {
            if (other.valorTotalFimDia != null) {
                return false;
            }
        } else if (!valorTotalFimDia.equals(other.valorTotalFimDia)) {
            return false;
        }
        if (valorTransacao == null) {
            if (other.valorTransacao != null) {
                return false;
            }
        } else if (!valorTransacao.equals(other.valorTransacao)) {
            return false;
        }
        if (valorTransacaoOriginal == null) {
            if (other.valorTransacaoOriginal != null) {
                return false;
            }
        } else if (!valorTransacaoOriginal.equals(other.valorTransacaoOriginal)) {
            return false;
        }
        if (valorTroco == null) {
            if (other.valorTroco != null) {
                return false;
            }
        } else if (!valorTroco.equals(other.valorTroco)) {
            return false;
        }
        return true;
    }

}