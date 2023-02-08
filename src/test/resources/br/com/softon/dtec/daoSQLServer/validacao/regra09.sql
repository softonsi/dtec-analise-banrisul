select count(*) as total from
(SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_PESSOA = 'F'
  AND ( 
       (CD_FORMA_OPER = 7 AND CD_TP_OPER = 4) OR 
       (CD_TP_OPER = 7 AND CD_FORMA_OPER = 12) OR 
       (CD_TP_OPER = 17) 
       )
  AND VL_OPER >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 9 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlOperEspeciePF')
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 9 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'))
union
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_PESSOA = 'J'
  AND ( 
       (CD_FORMA_OPER = 7 AND CD_TP_OPER = 4) OR 
       (CD_TP_OPER = 7 AND CD_FORMA_OPER = 12) OR 
       (CD_TP_OPER = 17) 
       )
  AND VL_OPER >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 9 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlOperEspeciePJ')
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 9 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFatmt'))
union
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_NATUR_OPER = 1
  AND CD_FORMA_OPER = 7
  AND CD_TP_OPER IN (27, 5, 8, 9)
  AND VL_OPER >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 9 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlTransf_Especie')
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 9