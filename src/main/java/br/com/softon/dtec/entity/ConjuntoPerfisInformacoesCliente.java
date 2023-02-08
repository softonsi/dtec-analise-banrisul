package br.com.softon.dtec.entity;

import java.util.Set;

public class ConjuntoPerfisInformacoesCliente {

    private final Set<PerfilInformacao> listaPerfisInformacoes;

    @SuppressWarnings("unchecked")
	public ConjuntoPerfisInformacoesCliente(final Set<PerfilInformacao> listaPerfisInformacoes) {
        if (listaPerfisInformacoes == null) {
            this.listaPerfisInformacoes = java.util.Collections.EMPTY_SET;
        } else {
            this.listaPerfisInformacoes = listaPerfisInformacoes;
        }
    }

    public Set<PerfilInformacao> getListaPerfisInformacoes() {
        return listaPerfisInformacoes;
    }

}
