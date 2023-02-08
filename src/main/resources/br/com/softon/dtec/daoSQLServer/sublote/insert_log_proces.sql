insert into tb_log_proces
(DT_INIC_PROCES, DT_FIM_PROCES, NM_PROCES, QT_PROCES, CD_LOTE)
select min(dt_inicio_analise) DT_INIC_PROCES, max(dt_fim_analise) DT_FIM_PROCES, 'DTEC_ANALISE' NM_PROCES, sum(qt_trans) QT_PROCES, max(CD_LOTE) CD_LOTE 
from tb_sub_lote where cd_lote = ?