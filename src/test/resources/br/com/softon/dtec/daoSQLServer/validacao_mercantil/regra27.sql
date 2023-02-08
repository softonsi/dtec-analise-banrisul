/* Regra 27 */
Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		= 2014091801 and cd_sublote = 1
  And Cd_Natur_Oper = 2 /*debito*/
  And Cd_Tp_Oper    = 7 /*saque*/
  And Cd_Forma_Oper = 7 /*especie*/
  And Exists 
    (Select 1 From Tb_Perfil_Informacao
      Where Cd_Identf_Perfil = 3
        And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
        And Cd_Variavel_Segunda  = t.cd_tp_identf_clie
        AND cd_variavel_quarta   = 1
        Having Count(*) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Regra = 27 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_QtOrigemDistintas')  
     )
) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 27 And Cd_Versao_Sistema = 3;


   
