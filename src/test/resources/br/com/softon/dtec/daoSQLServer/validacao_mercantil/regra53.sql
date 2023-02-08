/* regra 53 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'F'
  And Cd_Natur_Oper = 1 
  And qt_cta_clie > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 53 And Nm_Campo_Param = 'pm_NumerosasCtasPF')
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 1
       And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda  = T.Cd_Tp_Identf_Clie
       And Cd_Variavel_Terceira = 1
       having sum(vl_total) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 53 And Nm_Campo_Param = 'pm_VlSignificativoPF')  
      )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa = 'J'
  And Cd_Natur_Oper = 1 
  And qt_cta_clie > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 53 And Nm_Campo_Param = 'pm_NumerosasCtasPJ')
  And Exists
    (Select 1 From Tb_Perfil_Informacao 
     Where Cd_Identf_Perfil = 1
       And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda  = T.Cd_Tp_Identf_Clie
       And Cd_Variavel_Terceira = 1
       having sum(vl_total) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 53 And Nm_Campo_Param = 'pm_VlSignificativoPJ')  
    )
) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 53