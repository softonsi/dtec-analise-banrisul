package br.com.softon.dtec.entity;

import java.math.BigInteger;
import java.util.Date;

public class PerfilContaAgenciaTerminal implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	BigInteger codigoIdentificadorConta;
	private	String codigoTerminal;
	private	Date dataPerfil;
	private	Long numeroAgenciaVinculacao;
	
	public PerfilContaAgenciaTerminal() {}
	
	public PerfilContaAgenciaTerminal(final short codigoIdentificadorPerfil,
			final BigInteger codigoIdentificadorConta,
			final String codigoTerminal, final Date dataPerfil,
			final Long numeroAgenciaVinculacao) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.codigoIdentificadorConta = codigoIdentificadorConta;
		this.codigoTerminal = codigoTerminal;
		this.dataPerfil = dataPerfil;
		this.numeroAgenciaVinculacao = numeroAgenciaVinculacao;
	}

	public Short getCodigoIdentificadorPerfil() {
		return codigoIdentificadorPerfil;
	}

	public BigInteger getCodigoIdentificadorConta() {
		return codigoIdentificadorConta;
	}

	public String getCodigoTerminal() {
		return codigoTerminal;
	}

	public Date getDataPerfil() {
		return dataPerfil;
	}

	public Long getNumeroAgenciaVinculacao() {
		return numeroAgenciaVinculacao;
	}
	
}