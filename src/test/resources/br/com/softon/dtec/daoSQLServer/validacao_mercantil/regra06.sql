select count(*) as total from
( 
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA   = 'F'
  AND FL_FUNCIONARIO = 0
  AND FL_BNEFC_INSS  = 0
  AND CD_NATUR_OPER = 1
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesPF' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsCreditoPF'     AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaPF' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )

UNION
/*funcionario*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA   = 'F'
  AND FL_FUNCIONARIO = 1
  AND CD_NATUR_OPER  = 1
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsCreditoFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )
UNION
/*Beneficiario INSS*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA  = 'F'
  AND FL_BNEFC_INSS = 1
  AND CD_NATUR_OPER = 1
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsCreditoBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )

UNION
/*Burla PJ*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE       = 2014091801
  AND CD_TP_PESSOA  = 'J'
  AND CD_NATUR_OPER = 1
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesPJ' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsCreditoPJ' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percFatmt' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )
/* ------------ DEBITOS ---------------*/
UNION
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA   = 'F'
  AND FL_FUNCIONARIO = 0
  AND FL_BNEFC_INSS  = 0
  AND CD_NATUR_OPER = 2
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesPF' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsDebitoPF' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaPF' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )

UNION
/*funcionario*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA   = 'F'
  AND FL_FUNCIONARIO = 1
  AND CD_NATUR_OPER  = 2
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsDebitoFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaFunc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )
UNION
/*Beneficiario INSS*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE        = 2014091801
  AND CD_TP_PESSOA  = 'F'
  AND FL_BNEFC_INSS = 1
  AND CD_NATUR_OPER = 2
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsDebitoBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percRendaBnefc' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )

UNION
/*Burla PJ*/
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE       = 2014091801
  AND CD_TP_PESSOA  = 'J'
  AND CD_NATUR_OPER = 2
  AND EXISTS                    
       (SELECT 1 FROM TB_PERFIL_INFORMACAO P
        WHERE CD_IDENTF_PERFIL = 2
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE            
         HAVING COUNT(*) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_QtTiposDiferentesPJ' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
        )
   AND (
        (EXISTS   
           (SELECT 1 FROM TB_PERFIL_INFORMACAO P
             WHERE CD_IDENTF_PERFIL = 1
               AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
               AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
               AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
               AND VL_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_VlConsDebitoPJ' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3)
             )
          OR ( VL_RENDA_FAT is not null and 
               EXISTS         
                 (SELECT 1 FROM TB_PERFIL_INFORMACAO P
                   WHERE CD_IDENTF_PERFIL = 1
                     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE 
                     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE 
                     AND CD_VARIAVEL_TERCEIRA = T.cd_natur_oper
                     AND VL_TOTAL > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_percFatmt' AND CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3))
                  )
                )
            OR (VL_RENDA_FAT is null)
            )
        )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 6
