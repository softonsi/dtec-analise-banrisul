package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoCadeiasSocietarias {

    private final Collection<CadeiaSocietaria> listaCadeiasSocietarias;

    public ConjuntoCadeiasSocietarias(final Collection<CadeiaSocietaria> listaCadeiasSocietarias) {
        if (listaCadeiasSocietarias == null) {
            this.listaCadeiasSocietarias = emptyList();
        } else {
            this.listaCadeiasSocietarias = listaCadeiasSocietarias;
        }
    }

    public Collection<CadeiaSocietaria> getListaCadeiasSocietarias() {
        return listaCadeiasSocietarias;
    }

}
