select count(*) as total from
(
SELECT CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_OPER IN (25,26)
  AND CD_FORMA_OPER  IS NOT NULL
  AND CD_CARTAO IS NOT NULL
  
  AND exists (
	select 1 from tb_trans_anlse 
	where   cd_cartao = t.cd_cartao
		and cd_transacao <> t.cd_transacao
		and cd_forma_oper is not null
		and cd_forma_oper <> t.cd_forma_oper
		and cd_tp_oper in (25,26)
  ) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 84