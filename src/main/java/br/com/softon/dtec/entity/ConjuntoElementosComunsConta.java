package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoElementosComunsConta {

    private final Collection<ElementosComunsConta> listaElementosComunsConta;

    public ConjuntoElementosComunsConta(final Collection<ElementosComunsConta> listaElementosComunsConta) {
        if (listaElementosComunsConta == null) {
            this.listaElementosComunsConta = emptyList();
        } else {
            this.listaElementosComunsConta = listaElementosComunsConta;
        }
    }

    public Collection<ElementosComunsConta> getListaElementosComunsConta() {
        return listaElementosComunsConta;
    }

}
