select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE t.CD_LOTE = 2014091801
	and vl_oper is not null
	and vl_renda_fat is not null
	and vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 86 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlDefinidoLei') 
	and vl_oper > (t.vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 86 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat') )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 86