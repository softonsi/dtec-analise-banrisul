package br.com.softon.dtec.entity;

import java.util.Date;

public class PerfilCedente implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	Date dataPerfil;
	private	Long numeroCedente;
	private	Double valorDesvioDiario;
	private	Double valorDesvioTransacao;
	private	Double valorMedioDiario;
	private	Double valorMedioTransacao;
	
	public PerfilCedente() {}
	
	public PerfilCedente(final short codigoIdentificadorPerfil,
			final Date dataPerfil, final Long numeroCedente,
			final Double valorDesvioDiario, final Double valorDesvioTransacao,
			final Double valorMedioDiario, final Double valorMedioTransacao) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.dataPerfil = dataPerfil;
		this.numeroCedente = numeroCedente;
		this.valorDesvioDiario = valorDesvioDiario;
		this.valorDesvioTransacao = valorDesvioTransacao;
		this.valorMedioDiario = valorMedioDiario;
		this.valorMedioTransacao = valorMedioTransacao;
	}

	public Short getCodigoIdentificadorPerfil() {
		return codigoIdentificadorPerfil;
	}

	public Date getDataPerfil() {
		return dataPerfil;
	}

	public Long getNumeroCedente() {
		return numeroCedente;
	}

	public Double getValorDesvioDiario() {
		return valorDesvioDiario;
	}

	public Double getValorDesvioTransacao() {
		return valorDesvioTransacao;
	}

	public Double getValorMedioDiario() {
		return valorMedioDiario;
	}

	public Double getValorMedioTransacao() {
		return valorMedioTransacao;
	}
	
}