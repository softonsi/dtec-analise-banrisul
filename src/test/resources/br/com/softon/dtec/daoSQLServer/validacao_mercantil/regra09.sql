select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and (cd_forma_oper = 7 or cd_tp_oper = 17)
	and vl_oper is not null
	and vl_oper >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlOperEspecie' and cd_regra = 9 and cd_versao_sistema = 3)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 9