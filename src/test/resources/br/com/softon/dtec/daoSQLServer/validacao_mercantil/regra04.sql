select count(*) as total from
( 
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND VL_OPER is not null
  AND CD_LOTE = 2014091801
  AND ( ( VL_RENDA_FAT IS NOT NULL
            AND VL_OPER > ( VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'  ) ) )
           OR
           EXISTS (SELECT 1 FROM TB_MED_OCUP M, TB_CLIE_RENDA C
                   WHERE T.CD_DOC_IDENTF_CLIE = C.CD_DOC_IDENTF_CLIE
                     AND T.CD_TP_IDENTF_CLIE  = C.CD_TP_IDENTF_CLIE
                     AND C.CD_OCUP            = M.CD_OCUP                  
                     AND T.VL_OPER > ( M.VL_MED_CREDITO * 
                        ( SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percOcupacao') )
           )
           OR (VL_RENDA_FAT is not null)
          )
        
Union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND VL_OPER is not null
  AND CD_LOTE = 2014091801
  AND (( VL_RENDA_FAT IS NOT NULL
         AND VL_OPER > ( VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFatmt') ))
       OR EXISTS 
          (SELECT 1 FROM TB_MED_RAMO_ATIVID 
            WHERE T.CD_RAMO_ATIVID = CD_RAMO_ATIVID
              AND (T.VL_OPER >  VL_MED_CREDT * 
                        (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRamoAtivdd' )  ) )
        OR(VL_RENDA_FAT is not null)
       )        
) s;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 4


