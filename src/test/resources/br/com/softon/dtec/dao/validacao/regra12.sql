select count(*) as total from
(SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_PESSOA = 'F'
  AND ( (CD_TP_OPER IN (4,7) AND CD_FORMA_OPER = 7 ) OR CD_TP_OPER = 17 )
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 12 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'))
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE = 2014091801
  AND ( (CD_TP_OPER IN (4,7) AND CD_FORMA_OPER = 7 ) OR CD_TP_OPER = 17 )
  AND exists (select * from TB_CLIE_RENDA where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE and CD_TP_IDENTF_CLIE = t.CD_TP_IDENTF_CLIE)
  AND exists (select * from TB_MED_OCUP o
  			where t.VL_OPER > (o.VL_MED_CREDITO * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 12 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percOcupacao') )
  			and exists (select * from TB_CLIE_RENDA where CD_OCUP = o.CD_OCUP) )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_PESSOA = 'J'
  AND ( (CD_TP_OPER IN (4,7) AND CD_FORMA_OPER = 7 ) OR CD_TP_OPER = 17 )
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 12 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'))
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE = 2014091801
  AND ( (CD_TP_OPER IN (4,7) AND CD_FORMA_OPER = 7 ) OR CD_TP_OPER = 17 )
  AND exists (select * from TB_MED_RAMO_ATIVID where CD_RAMO_ATIVID = t.CD_RAMO_ATIVID and 
  			t.VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRamo_Ativid') ) )
  			); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 12