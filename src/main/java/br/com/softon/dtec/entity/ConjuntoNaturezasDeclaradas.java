package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoNaturezasDeclaradas {

    private final Collection<NaturezaDeclarada> listaNaturezasDeclaradas;

    public ConjuntoNaturezasDeclaradas(final Collection<NaturezaDeclarada> listaNaturezasDeclaradas) {
        if (listaNaturezasDeclaradas == null) {
            this.listaNaturezasDeclaradas = emptyList();
        } else {
            this.listaNaturezasDeclaradas = listaNaturezasDeclaradas;
        }
    }

    public Collection<NaturezaDeclarada> getListaNaturezasDeclaradas() {
        return listaNaturezasDeclaradas;
    }

}
