select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	    cd_Tp_Oper in (4,5,8,9)
	and CD_LOTE = 2014091801
	and (
	 (CD_AG_DESTORIG IS NULL AND CD_CTA_DESTORIG IS NULL)
	  OR
	  CD_DOC_IDENTF_DESTORIG IS NULL
	
	)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 44