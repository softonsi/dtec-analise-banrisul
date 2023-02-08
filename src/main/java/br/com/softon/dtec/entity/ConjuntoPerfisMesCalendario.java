package br.com.softon.dtec.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConjuntoPerfisMesCalendario {

    private final Set<PerfilMesCalendario> listaPerfisMesCalendario;

    private final PerfilMesCalendarioClienteMap perfilMesCalendarioClienteMap;
    
    @SuppressWarnings("unchecked")
	public ConjuntoPerfisMesCalendario(final Set<PerfilMesCalendario> listaPerfisMesCalendario) {
        if (listaPerfisMesCalendario == null) {
            this.listaPerfisMesCalendario = java.util.Collections.EMPTY_SET;
        } else {
            this.listaPerfisMesCalendario = listaPerfisMesCalendario;
        }
        
        perfilMesCalendarioClienteMap = new PerfilMesCalendarioClienteMap(this.listaPerfisMesCalendario);
    }

    public Set<PerfilMesCalendario> getListaPerfisMesCalendario() {
        return listaPerfisMesCalendario;
    }

    public HashSet<PerfilMesCalendario> filter(String codigoIdentificacaoCliente) {
		return perfilMesCalendarioClienteMap.filter(codigoIdentificacaoCliente);
	}

    private class PerfilMesCalendarioClienteMap {
    	
    	private final Logger logger = LoggerFactory
    			.getLogger(PerfilMesCalendarioClienteMap.class);
    	
    	private HashMap<String, HashSet<PerfilMesCalendario>> loadedPerfis;
    	
    	public PerfilMesCalendarioClienteMap(Set<PerfilMesCalendario> perfis) {
    		loadPerfis(perfis);
    	}
    	
    	private void loadPerfis(final Collection<PerfilMesCalendario> perfis) {
    		if (perfis == null || perfis.isEmpty()) {
				return;
			}
    		
    		loadedPerfis = new HashMap<String, HashSet<PerfilMesCalendario>>();
    		
    		for (Iterator<PerfilMesCalendario> iterator = perfis.iterator(); iterator.hasNext();) {
    			PerfilMesCalendario perfilMesCalendario = iterator.next();
    			
    			String key = perfilMesCalendario.getCodVariavelSegunda()+perfilMesCalendario.getCodVariavelPrimeira();
    			
    			HashSet<PerfilMesCalendario> hashSet = loadedPerfis.get( key );
    			
    			if (hashSet != null) {
    				hashSet.add(perfilMesCalendario);
    			} else {
    				HashSet<PerfilMesCalendario> newHashSet = new HashSet<PerfilMesCalendario>();
    				newHashSet.add(perfilMesCalendario);
    				
    				loadedPerfis.put(key, newHashSet);
    			}
    		}
    		
    		logger.info("CARGA DE PERFIS MÊS CALENDARIO PARA FILTRO DE CLIENTE FINALIZADA - QTD: "+loadedPerfis.size());
    	}

    	/**
    	 * Recebe o código identificador do cliente completo (tipoIdentificacao + codigoIdentificacao)
    	 * @param codigoIdentificacaoCliente
    	 * @return
    	 */
    	public HashSet<PerfilMesCalendario> filter(String codigoIdentificacaoCliente) {
    		return loadedPerfis.get(codigoIdentificacaoCliente);
    	}
    }
}