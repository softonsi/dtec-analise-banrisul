select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t1
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE 		= 2014091801
  AND CD_FORMA_OPER in (8, 4) 
  AND EXISTS (
	select 1 from tb_trans_anlse t2
		where   cd_tp_pessoa = 'F'
		    and cd_lote = 2014091801
		    and cd_forma_oper in (8, 4) 
  		    and t2.cd_Transacao       <> t1.cd_Transacao		          
	         and t2.cd_Doc_Identf_Clie <> t1.cd_Doc_Identf_Clie
	         and t2.nm_Mae             <> t1.nm_Mae
	         and t2.nm_Pai             <> t1.nm_Pai
         	    and t2.nm_Ender_Resid     = t1.nm_Ender_Resid
     having count(1) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 23 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeOcorrencia')
  )
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 23