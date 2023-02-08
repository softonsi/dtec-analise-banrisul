package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoPPEs {

    private final Collection<PPE> listaPPEs;

    public ConjuntoPPEs(final Collection<PPE> listaPPEs) {
        if (listaPPEs == null) {
            this.listaPPEs = emptyList();
        } else {
            this.listaPPEs = listaPPEs;
        }
    }

    public Collection<PPE> getListaPPEs() {
        return listaPPEs;
    }

}
