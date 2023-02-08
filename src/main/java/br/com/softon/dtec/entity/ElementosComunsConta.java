package br.com.softon.dtec.entity;

public class ElementosComunsConta {

	/**
	* CD_TRANSACAO - Sendo: - 20 posições para o campo nu_doc_identf
	*             - aaaammddhhmmss: 14 dígitos para Ano, mês, dia, hora, minutos, segundos, milisegundos
	*             - xx: 2 dígitos, para um número seqüencial, caso exista alguma transação fraudulenta.
	*/
	private final String codTransacao;

	public ElementosComunsConta(String codTransacao) {
		this.codTransacao = codTransacao;
	}

	/**
	 * @return the codTransacao
	 */
	public String getCodTransacao() {
		return codTransacao;
	}

}
