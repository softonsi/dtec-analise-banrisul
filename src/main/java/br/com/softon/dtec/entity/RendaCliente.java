package br.com.softon.dtec.entity;

import java.sql.Timestamp;

public class RendaCliente {

	/**
	* CD_CEP_TRAB - Código de Endereçamento Postal do local de trabalho do cliente.
	*/
	private Integer codCepTrabalho;

	/**
	* CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF
	* Se cd_tp_identf_clie = 2, isto é CPF, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CPF de 9 dígitos, 
	* completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda.
	* Se cd_tp_identf_clie = 3, isto é CNPJ, armazenar no campo CD_DOC_IDENTF_CLIE o radical do CNPJ de 8 dígitos, 
	* completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, 
	* completando também com zero a esquerda.
	*/
	private String codDocIdentificacaoCliente;

	/**
	* CD_DOC_IDENTF_TRAB - Número do documento de identificação da empresa empregadora, de acordo com o campo CD_TP_DOC_IDENTF_TRAB./nSe cd_tp_doc_identf_trab= 2, isto é CPF, armazenar no campo CD_TP_DOC_IDENTF_TRAB o radical do CPF de 9 dígitos, completando com zeros a esquerda mais o dígito verificador com 2 dígitos, também completando com zero a esquerda./nSe cd_tp_doc_identf_trab= 3, isto é CNPJ, armazenar no campo CD_TP_DOC_IDENTF_TRAB o radical do CNPJ de 8 dígitos, completando com zeros a esquerda mais a filial com 4 dígitos, completando com zero a esquerda e mais o dígito verificador com 2 dígitos, completando também com zero a esquerda
	*/
	private String codDocIdentificacaoTrabalho;

	/**
	* CD_FONE_COM - Número do telefone comercial do cliente
	*/
	private String codFoneComercial;

	/**
	* CD_LOTE - Número do lote de processamento.
	*/
	private Long codLote;

	/**
	* CD_OCUP - Código de ocupação do cliente, isto é da profissão. Integridade com a tabela TB_OCUP.
	*/
	private Long codOcupacao;

	/**
	* CD_PAIS_TRAB - Código do país onde o cliente trabalha. Este campo tem integridade com a tabela TB_PAIS.
	*/
	private Short codPaisTrabalho;

	/**
	* CD_SEQ_OCUP - Código seqüencial que identifica uma ocupação do cliente.
	*/
	private Integer codSeqOcupacao;

	/**
	* CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private Byte codTipoIdentificacaoCliente;

	/**
	* CD_TP_IDENTF_TRAB - Indica que tipo de documento o campo NU_DOC_IDENTF_TRAB contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID./n
	*/
	private Byte codTipoIdentificacaoTrabalho;

	/**
	* CD_TP_PESSOA - Código do tipo de pessoa, se jurídica ou física, sendo: F = física J = jurídica
	*/
	private Character codTipoPessoa;

	/**
	* CD_TP_PESSOA_TRAB - Código do tipo de pessoa, se jurídica ou física, sendo: F = física J = jurídica
	*/
	private Character codTipoPessoaTrabalho;

	/**
	* DT_FIM_ATIVID - Data término de trabalho do cliente na empresa.
	*/
	private Timestamp dataFimAtividade;

	/**
	* DT_INIC_ATIVID - Data Início de trabalho do cliente na empresa.
	*/
	private Timestamp dataInicioAtividade;

	/**
	* DT_ULTM_ATULZ_CADSTRL - Data da última atualização cadastral.
	*/
	private Timestamp dataUltimaAtualizacaoCadastral;

	/**
	* FL_RENDA_COMPROV - Indica se a renda do cliente foi comprovada. Sendo: 0 - Não Comprovada; 1- Comprovada.
	*/
	private Byte flagRendaComprovada;

	/**
	* NM_CID_TRAB - Nome da Cidade onde o cliente trabalha.
	*/
	private String nomeCidadeTrabalho;

	/**
	* NM_EMP_TRAB - Nome da empresa onde o cliente trabalha.
	*/
	private String nomeEmpresaTrabalho;

	/**
	* NM_ENDER_TRAB - Endereço onde o cliente trabalha.
	*/
	private String nomeEnderecoTrabalho;

	/**
	* NM_FANT - Nome fantasia da empresa, se cliente for pessoa jurídica.
	*/
	private String nomeFantasia;

	/**
	* SG_UF_TRAB - Sigla da UF onde o cliente trabalha.
	*/
	private String siglaUFTrabalho;

	/**
	* VL_RENDA - Valor de renda do cliente do banco.
	*/
	private Double valorRenda;
	

	public RendaCliente(Integer codCepTrabalho,
			String codDocIdentificacaoCliente,
			String codDocIdentificacaoTrabalho, String codFoneComercial,
			Long codLote, Long codOcupacao, Short codPaisTrabalho,
			Integer codSeqOcupacao, Byte codTipoIdentificacaoCliente,
			Byte codTipoIdentificacaoTrabalho, Character codTipoPessoa,
			Character codTipoPessoaTrabalho, Timestamp dataFimAtividade,
			Timestamp dataInicioAtividade, Timestamp dataUltimaAtualizacaoCadastral,
			Byte flagRendaComprovada, String nomeCidadeTrabalho,
			String nomeEmpresaTrabalho, String nomeEnderecoTrabalho,
			String nomeFantasia, String siglaUFTrabalho, Double valorRenda) {
		super();
		this.codCepTrabalho = codCepTrabalho;
		this.codDocIdentificacaoCliente = codDocIdentificacaoCliente;
		this.codDocIdentificacaoTrabalho = codDocIdentificacaoTrabalho;
		this.codFoneComercial = codFoneComercial;
		this.codLote = codLote;
		this.codOcupacao = codOcupacao;
		this.codPaisTrabalho = codPaisTrabalho;
		this.codSeqOcupacao = codSeqOcupacao;
		this.codTipoIdentificacaoCliente = codTipoIdentificacaoCliente;
		this.codTipoIdentificacaoTrabalho = codTipoIdentificacaoTrabalho;
		this.codTipoPessoa = codTipoPessoa;
		this.codTipoPessoaTrabalho = codTipoPessoaTrabalho;
		this.dataFimAtividade = dataFimAtividade;
		this.dataInicioAtividade = dataInicioAtividade;
		this.dataUltimaAtualizacaoCadastral = dataUltimaAtualizacaoCadastral;
		this.flagRendaComprovada = flagRendaComprovada;
		this.nomeCidadeTrabalho = nomeCidadeTrabalho;
		this.nomeEmpresaTrabalho = nomeEmpresaTrabalho;
		this.nomeEnderecoTrabalho = nomeEnderecoTrabalho;
		this.nomeFantasia = nomeFantasia;
		this.siglaUFTrabalho = siglaUFTrabalho;
		this.valorRenda = valorRenda;
	}

	/**
	 * @return the codCepTrabalho
	 */
	public Integer getCodCepTrabalho() {
		return codCepTrabalho;
	}

	/**
	 * @return the codDocIdentificacaoCliente
	 */
	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}

	/**
	 * @return the codDocIdentificacaoTrabalho
	 */
	public String getCodDocIdentificacaoTrabalho() {
		return codDocIdentificacaoTrabalho;
	}

	/**
	 * @return the codFoneComercial
	 */
	public String getCodFoneComercial() {
		return codFoneComercial;
	}

	/**
	 * @return the codLote
	 */
	public Long getCodLote() {
		return codLote;
	}

	/**
	 * @return the codOcupacao
	 */
	public Long getCodOcupacao() {
		return codOcupacao;
	}

	/**
	 * @return the codPaisTrabalho
	 */
	public Short getCodPaisTrabalho() {
		return codPaisTrabalho;
	}

	/**
	 * @return the codSeqOcupacao
	 */
	public Integer getCodSeqOcupacao() {
		return codSeqOcupacao;
	}

	/**
	 * @return the codTipoIdentificacaoCliente
	 */
	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}

	/**
	 * @return the codTipoIdentificacaoTrabalho
	 */
	public Byte getCodTipoIdentificacaoTrabalho() {
		return codTipoIdentificacaoTrabalho;
	}

	/**
	 * @return the codTipoPessoa
	 */
	public Character getCodTipoPessoa() {
		return codTipoPessoa;
	}

	/**
	 * @return the codTipoPessoaTrabalho
	 */
	public Character getCodTipoPessoaTrabalho() {
		return codTipoPessoaTrabalho;
	}

	/**
	 * @return the dataFimAtividade
	 */
	public Timestamp getDataFimAtividade() {
		return dataFimAtividade;
	}

	/**
	 * @return the dataInicioAtividade
	 */
	public Timestamp getDataInicioAtividade() {
		return dataInicioAtividade;
	}

	/**
	 * @return the dataUltimaAtualizacaoCadastral
	 */
	public Timestamp getDataUltimaAtualizacaoCadastral() {
		return dataUltimaAtualizacaoCadastral;
	}

	/**
	 * @return the flagRendaComprovada
	 */
	public Byte getFlagRendaComprovada() {
		return flagRendaComprovada;
	}

	/**
	 * @return the nomeCidadeTrabalho
	 */
	public String getNomeCidadeTrabalho() {
		return nomeCidadeTrabalho;
	}

	/**
	 * @return the nomeEmpresaTrabalho
	 */
	public String getNomeEmpresaTrabalho() {
		return nomeEmpresaTrabalho;
	}

	/**
	 * @return the nomeEnderecoTrabalho
	 */
	public String getNomeEnderecoTrabalho() {
		return nomeEnderecoTrabalho;
	}

	/**
	 * @return the nomeFantasia
	 */
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	/**
	 * @return the siglaUFTrabalho
	 */
	public String getSiglaUFTrabalho() {
		return siglaUFTrabalho;
	}

	/**
	 * @return the valorRenda
	 */
	public Double getValorRenda() {
		return valorRenda;
	}

}
