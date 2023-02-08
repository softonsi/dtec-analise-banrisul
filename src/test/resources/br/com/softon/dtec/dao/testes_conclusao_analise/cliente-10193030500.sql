select vl_apontamento, vl_ponto_corte, fl_carencia, fl_ponto_corte, fl_susp_ld 
from tb_apontamento a
where cd_doc_identf_clie = '10193030500' and
    		not exists 
    			(SELECT 1 FROM TB_APONTAMENTO
				WHERE 
					CD_STTUS_EVENTO_ATUAL IN (
						SELECT CD_STTUS_EVENTO FROM TB_STTUS_EVENTO WHERE FL_FIM_INVTGA = 1 AND CD_STTUS_EVENTO NOT IN (1,30)
					) AND
					a.cd_tp_identf_clie = cd_tp_identf_clie AND
					a.cd_doc_identf_clie = cd_doc_identf_clie AND
					a.dt_apontamento = dt_apontamento
			);