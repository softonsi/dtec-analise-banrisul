select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
	and CD_LOTE = 2014091801
	and CD_TP_OPER = 20
	and DT_NASCTO_FUND is not null
    and CD_CONTRATO is not null
	and datediff(year,DT_NASCTO_FUND,getdate()) < 18
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 69