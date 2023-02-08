select count(*) as total from
(
SELECT CD_TRANSACAO, cd_cep_resid
FROM TB_TRANS_ANLSE t
Where Cd_Lote = 2014091801 
  And cd_tp_pessoa = 'J'
  And Cd_Tp_Identf_Clie = 3 
  And Nm_Ender_Resid Is Not Null 
  And cd_cep_resid is not null
  And Exists (
  	Select Count(1) From Tb_Cad_Clie Cad
   	Where Cd_Tp_Identf_Clie = 3 
   	  And cd_tp_pessoa = 'J'
	  AND SUBSTRING(CD_DOC_IDENTF_CLIE,1,8) <> SUBSTRING(t.CD_DOC_IDENTF_CLIE,1,8)   
  	  And Nm_Ender_Resid Is Not Null 
      And Cd_Cep_Resid Is Not Null
   	  And Nm_Ender_Resid = T.Nm_Ender_Resid
      And cd_cep_resid = t.cd_cep_resid
	  having count(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QtdeClie' and cd_regra = 45)
   )
) s; 
Select Count(1) As Total From Tb_Detlh_Apontamento Where Cd_Lote = 2014091801 And Cd_Regra = 45




