package br.com.softon.dtec.entity;

import java.util.Date;

public class PerfilEstabelecimento implements Perfil {

    private short codigoIdentificadorPerfil; //1-7
    
	private	Date dataPerfil;
	private	Long numeroEstabelecimento;
	private	Double valorDesvioDiario;
	private	Double valorDesvioTransacao;
	private	Double valorMedioDiario;
	private	Double valorMedioTransacao;
	
	public PerfilEstabelecimento() {}
	
	public PerfilEstabelecimento(final short codigoIdentificadorPerfil,
			final Date dataPerfil,
			final Long numeroEstabelecimento,
			final Double valorDesvioDiario, final Double valorDesvioTransacao,
			final Double valorMedioDiario, final Double valorMedioTransacao) {
		super();
		this.codigoIdentificadorPerfil = codigoIdentificadorPerfil;
		this.dataPerfil = dataPerfil;
		this.numeroEstabelecimento = numeroEstabelecimento;
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

	public Long getNumeroEstabelecimento() {
		return numeroEstabelecimento;
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