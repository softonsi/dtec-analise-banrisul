/* regra 52 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'F'
  And Cd_Natur_Oper = 2
  And Cd_Tp_Oper In (4,5,8,9)  
  And Vl_Oper Is Not Null
  And Cd_Doc_Identf_Destorig Is Not Null
  And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig
  And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_AltoValorPF')
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 3
       And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda = T.Cd_Tp_Identf_Clie
       And Cd_Variavel_Quarta = 2
       And Vl_Total Is Not Null
       And Qt_Total Is Not Null
       And Vl_Total > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_AltoValorPF')
       having sum(Qt_Total) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_QtdContumazPF')       
      )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'J'
  And Cd_Natur_Oper = 2
  And Cd_Tp_Oper In (4,5,8,9)
  And Vl_Oper Is Not Null
  And Cd_Doc_Identf_Destorig Is Not Null
  And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig
  And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_AltoValorPJ')
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 3
       And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda = T.Cd_Tp_Identf_Clie
       And Cd_Variavel_quarta = 2
       And Vl_Total Is Not Null
       And Qt_Total Is Not Null
       And Vl_Total > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_AltoValorPJ')
       having sum(Qt_Total) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 52 And Nm_Campo_Param = 'pm_QtdContumazPJ')       
      )      
) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 52


            