
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_NATUR_OPER = 2
  and CD_DOC_IDENTF_DESTORIG IS NOT NULL
  and CD_DOC_IDENTF_CLIE <> CD_DOC_IDENTF_DESTORIG
  and exists
      (SELECT 1 FROM TB_TRANS_ANLSE
        WHERE T.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE
          AND T.CD_TP_IDENTF_CLIE  = CD_TP_IDENTF_CLIE
          AND T.CD_TRANSACAO <> CD_TRANSACAO 
          AND DT_TRANS        < T.DT_TRANS
          AND CD_NATUR_OPER  = 1
          AND DATEDIFF(MINUTE,DT_TRANS, T.DT_TRANS) <  (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 60 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_Minuto') 
          )
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 60

    