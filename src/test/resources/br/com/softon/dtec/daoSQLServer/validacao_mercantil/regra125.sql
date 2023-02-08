/* regra 125 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801
  AND CD_NATUR_OPER = 2
  AND CD_DOC_IDENTF_DESTORIG IS NOT NULL
  AND CD_PAIS_DESTORIG is not null  
  AND CD_PAIS_DESTORIG <> 24
  AND VL_OPER IS NOT NULL
  AND ((VL_RENDA_FAT IS NOT NULL AND
        VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 125 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat' )))
        OR 
        VL_RENDA_FAT IS NULL)
UNION
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801
  AND CD_NATUR_OPER = 2
  AND CD_DOC_IDENTF_DESTORIG IS NOT NULL
  AND CD_PAIS_DESTORIG is not null  
  AND CD_PAIS_DESTORIG <> 24
  
  AND EXISTS 
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
        WHERE CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
        )
  
  AND NOT EXISTS 
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
        WHERE CD_IDENTF_PERFIL = 4
          AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
          AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
          AND CD_VARIAVEL_TERCEIRA = T.CD_DOC_IDENTF_DESTORIG
          AND CD_VARIAVEL_QUARTA   = T.CD_TP_IDENTF_DESTORIG
  	  	  AND cd_ano_mes <> CASE WHEN MONTH(t.dt_trans) < 10
                                THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
              END
        )

  
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 125;