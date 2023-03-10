merge into tb_apontamento_simula a
using (
	SELECT DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, COALESCE(SUM(VL_ACUM),0) + COALESCE(SUM(VL_NACUM),0) vl_apontamento, MAX(FL_GRAVISSIMA) FL_GRAVISSIMA, CD_IDENTF_SIMULA
	FROM (
		SELECT 
			D.DT_APONTAMENTO, 
			D.CD_DOC_IDENTF_CLIE, 
			D.CD_TP_IDENTF_CLIE, 
			D.CD_REGRA,
			(SELECT CASE VL_PONTO WHEN 4 THEN 1 ELSE 0 END FROM TB_REGRA_SIMULA C WHERE D.CD_REGRA = C.CD_REGRA AND D.CD_VERSAO_SISTEMA = C.CD_VERSAO_SISTEMA AND D.CD_IDENTF_SIMULA = C.CD_IDENTF_SIMULA) FL_GRAVISSIMA,
			CASE RP.FL_ACUM_REGRA WHEN 1 THEN SUM(R.VL_PONTO) END VL_ACUM,
			CASE RP.FL_ACUM_REGRA WHEN 0 THEN
			     (SELECT MIN(R.VL_PONTO) FROM TB_DETLH_APONTAMENTO_SIMULA T 
					INNER JOIN TB_REGRA_SIMULA R ON T.CD_REGRA = R.CD_REGRA AND 
						T.CD_VERSAO_SISTEMA = R.CD_VERSAO_SISTEMA AND R.CD_IDENTF_SIMULA = T.CD_IDENTF_SIMULA
			     	WHERE T.DT_APONTAMENTO = D.DT_APONTAMENTO AND 
			     		T.CD_DOC_IDENTF_CLIE = D.CD_DOC_IDENTF_CLIE AND 
			     		T.CD_TP_IDENTF_CLIE = D.CD_TP_IDENTF_CLIE AND 
			     		T.CD_REGRA = D.CD_REGRA AND 
			     		T.CD_IDENTF_SIMULA = D.CD_IDENTF_SIMULA AND 
			     		D.CD_VERSAO_SISTEMA = T.CD_VERSAO_SISTEMA) END VL_NACUM, 
			D.CD_IDENTF_SIMULA 
		FROM TB_DETLH_APONTAMENTO_SIMULA D
		INNER JOIN TB_REGRA_SIMULA R ON D.CD_REGRA = R.CD_REGRA AND D.CD_VERSAO_SISTEMA = R.CD_VERSAO_SISTEMA AND R.CD_IDENTF_SIMULA = D.CD_IDENTF_SIMULA
		INNER JOIN TB_REGRA RP ON D.CD_REGRA = RP.CD_REGRA AND D.CD_VERSAO_SISTEMA = RP.CD_VERSAO_SISTEMA
		GROUP BY D.DT_APONTAMENTO, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA, D.CD_VERSAO_SISTEMA, FL_ACUM_REGRA, D.CD_IDENTF_SIMULA
) GROUP BY DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_IDENTF_SIMULA) b
on (
    a.dt_apontamento = b.dt_apontamento and
    a.cd_doc_identf_clie = b.cd_doc_identf_clie and
    a.cd_tp_identf_clie = b.cd_tp_identf_clie and
    a.CD_IDENTF_SIMULA = b.CD_IDENTF_SIMULA
)
when matched then update
set a.vl_apontamento = case when b.vl_apontamento > 999 then 999 else b.vl_apontamento end,
    a.vl_ponto_corte = (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE'),
    a.fl_susp_ld = case when b.vl_apontamento >= (select vl_param_real from tb_param_global where nm_campo_param = 'VL_PONTO_CORTE') then 1
                   else
                      case when FL_GRAVISSIMA = 1 then 1 else 0 end
                   end
 where a.cd_lote = :cd_lote and
    a.CD_IDENTF_SIMULA = :cd_simula