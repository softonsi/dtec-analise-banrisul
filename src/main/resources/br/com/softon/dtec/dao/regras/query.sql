select 
	r.CD_CLASSE_REGRA,
	r.CD_SISCOAF,
	r.CD_REGRA,
	r.CD_TP_REGRA,
	r.CD_USU,
	r.CD_VERSAO_SISTEMA,
	r.DS_ERRO_REGRA,
	r.DS_REGRA,
	r.DT_REGRA,
	r.DT_REGRA_DINAMICA,
	r.FL_ACUM_REGRA,
	r.FL_APAGADO,
	r.FL_REGRA_ATIVA,
	r.NM_REGRA,
	r.TX_REGRA_DINAMICA,
	r.VL_PONTO,
	r.CD_TP_PROCES
 from tb_regra r
 join tb_classe_regra c on r.cd_classe_regra = c.cd_classe_regra
 where 
 	(r.fl_apagado = 0 or r.fl_apagado is null) and 
 	fl_regra_ativa = 1 and
 	c.fl_classe_ativo = 1
 order by r.CD_REGRA
