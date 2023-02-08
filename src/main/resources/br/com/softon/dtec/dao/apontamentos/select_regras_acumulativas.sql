select distinct cd_regra from tb_detlh_apontamento a 
    	where exists (
    		select 1 from tb_regra 
    		where 
    			cd_regra = a.cd_regra and 
    			fl_acum_regra = 0 and 
    			fl_regra_ativa = 1 and 
    			fl_apagado <> 1
    		) and 
    		cd_tp_identf_clie = ? and
    		cd_doc_identf_clie = ? 
    	order by cd_regra