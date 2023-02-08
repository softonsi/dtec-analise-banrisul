/* regra 54 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'F'
  And (FL_BNEFC_INSS = 0 AND FL_FUNCIONARIO = 0)
  And (
     Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlSignificativoPF')
     Or
     (Cd_Natur_Oper = 1 And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlDepositoInusitadoPF'))
    )
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 5
       And Cd_Variavel_Primeira = T.Cd_Banco
       And Cd_Variavel_Segunda  = T.Cd_Ag
       And Cd_Variavel_Terceira = T.Cd_Cta
       And qt_total < (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_qtTransacoesPF')
      )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'F'
  And (FL_FUNCIONARIO = 1)
  And (
     Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlSignificativoFuncionario')
     Or
     (Cd_Natur_Oper = 1 And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlDepositoInusitadoFuncionario'))
    )
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 5
       And Cd_Variavel_Primeira = T.Cd_Banco
       And Cd_Variavel_Segunda  = T.Cd_Ag
       And Cd_Variavel_Terceira = T.Cd_Cta
       And qt_total < (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_qtTransacoesFuncionario')
      )

union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'F'
  And (FL_BNEFC_INSS = 1)
  And (
     Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlSignificativoBnefcINSS')
     Or
     (Cd_Natur_Oper = 1 And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlDepositoInusitadoBnefcINSS'))
    )
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 5
       And Cd_Variavel_Primeira = T.Cd_Banco
       And Cd_Variavel_Segunda  = T.Cd_Ag
       And Cd_Variavel_Terceira = T.Cd_Cta
       And qt_total < (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_qtTransacoesBnefcINSS')
      )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'J'
  And (
     Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlSignificativoPJ')
     Or
     (Cd_Natur_Oper = 1 And Vl_Oper > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_vlDepositoInusitadoPJ'))
    )
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 5
       And Cd_Variavel_Primeira = T.Cd_Banco
       And Cd_Variavel_Segunda  = T.Cd_Ag
       And Cd_Variavel_Terceira = T.Cd_Cta
       And Qt_Total < (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 54 And Nm_Campo_Param = 'pm_qtTransacoesPJ')
      )
) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 54

  

