package br.com.softon.dtec.entity;

import java.math.BigInteger;
import java.util.Date;

public class PerfilContaCedente implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	BigInteger codigoIdentificadorConta;
	private	Date dataPerfil;
	private	Long numeroCedente;
	
	public PerfilContaCedente() {}
	
	public PerfilContaCedente(final short codigoIdentificadorPerfil,
			final BigInteger codigoIdentificadorConta,
			final Date dataPerfil, final Long numeroCedente) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.codigoIdentificadorConta = codigoIdentificadorConta;
		this.dataPerfil = dataPerfil;
		this.numeroCedente = numeroCedente;
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

	public Long getNumeroCedente() {
		return numeroCedente;
	}
	
}