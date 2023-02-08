select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND VL_SLD_CREDOR is not null
  AND VL_SLD_CREDOR > 0
  AND DT_FECHA_FATURA is not NULL
  AND exists (
  	select count(1) from TB_PERFIL_INFORMACAO 
  	where CD_IDENTF_PERFIL = 20
	  AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
	  AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
	  
	  having COUNT(1) > (select vl_param from tb_regra_parametro where CD_REGRA = 1176 and CD_VERSAO_SISTEMA = 1 and NM_CAMPO_PARAM = 'pm_qHabitualidade')
	)
) s; 
select count(1) as total from TB_DETLH_APONTAMENTO WHERE CD_REGRA = 1176



