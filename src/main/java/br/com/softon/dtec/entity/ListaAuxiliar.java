package br.com.softon.dtec.entity;


public class ListaAuxiliar {

    private short numeroListaAuxiliar;
    private String nomeListaAuxiliar;
    private Byte numeroTipoListaAuxiliar;
    private Byte numeroClasseListaAuxiliar;
    private String descricaoConteudo;
    private String nomeMotivo;
    private String codTransacao;

    public ListaAuxiliar(final String descricaoConteudo, final Byte numeroTipoListaAuxiliar,
            final Byte numeroClasseListaAuxiliar, final String nomeListaAuxiliar, final short numeroListaAuxiliar,
            final String nomeMotivo) {
        this.numeroListaAuxiliar = numeroListaAuxiliar;
        this.descricaoConteudo = descricaoConteudo;
        this.numeroTipoListaAuxiliar = numeroTipoListaAuxiliar;
        this.numeroClasseListaAuxiliar = numeroClasseListaAuxiliar;
        this.nomeListaAuxiliar = nomeListaAuxiliar;
        this.nomeMotivo = nomeMotivo;
    }

    public ListaAuxiliar(final String descricaoConteudo, final Byte numeroTipoListaAuxiliar,
    		final Byte numeroClasseListaAuxiliar, final String nomeListaAuxiliar, final short numeroListaAuxiliar,
    		final String nomeMotivo, String codTransacao) {
    	this.numeroListaAuxiliar = numeroListaAuxiliar;
    	this.descricaoConteudo = descricaoConteudo;
    	this.numeroTipoListaAuxiliar = numeroTipoListaAuxiliar;
    	this.numeroClasseListaAuxiliar = numeroClasseListaAuxiliar;
    	this.nomeListaAuxiliar = nomeListaAuxiliar;
    	this.nomeMotivo = nomeMotivo;
		this.codTransacao = codTransacao;
    }
    
    public short getNumeroListaAuxiliar() {
        return numeroListaAuxiliar;
    }

    public String getNomeListaAuxiliar() {
        return nomeListaAuxiliar;
    }

    public Byte getNumeroTipoListaAuxiliar() {
        return numeroTipoListaAuxiliar;
    }

    public Byte getNumeroClasseListaAuxiliar() {
        return numeroClasseListaAuxiliar;
    }

    public String getDescricaoConteudo() {
        return descricaoConteudo;
    }

    public String getNomeMotivo() {
        return nomeMotivo;
    }

	public String getCodTransacao() {
		return codTransacao;
	}

	public void setCodTransacao(String codTransacao) {
		this.codTransacao = codTransacao;
	}
}