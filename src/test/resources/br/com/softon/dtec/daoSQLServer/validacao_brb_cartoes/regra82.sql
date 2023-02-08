select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_SUBLOTE = 1
  AND CD_TP_OPER   = 7
  AND CD_TP_CARTAO in (1,3)
  AND CD_FORMA_OPER IN (11,12,13,15)
  AND CD_VIA_OPER in (1,2)
  AND exists (
	select 1 from tb_trans_anlse 
	where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
	  and CD_TP_IDENTF_CLIE = t.CD_TP_IDENTF_CLIE
	  AND CD_LOTE = 2014091801 
      AND CD_SUBLOTE = 1
	  and cd_transacao <> t.cd_transacao
	  and cd_tp_cartao in (1,3)
	  and cd_tp_oper = 7
	  and cd_forma_oper in (11,12,13,15)
	  and cd_via_oper in (1,2)
	    
	having (count(1) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 82 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_MultiploSaques')	
	        and
	        sum(vl_oper) > 	(SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 82 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_valorMinimo'))	        
	        )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 82
