select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		in (5,8,9)
	and CD_LOTE 		= 2014091801
	and (
		(CD_AG_DESTORIG is null or CD_CTA_DESTORIG is null) or CD_DOC_IDENTF_DESTORIG is null
	)
union	
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	CD_NATUR_OPER = 2
	and CD_FORMA_OPER 		= 2	
	and FL_CHEQUE_ENDOSSO 		= 0	
	and CD_LOTE 		= 2014091801	
union	
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		= 20
	and CD_LOTE 		= 2014091801
	and CD_DOC_IDENTF_BNEFC 	is null
union	
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		= 25
	and CD_LOTE 		= 2014091801
	and CD_DOC_IDENTF_BNEFC 	is null
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 1