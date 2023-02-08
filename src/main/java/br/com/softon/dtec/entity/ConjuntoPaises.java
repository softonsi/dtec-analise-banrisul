package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoPaises {

    private final Collection<Pais> listaPaises;

    public ConjuntoPaises(final Collection<Pais> listaPaises) {
        if (listaPaises == null) {
            this.listaPaises = emptyList();
        } else {
            this.listaPaises = listaPaises;
        }
    }

    public Collection<Pais> getListaPaises() {
        return listaPaises;
    }

}
