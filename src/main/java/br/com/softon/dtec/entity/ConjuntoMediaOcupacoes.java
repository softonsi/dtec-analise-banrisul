package br.com.softon.dtec.entity;

import java.util.Collection;

import static java.util.Collections.*;

public class ConjuntoMediaOcupacoes {

    private final Collection<MediaOcupacao> listaMediaOcupacoes;

    public ConjuntoMediaOcupacoes(final Collection<MediaOcupacao> listaMediaOcupacoes) {
        if (listaMediaOcupacoes == null) {
            this.listaMediaOcupacoes = emptyList();
        } else {
            this.listaMediaOcupacoes = listaMediaOcupacoes;
        }
    }

    public Collection<MediaOcupacao> getListaMediaOcupacoes() {
        return listaMediaOcupacoes;
    }

}
