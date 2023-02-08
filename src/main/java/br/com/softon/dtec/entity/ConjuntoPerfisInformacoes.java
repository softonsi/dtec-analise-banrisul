package br.com.softon.dtec.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConjuntoPerfisInformacoes {

    private final Set<PerfilInformacao> listaPerfisInformacoes;
    
    private final PerfilInformacaoClienteMap perfilInformacaoClienteMap;

    @SuppressWarnings("unchecked")
	public ConjuntoPerfisInformacoes(final Set<PerfilInformacao> listaPerfisInformacoes) {
        if (listaPerfisInformacoes == null) {
            this.listaPerfisInformacoes = java.util.Collections.EMPTY_SET;
        } else {
            this.listaPerfisInformacoes = listaPerfisInformacoes;
            
        }

        perfilInformacaoClienteMap = new PerfilInformacaoClienteMap(this.listaPerfisInformacoes);
    }

    public Set<PerfilInformacao> getListaPerfisInformacoes() {
        return listaPerfisInformacoes;
    }
    
    public HashSet<PerfilInformacao> filter(String codigoIdentificacaoCliente) {
		return perfilInformacaoClienteMap.filter(codigoIdentificacaoCliente);
	}

    private class PerfilInformacaoClienteMap {
    	
    	private final Logger logger = LoggerFactory
    			.getLogger(PerfilInformacaoClienteMap.class);
    	
    	private HashMap<String, HashSet<PerfilInformacao>> loadedPerfis;
    	
    	public PerfilInformacaoClienteMap(Collection<PerfilInformacao> perfis) {
    		loadPerfis(perfis);
    	}
    	
    	private void loadPerfis(final Collection<PerfilInformacao> perfis) {
    		if (perfis == null || perfis.isEmpty()) {
				return;
			}
    		
    		loadedPerfis = new HashMap<String, HashSet<PerfilInformacao>>();
    		
    		for (Iterator<PerfilInformacao> iterator = perfis.iterator(); iterator.hasNext();) {
    			PerfilInformacao perfilInformacao = iterator.next();
    			
    			String key = perfilInformacao.getCodVariavelSegunda()+perfilInformacao.getCodVariavelPrimeira();
    			
    			HashSet<PerfilInformacao> hashSet = loadedPerfis.get( key );
    			
    			if (hashSet != null) {
    				hashSet.add(perfilInformacao);
    			} else {
    				HashSet<PerfilInformacao> newHashSet = new HashSet<PerfilInformacao>();
    				newHashSet.add(perfilInformacao);
    				
    				loadedPerfis.put(key, newHashSet);
    			}
    		}
    		
    		logger.info("CARGA DE PERFIS PARA FILTRO DE CLIENTE FINALIZADA - QTD: "+loadedPerfis.size());
    	}

    	/**
    	 * Recebe o código identificador do cliente completo (tipoIdentificacao + codigoIdentificacao)
    	 * @param codigoIdentificacaoCliente
    	 * @return
    	 */
    	public HashSet<PerfilInformacao> filter(String codigoIdentificacaoCliente) {
    		return loadedPerfis.get(codigoIdentificacaoCliente);
    	}
    }

}
