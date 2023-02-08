INSERT INTO TB_LOTE_PROCES (
CD_LOTE,CD_STATUS_PROCES,DT_INIC_CARGA,DT_FIM_CARGA,DT_INIC_PROCES,DT_FIM_PROCES,DT_REF,DT_ATULZ_REG
) VALUES (
:cd_lote, 70, sysdate - 10/24/60, sysdate - 1/24/60, sysdate, null, null, null
);

insert into tb_sub_lote (
cd_lote, cd_sublote, dt_inicio_carga_analise, dt_fim_carga_analise
) values (
:cd_lote,1,sysdate - 1/24/60, sysdate
);

