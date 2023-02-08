package br.com.softon.dtec.service;

import java.util.Collections;
import java.util.List;

import br.com.softon.dtec.entity.Regra;

public class RegraHandle {

    final List<Regra> regras;
    final List<Regra> regrasRedeNeural;
    final List<Regra> regrasProcessamentoDiario;
    final List<Regra> regrasProcessamentoMensal;
    final List<Regra> regrasPosProcessamentoMensal;

    public RegraHandle(
    		final List<Regra> regras,
    	    final List<Regra> regrasRedeNeural,
    	    final List<Regra> regrasProcessamentoDiario,
    	    final List<Regra> regrasProcessamentoMensal,
    	    final List<Regra> regrasPosProcessamentoMensal) {
        this.regras = Collections.unmodifiableList(regras);
        this.regrasRedeNeural = Collections.unmodifiableList(regrasRedeNeural);
        this.regrasProcessamentoDiario = Collections.unmodifiableList(regrasProcessamentoDiario);
        this.regrasProcessamentoMensal = Collections.unmodifiableList(regrasProcessamentoMensal);
        this.regrasPosProcessamentoMensal = Collections.unmodifiableList(regrasPosProcessamentoMensal);
    }

    public List<Regra> getTodasRegras() {
        return regras;
    }

    public Regra getRegra(short coRegra) {
    	for(Regra regra : regras) {
    		if(regra.getCodigoRegra() == coRegra) {
    			return regra;
    		}
    	}
        return null;
    }

	public List<Regra> getRegrasNeural() {
		return regrasRedeNeural;
	}

	public List<Regra> getRegrasProcessamentoDiario() {
		return regrasProcessamentoDiario;
	}

	public List<Regra> getRegrasProcessamentoMensal() {
		return regrasProcessamentoMensal;
	}

	public List<Regra> getRegrasPosProcessamentoMensal() {
		return regrasPosProcessamentoMensal;
	}

}
