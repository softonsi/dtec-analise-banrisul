package br.com.softon.dtec.entity;

import java.util.Set;

public class ConjuntoPerfisMesCalendarioCliente {

    private final Set<PerfilMesCalendario> listaPerfisMesCalendario;

    @SuppressWarnings("unchecked")
	public ConjuntoPerfisMesCalendarioCliente(final Set<PerfilMesCalendario> listaPerfisMesCalendario) {
        if (listaPerfisMesCalendario == null) {
            this.listaPerfisMesCalendario = java.util.Collections.EMPTY_SET;
        } else {
            this.listaPerfisMesCalendario = listaPerfisMesCalendario;
        }
        
    }

    public Set<PerfilMesCalendario> getListaPerfisMesCalendario() {
        return listaPerfisMesCalendario;
    }

}
