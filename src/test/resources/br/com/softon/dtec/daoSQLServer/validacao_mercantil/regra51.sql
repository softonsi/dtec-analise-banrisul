/* regra 51 */
Select Count(*) As Total From
( 
Select distinct Cd_Transacao 
From Tb_Trans_Anlse t
 Where CD_LOTE = 2014091801 and cd_sublote = 1
   And Cd_Tp_Oper In (5, 8, 9)
   And Vl_Oper Is Not Null 
   And ( 
      (Vl_Oper < (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 51 And Nm_Campo_Param = 'pm_ValorNotificacao')
      And 
      ((Vl_Oper/(Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 51 And Nm_Campo_Param = 'pm_ValorNotificacao')) * 100) > 
                          (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 51 And Nm_Campo_Param = 'pm_percAbxLimite')
      )
      Or
      (vl_oper >= 1000 and (vl_oper % 1000) = 0)
      )   
 ) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 51

	

