update tb_regra
	set
		fl_regra_ativa = 1,
		tx_regra_dinamica = ?
	where
		cd_regra = 35