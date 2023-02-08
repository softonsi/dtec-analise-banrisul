/* regra 32 */
Select Count(*) As Total From
( 
Select distinct Cd_Transacao 
From Tb_Trans_Anlse t
 Where CD_LOTE = 2014091801 and cd_sublote = 1
   And Cd_Forma_Oper = 8
   And Vl_Oper Is Not Null 
   And Exists
     (Select 1 From Tb_Natur_Declr_Oper N
      Where T.Cd_Natur_Declr_Oper = N.Cd_Natur_Declr_Oper
        And N.Vl_Medio_Natur_Oper Is Not Null
        And T.Vl_Oper >= (N.Vl_Medio_Natur_Oper * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Regra = 32 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_percNaturOper'))
      )   
 ) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 32

				 

