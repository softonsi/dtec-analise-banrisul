

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_LOTE = 2014091801 
  AND CD_TP_OPER = 61
  AND NM_PRODT_DTEC_FLEX LIKE '%DTECINTL%'
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 28






  				