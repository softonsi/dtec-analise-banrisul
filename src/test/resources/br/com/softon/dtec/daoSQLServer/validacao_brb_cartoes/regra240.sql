select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
  and CD_TP_OPER in (30, 6)
  and DT_TRANS < DT_VENCTO_PCELA
  and DATEDIFF(DAY, DT_TRANS, DT_VENCTO_PCELA) < (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 240 and CD_VERSAO_SISTEMA = 1 and NM_CAMPO_PARAM = 'pm_qtdeDiaAntecipacao')
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 240
