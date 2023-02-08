package br.com.softon.dtec.entity;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class ConjuntoTransmissoesOrdem {

    private final Collection<TransmissaoOrdem> listaTransmissoesOrdem;

    public ConjuntoTransmissoesOrdem(final Collection<TransmissaoOrdem> listaTransmissoesOrdem) {
        if (listaTransmissoesOrdem == null) {
            this.listaTransmissoesOrdem = emptyList();
        } else {
            this.listaTransmissoesOrdem = listaTransmissoesOrdem;
        }
    }

    public Collection<TransmissaoOrdem> getListaTransmissoesOrdem() {
        return listaTransmissoesOrdem;
    }

}
