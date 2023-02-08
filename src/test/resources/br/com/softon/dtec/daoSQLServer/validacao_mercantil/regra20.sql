select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE = 2014091801
  AND ( CD_FORMA_OPER = 7 OR CD_TP_OPER = 17 )
  AND (
    (exists (
 	select 1 from tb_clie_renda r
  	 where cd_doc_identf_clie = t.cd_doc_identf_clie 
  	   and cd_tp_identf_clie = t.cd_tp_identf_clie
  	   and exists (
  			select 1 from tb_med_ocup o 
 			where r.cd_ocup = o.cd_ocup
 			  and t.vl_oper > (vl_med_credito * 
  				(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percOcupacao')  )
  				)
   	))
   	OR (VL_RENDA_FAT IS NOT NULL AND
         vl_oper > (vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda')) )
    OR VL_RENDA_FAT IS NULL
   )
  
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND ( CD_FORMA_OPER = 7 or CD_TP_OPER = 17 )  
  AND (( exists (
  	    select 1 from tb_med_ramo_ativid 
  	    where cd_ramo_ativid = t.cd_ramo_ativid and vl_med_credito is not null 
  	      and t.vl_oper > (vl_med_credito * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRamo_Ativid')  )
        ))
        or
        (vl_renda_fat is not null AND vl_oper > (vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 20 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFat') ) ) 
        or
        vl_renda_fat is null
        )
    
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 20