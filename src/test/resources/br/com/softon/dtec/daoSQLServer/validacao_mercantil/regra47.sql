
select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 and cd_sublote = 1 
  AND CD_TP_PESSOA = 'F'
  AND CD_TP_OPER = 24
  AND NM_ENDER_RESID is not NULL 
  AND CD_CEP_RESID is not NULL
  AND exists (
  	select count(1) from tb_cad_clie 
  	where CD_TP_PESSOA = 'F'
	  AND CD_DOC_IDENTF_CLIE <> t.CD_DOC_IDENTF_CLIE
	  AND NM_ENDER_RESID is not NULL 
	  AND CD_CEP_RESID is not NULL
	  AND NM_ENDER_RESID = t.NM_ENDER_RESID
	  AND CD_CEP_RESID   = t.CD_CEP_RESID 
	  AND NOT ( (NM_MAE is not null     and NM_MAE = T.NM_MAE) or
			    (NM_PAI is not null     and NM_PAI = T.NM_PAI) or
			    (NM_CONJUGE is not null and NM_CLIE    is not null and NM_CONJUGE = t.NM_CLIE and t.NM_CONJUGE = NM_CLIE) or
			    (NM_MAE     is not null and NM_CONJUGE is not null and t.NM_MAE   = NM_CONJUGE) or
			    (NM_PAI     is not null and NM_CONJUGE is not null and t.NM_PAI   = NM_CONJUGE) or
			    (NM_MAE     is not null and NM_CLIE    is not null and t.NM_CLIE  = NM_MAE) or
			    (NM_PAI     is not null and NM_CLIE    is not null and t.NM_CLIE  = NM_PAI) 			    			    
                )
			  	  having COUNT(1) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 47 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeEnderResid')  
	          )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 and cd_sublote = 1 
  AND CD_TP_PESSOA = 'F'
  AND CD_TP_OPER = 24
  AND exists (
  	select count(1) from tb_clie_renda r
  	where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
	  AND CD_TP_IDENTF_CLIE  = t.CD_TP_IDENTF_CLIE
  	  AND NM_ENDER_TRAB is not null
	  AND NM_EMP_TRAB is not null	  
	  AND exists (
	  	select 1 from tb_clie_renda
  		where CD_DOC_IDENTF_CLIE <> t.CD_DOC_IDENTF_CLIE
	  		AND NM_ENDER_TRAB is not null
	  		AND NM_EMP_TRAB is not null
	  		AND NM_ENDER_TRAB = r.NM_ENDER_TRAB
	  		AND NM_EMP_TRAB = r.NM_EMP_TRAB
	  ) having COUNT(*) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 47 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeEnderTrab')  	
	)
) S; 
select count(1) as total from TB_DETLH_APONTAMENTO WHERE CD_REGRA = 47