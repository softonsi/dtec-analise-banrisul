package br.com.softon.dtec.entity;

import java.math.BigInteger;
import java.util.Date;

public class PerfilContaDestino implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	BigInteger codigoIdentificadorConta;
	private	BigInteger codigoIdentificadorContaOrigemDestino;
	private	Date dataPerfil;
	
	public PerfilContaDestino() {}
	
	public PerfilContaDestino(final short codigoIdentificadorPerfil,
			final BigInteger codigoIdentificadorConta,
			final BigInteger codigoIdentificadorContaOrigemDestino,
			final Date dataPerfil) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.codigoIdentificadorConta = codigoIdentificadorConta;
		this.codigoIdentificadorContaOrigemDestino = codigoIdentificadorContaOrigemDestino;
		this.dataPerfil = dataPerfil;
	}

	public Short getCodigoIdentificadorPerfil() {
		return codigoIdentificadorPerfil;
	}

	public BigInteger getCodigoIdentificadorConta() {
		return codigoIdentificadorConta;
	}

	public BigInteger getCodigoIdentificadorContaOrigemDestino() {
		return codigoIdentificadorContaOrigemDestino;
	}

	public Date getDataPerfil() {
		return dataPerfil;
	}
	
}