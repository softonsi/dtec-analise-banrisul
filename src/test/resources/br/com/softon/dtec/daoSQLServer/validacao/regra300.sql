select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE FL_DTEC_GOV = 1
  AND CD_LOTE 		= 2014091801    
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 300