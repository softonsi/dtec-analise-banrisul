select count(1) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_PESSOA = 'F'
  And Vl_Oper Is Not Null
  And Fl_Ppe = 1
  AND (Vl_Renda_Fat Is Not Null and (VL_OPER > (VL_RENDA_FAT * 
            (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO 
             WHERE CD_REGRA = 2 AND CD_VERSAO_SISTEMA = 3
                AND NM_CAMPO_PARAM = 'pm_percRenda')))
        Or
        (Vl_Patrim Is Not Null and VL_OPER > (VL_PATRIM * 
            (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO 
             WHERE CD_REGRA = 2 AND CD_VERSAO_SISTEMA = 3
               AND NM_CAMPO_PARAM = 'pm_percPatrim_PF')))
         )
) s;         
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 2
