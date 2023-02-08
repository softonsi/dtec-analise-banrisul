merge into tb_apontamento a
using (
SELECT DT_APONTAMENTO, CD_LOTE_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, COALESCE(SUM(VL_ACUM),0) + COALESCE(SUM(VL_NACUM),0) vl_apontamento, MAX(FL_GRAVISSIMA) FL_GRAVISSIMA
FROM (
SELECT D.DT_APONTAMENTO, D.CD_LOTE_APONTAMENTO, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA,
(SELECT CASE VL_PONTO WHEN 4 THEN 1 ELSE 0 END FROM TB_REGRA C WHERE D.CD_REGRA = C.CD_REGRA AND D.CD_VERSAO_SISTEMA = C.CD_VERSAO_SISTEMA) FL_GRAVISSIMA,
CASE R.FL_ACUM_REGRA WHEN 1 THEN SUM(D.VL_PONTO) END VL_ACUM,
CASE R.FL_ACUM_REGRA WHEN 0 THEN
     (SELECT MIN(VL_PONTO) FROM TB_DETLH_APONTAMENTO T WHERE T.DT_APONTAMENTO = D.DT_APONTAMENTO AND T.CD_DOC_IDENTF_CLIE = D.CD_DOC_IDENTF_CLIE AND T.CD_TP_IDENTF_CLIE = D.CD_TP_IDENTF_CLIE AND T.CD_REGRA = D.CD_REGRA AND D.CD_VERSAO_SISTEMA = T.CD_VERSAO_SISTEMA) END VL_NACUM
FROM TB_DETLH_APONTAMENTO D
INNER JOIN TB_REGRA R ON D.CD_REGRA = R.CD_REGRA AND D.CD_VERSAO_SISTEMA = R.CD_VERSAO_SISTEMA
GROUP BY D.DT_APONTAMENTO, D.CD_LOTE_APONTAMENTO, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA, D.CD_VERSAO_SISTEMA, FL_ACUM_REGRA
) GROUP BY DT_APONTAMENTO, CD_LOTE_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE) b
on (
    a.dt_apontamento = b.dt_apontamento and
    a.cd_lote = b.cd_lote_apontamento and
    a.cd_doc_identf_clie = b.cd_doc_identf_clie and
    a.cd_tp_identf_clie = b.cd_tp_identf_clie
)
when matched then update
set a.vl_apontamento = case when b.vl_apontamento > 999 then 999 else b.vl_apontamento end,
    a.vl_ponto_corte = (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE'),
    a.fl_susp_ld = case when b.vl_apontamento >= (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE') then 1
                   else
                      case when FL_GRAVISSIMA = 1 then 1 else 0 end
                   end,
    a.fl_ponto_corte = case when b.vl_apontamento >= (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE') then 1 else 0 end,
    a.fl_carencia = 1,
    a.DT_ATUALZ_CALCULO = sysdate,
    a.CD_STTUS_EVENTO_ATUAL = case when b.vl_apontamento >= (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE') then 0
                   else
                      case when FL_GRAVISSIMA = 1 then 0 else 1 end
                   end
 where SUBSTR(CAST(a.cd_lote AS VARCHAR(10)), 1, 6) = :cd_lote and
    a.CD_STTUS_EVENTO_ATUAL = 2