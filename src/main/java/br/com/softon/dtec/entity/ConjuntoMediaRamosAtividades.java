package br.com.softon.dtec.entity;

import java.util.Collection;

import static java.util.Collections.*;

public class ConjuntoMediaRamosAtividades {

    private final Collection<MediaRamoAtividade> listaMediaRamosAtividades;

    public ConjuntoMediaRamosAtividades(final Collection<MediaRamoAtividade> listaMediaRamosAtividades) {
        if (listaMediaRamosAtividades == null) {
            this.listaMediaRamosAtividades = emptyList();
        } else {
            this.listaMediaRamosAtividades = listaMediaRamosAtividades;
        }
    }

    public Collection<MediaRamoAtividade> getListaMediaRamosAtividades() {
        return listaMediaRamosAtividades;
    }

}
