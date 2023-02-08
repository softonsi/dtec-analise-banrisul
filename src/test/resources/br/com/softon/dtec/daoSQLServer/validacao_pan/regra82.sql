select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_OPER = 7
  AND CD_TP_CARTAO = 3
  AND CD_FORMA_OPER IN (11,12,13)
  AND CD_VIA_OPER = 1
  AND SG_UF_EMISS_CARTAO IS NOT NULL
  AND SG_UF_AG_OPER IS NOT NULL
  AND SG_UF_EMISS_CARTAO <> SG_UF_AG_OPER
  AND exists (
	select 1 from tb_trans_anlse 
	where   cd_lote = 2014091801
		and cd_cartao = t.cd_cartao
		and cd_transacao <> t.cd_transacao
		and cd_tp_cartao = 3
		and cd_tp_oper = 7
		and cd_forma_oper in (11,12,13)
		and cd_via_oper = 1
        AND SG_UF_EMISS_CARTAO IS NOT NULL
        AND SG_UF_AG_OPER IS NOT NULL		
		and sg_uf_ag_oper <> t.sg_uf_ag_oper
		and sg_uf_emiss_cartao <> sg_uf_ag_oper
	having count(1) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 82 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_MultiploSaques')	
 ) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 82
