INSERT INTO tb_sub_lote_simula 
				  ( 
				    cd_identf_simula, 
				    cd_lote, 
				    cd_sublote, 
				    dt_inicio_analise, 
				    dt_fim_analise, 
				    cd_processamento, 
				    qt_tentativa, 
				    dt_inicio_carga_analise, 
				    dt_fim_carga_analise, 
				    qt_trans, 
				    nm_maquina 
				  ) 
				SELECT ?, 
				  cd_lote, 
				  cd_sublote, 
				  null, 
				  null, 
				  null, 
				  null, 
				  dt_inicio_carga_analise, 
				  dt_fim_carga_analise, 
				  qt_trans, 
				  null 
				FROM TB_sub_lote 
				WHERE cd_lote = ?