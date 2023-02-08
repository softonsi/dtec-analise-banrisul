Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote    = 2014091801
  And Cd_tp_oper = 35 /*resgate*/
  And dt_vencto  is not null 
  And Dt_Trans   Is Not Null
  And  DATEDIFF (DAY, dt_vencto, dt_trans) < (Select Vl_Param  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 75 And Nm_Campo_Param = 'pm_QDia_PrazoCurto')    
 ) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 75

