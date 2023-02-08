/* Regra 23 */

Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		= 2014091801 and cd_sublote = 1
  And Cd_Tp_Oper    = 4
  and cd_forma_oper = 7  
  
  And Exists
   (Select 1 From Tb_Perfil_Informacao
      Where Cd_Identf_Perfil = 2
        And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
        And Cd_Variavel_Segunda  = T.Cd_Tp_Identf_Clie
        And Cd_Variavel_Terceira = 4 /*deposito*/      
        And Cd_Variavel_Quarta   = 7 /*especie*/
        And Qt_Total > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Regra = 23 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_qtDepEspecie')  
        And vl_total > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Regra = 23 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_vlDepEspecie')  
    )	 	  
) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 23 And Cd_Versao_Sistema = 3;

  	