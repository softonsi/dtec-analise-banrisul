Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote      = 2014091801
  and cd_tp_oper   = 34 /*investimento*/
  and vl_oper      is not null
  and vl_renda_fat is not null
  and vl_oper      > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 74 And Nm_Campo_Param = 'pm_vlSignificativo')
  and vl_oper      > (vl_renda_fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 74 And Nm_Campo_Param = 'pm_percRendaFat')) 
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 74