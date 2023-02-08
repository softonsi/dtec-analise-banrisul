package br.com.softon.dtec.service;

import br.com.softon.dtec.entity.ObjetoAnalise;
import br.com.softon.dtec.entity.Sublote;

public interface GeradorAlerta {

	void conclusaoAnalise(Sublote sublote) throws Exception;

	void calcularPontoCorte(Sublote sublote, int qtd_threads, long qtdRowsPerPage) throws Exception;

	void push(ObjetoAnalise obj);

	boolean hasTransacao();
	
	void calculaPontuacaoMensal() throws Exception;
	
	void transporteAgregados(Sublote sublote) throws Exception;

	void salvar() throws Exception;

	void dispose();

}
