package br.com.softon.dtec.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConjuntoRendasClientes {

    private final Set<RendaCliente> listaRendasClientes;

    private final RendaClienteMap rendaClienteMap;
    
    @SuppressWarnings("unchecked")
	public ConjuntoRendasClientes(final Set<RendaCliente> listaRendasClientes) {
        if (listaRendasClientes == null) {
            this.listaRendasClientes = java.util.Collections.EMPTY_SET;
        } else {
            this.listaRendasClientes = listaRendasClientes;
        }
        
        rendaClienteMap = new RendaClienteMap(this.listaRendasClientes);
    }

    public Collection<RendaCliente> getListaRendasClientes() {
        return listaRendasClientes;
    }
    
    public HashSet<RendaCliente> filter(String codigoIdentificacaoCliente) {
		return rendaClienteMap.filter(codigoIdentificacaoCliente);
	}

    private class RendaClienteMap {
    	
    	private final Logger logger = LoggerFactory
    			.getLogger(RendaClienteMap.class);
    	
    	private HashMap<String, HashSet<RendaCliente>> loadedRendas;
    	
    	public RendaClienteMap(Collection<RendaCliente> rendas) {
    		loadPerfis(rendas);
    	}
    	
    	private void loadPerfis(final Collection<RendaCliente> clientes) {
    		if (clientes == null || clientes.isEmpty()) {
				return;
			}
    		
    		loadedRendas = new HashMap<String, HashSet<RendaCliente>>();
    		
    		for (Iterator<RendaCliente> iterator = clientes.iterator(); iterator.hasNext();) {
    			RendaCliente rendaCliente = iterator.next();
    			
    			String key = rendaCliente.getCodTipoIdentificacaoCliente()+rendaCliente.getCodDocIdentificacaoCliente();
    			
    			HashSet<RendaCliente> hashSet = loadedRendas.get( key );
    			
    			if (hashSet != null) {
    				hashSet.add(rendaCliente);
    			} else {
    				HashSet<RendaCliente> newHashSet = new HashSet<RendaCliente>();
    				newHashSet.add(rendaCliente);
    				
    				loadedRendas.put(key, newHashSet);
    			}
    		}
    		
    		logger.info("CARGA DE RENDAS PARA FILTRO DE CLIENTE FINALIZADA - QTD: "+loadedRendas.size());
    	}

    	/**
    	 * Recebe o código identificador do cliente completo (tipoIdentificacao + codigoIdentificacao)
    	 * @param codigoIdentificacaoCliente
    	 * @return
    	 */
    	public HashSet<RendaCliente> filter(String codigoIdentificacaoCliente) {
    		return loadedRendas.get(codigoIdentificacaoCliente);
    	}
    }
}
