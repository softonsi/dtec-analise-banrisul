select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_NATUR_OPER = 2
  AND CD_LOTE 		= 2014091801
  AND CD_TP_OPER = 7
  AND CD_FORMA_OPER = 7 
  AND exists (
	select 1 from tb_perfil_informacao p
	where p.cd_identf_perfil     = 3
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and p.cd_variavel_terceira = '1'
	  group by cd_identf_perfil, cd_variavel_primeira, cd_variavel_segunda, cd_variavel_terceira
	  having count(*) > (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QtOrigemDistintas' and cd_regra = 19 and cd_versao_sistema = 3)
  )
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 19