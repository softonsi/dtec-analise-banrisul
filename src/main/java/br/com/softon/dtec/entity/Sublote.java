package br.com.softon.dtec.entity;

public class Sublote {

    private final Long coLote;
    private final Short coSublote;
    private final Byte coTipoProcessamento;

    public Sublote(Long coLote, Short s, Byte coTipoProcessamento) {
        this.coLote = coLote;
        this.coSublote = s;
        this.coTipoProcessamento = coTipoProcessamento;
    }

    public Long getCoLote() {
        return coLote;
    }

    public Short getCoSublote() {
        return coSublote;
    }

    public Byte getTipoProcessamento() {
        return coTipoProcessamento;
    }
}