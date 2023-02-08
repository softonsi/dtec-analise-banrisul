package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoCadastroClientes {

    private final Collection<CadastroCliente> listaCadastroClientes;

    public ConjuntoCadastroClientes(final Collection<CadastroCliente> listaCadastroClientes) {
        if (listaCadastroClientes == null) {
            this.listaCadastroClientes = emptyList();
        } else {
            this.listaCadastroClientes = listaCadastroClientes;
        }
    }

    public Collection<CadastroCliente> getListaCadastroClientes() {
        return listaCadastroClientes;
    }

}
