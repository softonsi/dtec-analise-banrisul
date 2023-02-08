select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_PESSOA = 'F'
  AND FL_AGENTE_PUBLICO = 1
  AND VL_OPER is not NULL 
  AND 
    ((VL_RENDA_FAT is not NULL and
     VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 101 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda') ) )
     OR
     VL_RENDA_FAT is NULL)
     
UNION
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_PESSOA = 'F'
  AND FL_AGENTE_PUBLICO = 1
  AND NOT EXISTS (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
                  WHERE CD_IDENTF_PERFIL = 9
                    AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
                    AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE                 
                    AND CD_VARIAVEL_TERCEIRA = T.CD_TP_OPER
              	  	AND CD_ANO_MES <> CASE WHEN MONTH(t.dt_trans) < 10
                                            THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                            ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                                        END
                  )		 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 101