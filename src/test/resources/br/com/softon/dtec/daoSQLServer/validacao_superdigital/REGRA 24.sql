/* Contas PF´s com >= y transferências para outras CS, cumulado no mês.*/


select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER     = 9
  AND CD_TP_PESSOA   = 'F'
  AND FL_CS_DESTORIG = 1
  AND CD_LOTE = 2014091801 
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
       WHERE CD_IDENTF_PERFIL = 12
         AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
         AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
         AND cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                               THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                          END 	  		
		 AND QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QTransferenciaMes' and cd_regra = 24 and cd_versao_sistema = 3)
        )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 24

 

  				