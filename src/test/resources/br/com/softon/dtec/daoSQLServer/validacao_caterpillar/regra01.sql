select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		= 20
	and CD_LOTE 		= 2014091801
	and CD_DOC_IDENTF_BNEFC 	is null
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 1