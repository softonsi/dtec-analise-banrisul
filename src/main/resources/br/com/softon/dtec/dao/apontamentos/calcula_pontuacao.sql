merge into tb_apontamento a
using (
SELECT DT_APONTAMENTO, cd_lote_apontamento, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, COALESCE(SUM(VL_ACUM),0) + COALESCE(SUM(VL_NACUM),0) vl_apontamento
FROM (
SELECT D.DT_APONTAMENTO, d.cd_lote_apontamento, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA,
CASE R.FL_ACUM_REGRA WHEN 1 THEN SUM(D.VL_PONTO) END VL_ACUM,
CASE R.FL_ACUM_REGRA WHEN 0 THEN
     (SELECT MIN(VL_PONTO) FROM TB_DETLH_APONTAMENTO T WHERE T.DT_APONTAMENTO = D.DT_APONTAMENTO 
       	and t.cd_lote_apontamento = d.cd_lote_apontamento AND T.CD_DOC_IDENTF_CLIE = D.CD_DOC_IDENTF_CLIE AND T.CD_TP_IDENTF_CLIE = D.CD_TP_IDENTF_CLIE AND T.CD_REGRA = D.CD_REGRA) END VL_NACUM
FROM TB_DETLH_APONTAMENTO D
INNER JOIN TB_REGRA R ON D.CD_REGRA = R.CD_REGRA AND D.CD_VERSAO_SISTEMA = R.CD_VERSAO_SISTEMA
where exists
    (select 1 from tb_apontamento t
     where d.dt_apontamento = t.dt_apontamento
       and t.cd_doc_identf_clie = d.cd_doc_identf_clie
       and t.cd_tp_identf_clie = d.cd_tp_identf_clie
       and t.cd_lote = d.cd_lote_apontamento
      )
GROUP BY D.DT_APONTAMENTO, D.cd_lote_apontamento, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA, FL_ACUM_REGRA
) GROUP BY DT_APONTAMENTO, cd_lote_apontamento, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE) b
on (a.dt_apontamento = b.dt_apontamento and a.cd_doc_identf_clie = b.cd_doc_identf_clie and a.cd_tp_identf_clie = b.cd_tp_identf_clie  and a.cd_sttus_evento_atual = 2)
when matched then update
set a.vl_apontamento = case when b.vl_apontamento > 999 then 999 else b.vl_apontamento end