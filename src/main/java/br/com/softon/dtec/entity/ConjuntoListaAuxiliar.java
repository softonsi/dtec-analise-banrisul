package br.com.softon.dtec.entity;

import java.util.Collection;

import static java.util.Collections.*;

public class ConjuntoListaAuxiliar {

    private final Collection<ListaAuxiliar> listasAuxiliares;

    public ConjuntoListaAuxiliar(final Collection<ListaAuxiliar> listasAuxiliares) {
        if (listasAuxiliares == null) {
            this.listasAuxiliares = emptyList();
        } else {
            this.listasAuxiliares = listasAuxiliares;
        }
    }

    public Collection<ListaAuxiliar> getListasAuxiliares() {
        return listasAuxiliares;
    }
}
