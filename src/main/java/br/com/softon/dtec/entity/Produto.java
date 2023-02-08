package br.com.softon.dtec.entity;

public class Produto {

	/**
	* CD_PRODUTO - Código identificador do produto, ex: veículos, crédito pessoal, consignado, cartão, cdc, entre outros. 
	*/
	private final String codProduto;

	/**
	* FL_BX_LIQUIDEZ - Indica se o produto tem baixa liquidez, sendo 1-sim e 0-não
	*/
	private final Byte flagBaixaLiquidez;

	/**
	* FL_BX_RENTABILIDADE - Indica se o produto tem baixa rentabilidade, sendo 1-sim e 0-não
	*/
	private final Byte flagBaixaRentabilidade;

	/**
	* NM_PRODUTO - Nome do produto
	*/
	private final String nomeProduto;

	/**
	* VL_MED_INVEST - Valor médio de investimento do produto.
	*/
	private final Double valorMedioInvestimento;

	/**
	* VL_MERCADO - Valor de mercado do produto.
	*/
	private final Double valorMercado;

	public Produto(String codProduto, Byte flagBaixaLiquidez,
			Byte flagBaixaRentabilidade, String nomeProduto,
			Double valorMedioInvestimento, Double valorMercado) {
		super();
		this.codProduto = codProduto;
		this.flagBaixaLiquidez = flagBaixaLiquidez;
		this.flagBaixaRentabilidade = flagBaixaRentabilidade;
		this.nomeProduto = nomeProduto;
		this.valorMedioInvestimento = valorMedioInvestimento;
		this.valorMercado = valorMercado;
	}

	/**
	 * @return the codProduto
	 */
	public String getCodProduto() {
		return codProduto;
	}

	/**
	 * @return the flagBaixaLiquidez
	 */
	public Byte getFlagBaixaLiquidez() {
		return flagBaixaLiquidez;
	}

	/**
	 * @return the flagBaixaRentabilidade
	 */
	public Byte getFlagBaixaRentabilidade() {
		return flagBaixaRentabilidade;
	}

	/**
	 * @return the nomeProduto
	 */
	public String getNomeProduto() {
		return nomeProduto;
	}

	/**
	 * @return the valorMedioInvestimento
	 */
	public Double getValorMedioInvestimento() {
		return valorMedioInvestimento;
	}

	/**
	 * @return the valorMercado
	 */
	public Double getValorMercado() {
		return valorMercado;
	}
	
}
