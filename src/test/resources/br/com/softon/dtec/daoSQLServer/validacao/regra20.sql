select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE 		= 2014091801
  AND CD_FORMA_OPER in (8, 4) 
	and vl_oper 	is not null
	and vl_renda_fat 	is not null
  AND vl_oper      	> (vl_renda_fat * 
  	(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda')  ) 
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE 		= 2014091801
  AND CD_FORMA_OPER in (8, 4) 
  AND exists (
  	select 1 from tb_clie_renda where cd_doc_identf_clie = t.cd_doc_identf_clie and cd_tp_identf_clie = t.cd_tp_identf_clie
  )
  AND exists (
  	select 1 from tb_med_ocup o where t.vl_oper > (vl_med_credito * 
  		(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percOcupacao')  )
  		and exists (select 1 from tb_clie_renda where cd_ocup = o.cd_ocup)
  )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND CD_FORMA_OPER in (8, 4) 
	and vl_oper 	is not null
	and vl_renda_fat 	is not null
  AND vl_oper      	> (vl_renda_fat * 
  	(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFat')  ) 
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND CD_FORMA_OPER in (8, 4) 
  AND exists (
  	select 1 from tb_med_ramo_ativid where cd_ramo_ativid = t.cd_ramo_ativid and cd_tp_identf_clie = t.cd_tp_identf_clie and t.vl_oper > (vl_med_credito * 
  		(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRamo_Ativid')  )
  )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 20