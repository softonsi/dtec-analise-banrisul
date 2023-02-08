package br.com.softon.dtec.entity;

import java.util.ArrayList;
import java.util.List;

public class TransacaoAlertada {

	private final ObjetoAnalise transacao;
    private final List<DetalheApontamento> listaDetalheApontamentos = new ArrayList<DetalheApontamento>();
    private Integer valorPontuacaoTotal = 0;

    public TransacaoAlertada(ObjetoAnalise tx) {
        this.transacao = tx;
    }

    public ObjetoAnalise getTransacao() {
        return transacao;
    }

    public List<DetalheApontamento> getListaDetalheApontamentos() {
        return listaDetalheApontamentos;
    }

    public void adicionarDetalheApontamento(final DetalheApontamento apontamento, Integer pontuacaoRegra) {
        listaDetalheApontamentos.add(apontamento);
        valorPontuacaoTotal += pontuacaoRegra;
    }

    public Integer getValorPontuacaoTotal() {
        return this.valorPontuacaoTotal;
    }

}
