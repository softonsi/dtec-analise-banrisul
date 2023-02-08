package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoContratos {

    private final Collection<Contrato> listaContratos;

    public ConjuntoContratos(final Collection<Contrato> listaContratos) {
        if (listaContratos == null) {
            this.listaContratos = emptyList();
        } else {
            this.listaContratos = listaContratos;
        }
    }

    public Collection<Contrato> getListaContratos() {
        return listaContratos;
    }

}
