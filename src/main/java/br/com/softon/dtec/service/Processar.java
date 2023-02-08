package br.com.softon.dtec.service;

import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.dao.ObjetoAnaliseDAO;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.ObjetoAnalise;

public class Processar implements Callable<Integer>, Configuracao {

    private final static Logger log = LoggerFactory.getLogger(Processar.class);

    public static final AtomicInteger counter = new AtomicInteger();

    private final GeradorAlerta geradorApontamento;

	private final Regra regra;
	
	private final Sublote sublote;
	
	private final ControleSimulacao loteSimula;
	
	private final boolean isSimulacaoAnalise;
	
	private final SortedMap<Short, String> mapaTempoRegra;
	

    public Processar(
    		final SortedMap<Short, String> mapaTempoRegra,
    		final Regra regra,
    		final Sublote sublote,
            final GeradorAlerta geradorApontamento, 
            final ControleSimulacao loteSimula, 
            final boolean isSimulacaoAnalise) {
    	this.mapaTempoRegra = mapaTempoRegra;
		this.regra = regra;
		this.sublote = sublote;
        this.geradorApontamento = geradorApontamento;
        this.loteSimula = loteSimula;
        this.isSimulacaoAnalise = isSimulacaoAnalise;
    }

    public Integer call() throws Exception {
        
        long tempo = System.currentTimeMillis();
        log.debug("processamento do SQL da regra: " + regra.getCodigoRegra() + " iniciado...");
        
        List<ObjetoAnalise> listaObj = ObjetoAnaliseDAO.getObjetosPorRegra(regra, sublote, loteSimula, isSimulacaoAnalise);

        for (ObjetoAnalise obj : listaObj) {
            counter.incrementAndGet();
            
            obj.addRegra(regra.getCodigoRegra().shortValue());
            geradorApontamento.push(obj);
        }
        
        tempo = System.currentTimeMillis() - tempo;

        log.debug("processamento do SQL da regra: " + regra.getCodigoRegra()
                + " concluido em " + tempo / 1000 + " segundos com " + listaObj.size() + " transações processadas.");
        
        mapaTempoRegra.put(regra.getCodigoRegra(), tempo/1000+" segundos para processar com " + listaObj.size() + " disparo(s).");

        return 0;
    }

}