/* Conversão de reais para moedas estrangeiras, cuja somatória seja igual ou superior ao parâmetro, no mês calendário fechado.. */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_TP_OPER   = 63
  AND CD_LOTE = 2014091801 
  AND EXISTS
     (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
       WHERE CD_IDENTF_PERFIL = 7
         AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
         AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
         AND CD_VARIAVEL_TERCEIRA = 63
         AND cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                               THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                          END 	  		
		 AND (VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VConversaoMoeda_PF' and cd_regra = 12 and cd_versao_sistema = 3)
		      OR
		      QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QConversaoMoeda_PF' and cd_regra = 12 and cd_versao_sistema = 3)
              )
        )
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_TP_OPER   = 63
  AND CD_LOTE = 2014091801 
  AND EXISTS
     (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
       WHERE CD_IDENTF_PERFIL = 7
         AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
         AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
         AND CD_VARIAVEL_TERCEIRA = 63
         AND cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                               THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                          END 	  		
		 AND (VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VConversaoMoeda_PJ' and cd_regra = 12 and cd_versao_sistema = 3)
		      OR
		      QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QConversaoMoeda_PJ' and cd_regra = 12 and cd_versao_sistema = 3)
              )
        )
     ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 12      

  				