SELECT CD_IDENTF_SIMULA,CD_LOTE,DT_INICIO_ANLSE,DT_FIM_ANLSE,QT_CLIE_PROCES,QT_CLIE_SUSP,VL_PONTO_CORTE,DT_SIMULA 
FROM TB_CONTROLE_SIMULA 
where dt_fim_anlse is null order by DT_SIMULA asc