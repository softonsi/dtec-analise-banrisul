insert into tb_sub_lote (cd_lote, cd_sublote, dt_fim_carga_analise) values (:cd_lote,1,sysdate);
insert into tb_lote_proces (cd_lote) values (:cd_lote);
