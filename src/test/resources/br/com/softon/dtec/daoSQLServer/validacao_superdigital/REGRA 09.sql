/* Valor de saques igual ou superior a 90% dos créditos recebidos, considerando o total de cargas, no mês calendário fechado. Essa regra não se aplica a CS PF FOPA.*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_NATUR_OPER = 2
  AND CD_TP_OPER = 7
  AND CD_TP_CTA <> 9
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO s, TB_PERFIL_MES_CALENDARIO c
	     WHERE s.CD_IDENTF_PERFIL = 7
		   AND s.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND s.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND s.CD_VARIAVEL_TERCEIRA = 7
	  	   AND s.cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                                    THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                    ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                               END 	  		
           AND c.CD_IDENTF_PERFIL = 6
		   AND c.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND c.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND c.CD_VARIAVEL_TERCEIRA = 1
	  	   AND c.cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                                    THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                    ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                               END 	  		
		   							   
		   AND c.VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VCreditoMensal_PF' and cd_regra = 9 and cd_versao_sistema = 3)
		   AND s.VL_TOTAL >= (c.VL_TOTAL * (select vl_param/100 from tb_regra_parametro where nm_campo_param = 'pm_percSaqueCredito_PF' and cd_regra = 9 and cd_versao_sistema = 3))		   
       )
union
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_NATUR_OPER = 2
  AND CD_TP_OPER = 7
  AND CD_TP_CTA <> 9
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO s, TB_PERFIL_MES_CALENDARIO c
	     WHERE s.CD_IDENTF_PERFIL = 7
		   AND s.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND s.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND s.CD_VARIAVEL_TERCEIRA = 7
	  	   AND s.cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
           AND c.CD_IDENTF_PERFIL = 6
		   AND c.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND c.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND c.CD_VARIAVEL_TERCEIRA = 1
	  	   AND c.cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
		   							   
		   AND c.VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VCreditoMensal_PJ' and cd_regra = 9 and cd_versao_sistema = 3)
		   AND s.VL_TOTAL >= (c.VL_TOTAL * (select vl_param/100 from tb_regra_parametro where nm_campo_param = 'pm_percSaqueCredito_PJ' and cd_regra = 9 and cd_versao_sistema = 3))		   
       )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 9

	   



