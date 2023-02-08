package br.com.softon.dtec.entity;

public class GOV {

	/**
	* NU_CPF_CNPJ - Número do documento de identificação.
	*/
	private final String numeroCPFCNPJ;
	

	public GOV(String numeroCPFCNPJ) {
		super();
		this.numeroCPFCNPJ = numeroCPFCNPJ;
	}


	/**
	 * @return the codDocIdentificacaoCliente
	 */
	public String getNumeroCPFCNPJ() {
		return numeroCPFCNPJ;
	}
	
}
