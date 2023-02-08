/* Regra 25 */
Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		  = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa  = 'F'
  And Cd_Forma_Oper = 7
  And Cd_Ag_Oper   Is Not Null
  And Vl_Oper      Is Not Null
  And ((Vl_Renda_Fat Is Not Null
         And Vl_Oper >= (Vl_Renda_Fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Regra = 25 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_percRenda') ))
        OR Vl_Renda_Fat Is Null)
  And Exists (Select 1 From Tb_Agencia  Where Cd_Ag = T.Cd_Ag_Oper And Fl_Front = 1)  	 	           
Union
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		  = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa  = 'J'
  And Cd_Forma_Oper = 7
  And Cd_Ag_Oper   Is Not Null
  And Vl_Oper      Is Not Null
  And ((Vl_Renda_Fat Is Not Null
        And Vl_Oper >= (Vl_Renda_Fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Regra = 25 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_percFatmt') ))
        or Vl_Renda_Fat Is Null)
  And Exists (Select 1 From Tb_Agencia  Where Cd_Ag = T.Cd_Ag_Oper And Fl_Front = 1)  	 	           

) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 25 And Cd_Versao_Sistema = 3;

