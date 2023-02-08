package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoGOVs {

    private final Collection<GOV> listaGOVs;

    public ConjuntoGOVs(final Collection<GOV> listaGOVs) {
        if (listaGOVs == null) {
            this.listaGOVs = emptyList();
        } else {
            this.listaGOVs = listaGOVs;
        }
    }

    public Collection<GOV> getListaGOVs() {
        return listaGOVs;
    }

}
