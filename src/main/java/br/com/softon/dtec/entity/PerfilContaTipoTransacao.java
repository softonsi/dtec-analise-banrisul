package br.com.softon.dtec.entity;

import java.math.BigInteger;
import java.util.Date;

public class PerfilContaTipoTransacao implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	BigInteger codigoIdentificadorConta;
	private	Date dataPerfil;
	private	String codigoServico;
	private	Double valorDesvioTransacao;
	private	Double valorMedioTransacao;
	
	public PerfilContaTipoTransacao() {}
	
	public PerfilContaTipoTransacao(final short codigoIdentificadorPerfil,
			final BigInteger codigoIdentificadorConta,
			final Date dataPerfil, final String codigoServico,
			final Double valorDesvioTransacao, final Double valorMedioTransacao) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.codigoIdentificadorConta = codigoIdentificadorConta;
		this.dataPerfil = dataPerfil;
		this.codigoServico = codigoServico;
		this.valorDesvioTransacao = valorDesvioTransacao;
		this.valorMedioTransacao = valorMedioTransacao;
	}

	public Short getCodigoIdentificadorPerfil() {
		return codigoIdentificadorPerfil;
	}

	public BigInteger getCodigoIdentificadorConta() {
		return codigoIdentificadorConta;
	}

	public Date getDataPerfil() {
		return dataPerfil;
	}

	public String getCodigoServico() {
		return codigoServico;
	}

	public Double getValorDesvioTransacao() {
		return valorDesvioTransacao;
	}

	public Double getValorMedioTransacao() {
		return valorMedioTransacao;
	}
	
}