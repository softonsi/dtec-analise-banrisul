update tb_regra
	set
		fl_regra_ativa = 1,
		tx_regra_dinamica = ?, dt_regra = GETDATE() 
	where
		cd_regra = 96