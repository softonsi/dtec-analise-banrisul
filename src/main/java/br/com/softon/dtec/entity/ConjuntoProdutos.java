package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoProdutos {

    private final Collection<Produto> listaProdutos;

    public ConjuntoProdutos(final Collection<Produto> listaProdutos) {
        if (listaProdutos == null) {
            this.listaProdutos = emptyList();
        } else {
            this.listaProdutos = listaProdutos;
        }
    }

    public Collection<Produto> getListaProdutos() {
        return listaProdutos;
    }

}
