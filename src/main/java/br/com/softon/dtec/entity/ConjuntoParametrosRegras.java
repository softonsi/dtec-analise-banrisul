package br.com.softon.dtec.entity;

import java.util.Collection;

import static java.util.Collections.*;

public class ConjuntoParametrosRegras {

    private final Collection<ParametrosRegras> listaParametrosRegras;

    public ConjuntoParametrosRegras(final Collection<ParametrosRegras> listaParametrosRegras) {
        if (listaParametrosRegras == null) {
            this.listaParametrosRegras = emptyList();
        } else {
            this.listaParametrosRegras = listaParametrosRegras;
        }
    }

    public Collection<ParametrosRegras> getListaParametrosRegras() {
        return listaParametrosRegras;
    }
}
