
select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where CD_LOTE 		= 2014091801 
  and (
  ( CD_TP_OPER in (6,4,5,8,9) and CD_DOC_IDENTF_DESTORIG is null)
   or (DT_TRANS is null)
   or (CD_NATUR_OPER != 3 and VL_OPER is null)	   
   )
  
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 42

