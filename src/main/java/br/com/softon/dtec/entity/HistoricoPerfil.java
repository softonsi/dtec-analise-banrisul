package br.com.softon.dtec.entity;

import java.util.Collection;

import static java.util.Collections.*;

public class HistoricoPerfil {

    private final Collection<Perfil> listaPerfilContaAgenciaTerminal;

    private final Collection<Perfil> listaPerfilContaEstabelecimentoTerminal;

    private final Collection<Perfil> listaPerfilContaDestino;

    private final Collection<Perfil> listaPerfilContaCedente;

    private final Collection<Perfil> listaPerfilCedente;

    private final Collection<Perfil> listaPerfilEstabelecimento;

    private final Collection<Perfil> listaPerfilContaTipoTransacao;
    
    

    public HistoricoPerfil(
			Collection<Perfil> listaPerfilContaAgenciaTerminal,
			Collection<Perfil> listaPerfilContaEstabelecimentoTerminal,
			Collection<Perfil> listaPerfilContaDestino,
			Collection<Perfil> listaPerfilContaCedente,
			Collection<Perfil> listaPerfilCedente,
			Collection<Perfil> listaPerfilEstabelecimento,
			Collection<Perfil> listaPerfilContaTipoTransacao) {
    	
        if (listaPerfilContaAgenciaTerminal == null) {
            this.listaPerfilContaAgenciaTerminal = emptyList();
        } else {
            this.listaPerfilContaAgenciaTerminal = listaPerfilContaAgenciaTerminal;
        }
        if (listaPerfilContaEstabelecimentoTerminal == null) {
            this.listaPerfilContaEstabelecimentoTerminal = emptyList();
        } else {
            this.listaPerfilContaEstabelecimentoTerminal = listaPerfilContaEstabelecimentoTerminal;
        }
        if (listaPerfilContaDestino == null) {
            this.listaPerfilContaDestino = emptyList();
        } else {
            this.listaPerfilContaDestino = listaPerfilContaDestino;
        }
        if (listaPerfilContaCedente == null) {
            this.listaPerfilContaCedente = emptyList();
        } else {
            this.listaPerfilContaCedente = listaPerfilContaCedente;
        }
        if (listaPerfilCedente == null) {
            this.listaPerfilCedente = emptyList();
        } else {
            this.listaPerfilCedente = listaPerfilCedente;
        }
        if (listaPerfilEstabelecimento == null) {
            this.listaPerfilEstabelecimento = emptyList();
        } else {
            this.listaPerfilEstabelecimento = listaPerfilEstabelecimento;
        }
        if (listaPerfilContaTipoTransacao == null) {
            this.listaPerfilContaTipoTransacao = emptyList();
        } else {
            this.listaPerfilContaTipoTransacao = listaPerfilContaTipoTransacao;
        }
	}



	public Collection<Perfil> getListaPerfilContaAgenciaTerminal() {
		return listaPerfilContaAgenciaTerminal;
	}



	public Collection<Perfil> getListaPerfilContaEstabelecimentoTerminal() {
		return listaPerfilContaEstabelecimentoTerminal;
	}



	public Collection<Perfil> getListaPerfilContaDestino() {
		return listaPerfilContaDestino;
	}



	public Collection<Perfil> getListaPerfilContaCedente() {
		return listaPerfilContaCedente;
	}



	public Collection<Perfil> getListaPerfilCedente() {
		return listaPerfilCedente;
	}



	public Collection<Perfil> getListaPerfilEstabelecimento() {
		return listaPerfilEstabelecimento;
	}



	public Collection<Perfil> getListaPerfilContaTipoTransacao() {
		return listaPerfilContaTipoTransacao;
	}

    public void dispose() {
    	listaPerfilContaAgenciaTerminal.clear();
    	listaPerfilContaEstabelecimentoTerminal.clear();
    	listaPerfilContaDestino.clear();
    	listaPerfilContaCedente.clear();
    	listaPerfilCedente.clear();
    	listaPerfilEstabelecimento.clear();
    	listaPerfilContaTipoTransacao.clear();
    }    
}
