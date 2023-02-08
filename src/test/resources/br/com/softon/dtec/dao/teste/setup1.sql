
insert into tb_sub_lote (
cd_lote, cd_sublote, dt_inicio_carga_analise, dt_fim_carga_analise
) values (
:cd_lote,1,sysdate - 1/24/60, sysdate
);

