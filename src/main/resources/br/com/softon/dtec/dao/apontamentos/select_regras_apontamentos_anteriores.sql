select dt_apontamento, cd_regra from tb_detlh_apontamento a 
    	where  
    		cd_tp_identf_clie = ? and
    		cd_doc_identf_clie = ? and
    		exists 
    			(SELECT 1 FROM TB_APONTAMENTO
				WHERE 
					CD_STTUS_EVENTO_ATUAL IN (
						:statusFim
					) AND
					a.cd_tp_identf_clie = cd_tp_identf_clie AND
					a.cd_doc_identf_clie = cd_doc_identf_clie AND
					a.dt_apontamento = dt_apontamento AND
       				cd_lote = a.cd_lote_apontamento
			) and
		(sysdate - :qtdDiaCarencia ) < dt_apontamento
union all
select dt_apontamento, cd_regra from tb_detlh_apontamento_hist a 
    	where  
    		cd_tp_identf_clie = ? and
    		cd_doc_identf_clie = ? and
    		exists 
    			(SELECT 1 FROM TB_APONTAMENTO_HIST
				WHERE 
					CD_STTUS_EVENTO_ATUAL IN (
						:statusFim
					) AND
					a.cd_tp_identf_clie = cd_tp_identf_clie AND
					a.cd_doc_identf_clie = cd_doc_identf_clie AND
					a.dt_apontamento = dt_apontamento AND
       				cd_lote = a.cd_lote_apontamento
			) and
		(sysdate - :qtdDiaCarencia ) < dt_apontamento
	group by dt_apontamento, cd_regra
	order by dt_apontamento, cd_regra