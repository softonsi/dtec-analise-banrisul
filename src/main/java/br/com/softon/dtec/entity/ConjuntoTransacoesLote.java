package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoTransacoesLote {

	private Collection<ObjetoAnalise> listaTransacoesPorSubLote;

	public ConjuntoTransacoesLote(final Collection<ObjetoAnalise> listaTransacoesPorSubLote) {
        if (listaTransacoesPorSubLote == null) {
            this.listaTransacoesPorSubLote = emptyList();
        } else {
            this.listaTransacoesPorSubLote = listaTransacoesPorSubLote;
        }
	}

    public Collection<ObjetoAnalise> getTransacoesSubLote() {
		return this.listaTransacoesPorSubLote;
	}
    
}