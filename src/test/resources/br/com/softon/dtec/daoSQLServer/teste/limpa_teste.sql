delete from tb_detlh_apontamento where cd_lote = 2014091801;
delete from tb_evento 
	where exists (
		select 1 from tb_apontamento 
		where 
			tb_evento.DT_APONTAMENTO = DT_APONTAMENTO and 
			tb_evento.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE and
			tb_evento.CD_TP_IDENTF_CLIE = CD_TP_IDENTF_CLIE and
			cd_lote = 2014091801);
delete from tb_apontamento where cd_lote = 2014091801;
update tb_regra set fl_regra_ativa = 0;