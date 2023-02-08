
/* regra 61 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Natur_Oper = 2 /*debito*/
  And Cd_Doc_Identf_Destorig Is Not Null
  And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig /*transferencia para terceiros*/
  And Exists
    (Select 1 From Tb_Trans_Anlse
     Where Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
       And Cd_Tp_Identf_Clie  = T.Cd_Tp_Identf_Clie
       And CD_LOTE = 2014091801 and cd_sublote = 1
       And Cd_Transacao      <> T.Cd_Transacao
       And Cd_Natur_Oper      = 2 /*debito*/      
       And Cd_Doc_Identf_Destorig Is Not Null
       And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig /*transferencia para terceiros*/
       having sum(vl_oper) > (Select Vl_Param  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 61 And Nm_Campo_Param = 'pm_ValorTerceiros')
      )
UNION
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And Cd_Natur_Oper = 1
  And Cd_Doc_Identf_Destorig Is Not Null
  And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig /*transferencia para terceiros*/
  And Exists
    (Select 1 From Tb_Trans_Anlse
     Where Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
       And Cd_Tp_Identf_Clie  = T.Cd_Tp_Identf_Clie
       And CD_LOTE = 2014091801 and cd_sublote = 1
       And Cd_Transacao      <> T.Cd_Transacao
       And Cd_Natur_Oper      = 1
       And Cd_Doc_Identf_Destorig Is Not Null
       And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig /*transferencia para terceiros*/
       having sum(vl_oper) > (Select Vl_Param  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 61 And Nm_Campo_Param = 'pm_ValorTerceiros')
      )      
) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 61

  