/* Regra 24 */
Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote       = 2014091801 and cd_sublote = 1
  And cd_natur_oper = 1
  And Cd_Tp_Oper    = 4
  And Cd_Forma_Oper = 7  
  And Exists (Select 1 From Tb_Agencia  Where Cd_Ag = T.Cd_Ag_Oper and fl_reg_crime = 1)  
  And Exists
   (Select 1 From Tb_TRANS_ANLSE
    Where CD_DOC_IDENTF_CLIE = T.CD_DOC_IDENTF_CLIE
      AND CD_TP_IDENTF_CLIE  = T.CD_TP_IDENTF_CLIE
      AND CD_NATUR_OPER      = 1
      AND CD_TP_OPER         = 4
      AND CD_FORMA_OPER      = 7
      AND (( 
              CD_BANCO = T.Cd_Banco
          And CD_AG    = T.Cd_Ag
          And CD_CTA   = T.Cd_Cta)
          OR
          Exists (Select 1 From Tb_Agencia  Where Cd_Ag = T.Cd_Ag_Oper and fl_reg_crime = 1)  
          )          
      HAVING SUM(VL_OPER) > (Select Vl_Param From Tb_Regra_Parametro Where Cd_Regra = 24 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_VlAltoDepEspecie')  
    )	 	
) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 24 And Cd_Versao_Sistema = 3;

