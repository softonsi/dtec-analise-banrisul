package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoAgencias {

    private final Collection<Agencia> listaAgencias;

    public ConjuntoAgencias(final Collection<Agencia> listaAgencias) {
        if (listaAgencias == null) {
            this.listaAgencias = emptyList();
        } else {
            this.listaAgencias = listaAgencias;
        }
    }

    public Collection<Agencia> getListaAgencias() {
        return listaAgencias;
    }

}
