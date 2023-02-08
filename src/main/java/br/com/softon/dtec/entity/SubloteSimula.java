package br.com.softon.dtec.entity;

public class SubloteSimula {

    private final Long coLote;
    private final Short coSublote;

    public SubloteSimula(Long coLote, Short s) {
        this.coLote = coLote;
        this.coSublote = s;
    }

    public Long getCoLote() {
        return coLote;
    }

    public Short getCoSublote() {
        return coSublote;
    }
}