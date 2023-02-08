update tb_regra
	set
		fl_regra_ativa = 1,
		tx_regra_dinamica = ?,  dt_regra = sysdate, ds_erro_regra = null
	where
		cd_regra = 90