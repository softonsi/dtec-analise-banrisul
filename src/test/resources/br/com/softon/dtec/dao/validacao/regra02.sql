select count(1) as total from
(SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE FL_PPE = 1
  AND CD_TP_PESSOA = 'F'
  AND ((VL_OPER > (VL_RENDA_FAT * 
            (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO 
             WHERE CD_REGRA = 2 AND CD_VERSAO_SISTEMA = 3
                AND NM_CAMPO_PARAM = 'pm_percRenda')))
        OR
        (VL_OPER > (VL_PATRIM * 
            (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO 
             WHERE CD_REGRA = 2 AND CD_VERSAO_SISTEMA = 3
               AND NM_CAMPO_PARAM = 'pm_percPatrim_PF')))
         )
  AND CD_LOTE = 2014091801
);         
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 2