
/* regra 33 */
Select Count(*) As Total From
( 
Select distinct Cd_Transacao 
From Tb_Trans_Anlse T1
 Where T1.CD_LOTE = 2014091801 and cd_sublote = 1
   And T1.Cd_Tp_Pessoa = 'F'
   And T1.Cd_Forma_Oper = 8 
   And T1.Nm_Ender_Resid Is Not Null
   And T1.Cd_Cep_Resid is not null   
   And Exists
     (Select Count(*) From Tb_Trans_Anlse T2
      Where T2.Cd_Tp_Pessoa = 'F'
        And T2.Cd_Forma_Oper In (8, 4)
        And T2.Cd_Transacao <> T1.Cd_Transacao
        And T2.Cd_Doc_Identf_Clie <> T1.Cd_Doc_Identf_Clie
        And T2.Nm_Ender_Resid Is Not Null
        And T2.cd_cep_Resid Is Not Null
        And T2.Nm_Ender_Resid = T1.Nm_Ender_Resid
        And T2.cd_cep_Resid = T1.cd_cep_Resid        
        And not (
          (T2.Nm_Mae Is Not Null       And T2.Nm_Mae = T1.Nm_Mae) or
          (T2.Nm_Pai Is Not Null       And T2.Nm_Pai = T1.Nm_Pai) or
          (T2.Nm_Conjuge Is Not Null   And T2.Nm_Clie Is Not Null  and
          (T1.Nm_clie = T2.nm_conjuge or T1.Nm_Conjuge = T2.nm_clie))
          )     
        having count(*) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Regra = 33 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_QtdeOcorrencia')
        
      )   
 ) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 33