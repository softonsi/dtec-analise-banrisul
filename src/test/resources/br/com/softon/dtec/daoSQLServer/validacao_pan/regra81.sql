select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND VL_OPER IS NOT NULL
  AND ( CD_FORMA_OPER IN (11,12) OR CD_TP_OPER IN (25,26) )
  AND exists (
  	select 1 from TB_PERFIL_INFORMACAO
  	where CD_IDENTF_PERFIL = 14
	  AND CD_VARIAVEL_PRIMEIRA = t.CD_DOC_IDENTF_CLIE
	  AND CD_VARIAVEL_SEGUNDA = t.CD_TP_IDENTF_CLIE
	  AND VL_MEDIO_DIARIO IS NOT NULL
	  AND t.VL_OPER > ( VL_MEDIO_DIARIO * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 81 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percPerfil') )
  )  	
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE 		= 2014091801
  AND VL_OPER IS NOT NULL
  AND CD_NATUR_OPER = 1
  AND ( CD_FORMA_OPER = 11 or CD_TP_OPER in (25,26) )
  AND exists (
  	select 1 from tb_clie_renda r
  		where cd_doc_identf_clie = t.cd_doc_identf_clie 
  			and cd_tp_identf_clie = t.cd_tp_identf_clie
  			and exists (
  				select 1 from tb_med_ocup o 
  					where vl_med_credito is not null
  						and t.vl_oper > (vl_med_credito * 
  						(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 81 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percMedOcup')  )
  						and r.cd_ocup = o.cd_ocup )
  )  
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE 		= 2014091801
  AND VL_OPER IS NOT NULL
  AND VL_RENDA_FAT IS NOT NULL
  AND ( CD_FORMA_OPER in (11,12) or CD_TP_OPER in (25,26) )
  AND VL_OPER > ( VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 81 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda') )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND VL_OPER IS NOT NULL
  AND CD_NATUR_OPER = 1
  AND ( CD_FORMA_OPER = 11 or CD_TP_OPER in (25,26) )
  AND exists (
  	select 1 from tb_med_ramo_ativid where cd_ramo_ativid = t.cd_ramo_ativid and vl_med_credito is not null and t.vl_oper > (vl_med_credito * 
  		(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 81 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_perMedRamoAtivid')  )
  )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND VL_OPER IS NOT NULL
  AND VL_RENDA_FAT IS NOT NULL
  AND ( CD_FORMA_OPER in (11,12) or CD_TP_OPER in (25,26) )
  AND VL_OPER > ( VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 81 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFat') )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 81