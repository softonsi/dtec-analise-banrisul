package br.com.softon.dtec.entity;

import java.util.Collection;
import java.util.Set;

public class ConjuntoRendasClientesCliente {

    private final Set<RendaCliente> listaRendasClientes;
    
    @SuppressWarnings("unchecked")
	public ConjuntoRendasClientesCliente(final Set<RendaCliente> listaRendasClientes) {
        if (listaRendasClientes == null) {
            this.listaRendasClientes = java.util.Collections.EMPTY_SET;
        } else {
            this.listaRendasClientes = listaRendasClientes;
        }
        
    }

    public Collection<RendaCliente> getListaRendasClientes() {
        return listaRendasClientes;
    }
    
}
