select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO,cd_tp_oper, cd_forma_oper,
CASE WHEN MONTH(GETDATE()) < 10
THEN CAST(YEAR(GETDATE()) AS VARCHAR) + '0' + CAST(MONTH(GETDATE()) AS VARCHAR)
ELSE CAST(YEAR(GETDATE()) AS VARCHAR) + CAST(MONTH(GETDATE()) AS VARCHAR)
END as CD_ANO_MES
FROM TB_TRANS_ANLSE t
WHERE t.CD_LOTE = 2014091801
    and cd_tp_oper = 6 /*pagamento*/
	and vl_oper is not null
	and vl_renda_fat is not null
	and vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 11 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlDefinidoLei') 
	and vl_oper > (t.vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 11 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat') )
	
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 11

