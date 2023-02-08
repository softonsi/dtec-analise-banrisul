
select count(*) as total from
(	
	SELECT CD_TRANSACAO FROM TB_TRANS_ANLSE
	WHERE Fl_Info_Coaf = 1
      AND cd_lote = 2014091801
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 12
      