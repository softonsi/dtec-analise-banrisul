select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
From Tb_Trans_Anlse T1
Where Cd_Lote = 2014091801 
  And Cd_Doc_Identf_Destorig Is Not Null
  And Exists
   (Select 1 From Tb_Trans_Anlse T2
    Where Cd_Doc_Identf_Clie = T1.Cd_Doc_Identf_Clie
      And Cd_Tp_Identf_Clie  = T1.Cd_Tp_Identf_Clie
      and Cd_Lote            = 2014091801 
      And Cd_Transacao      <> T1.Cd_Transacao
      And Cd_Doc_Identf_Destorig Is Not Null
      And Exists 
        (Select 1 From Tb_Ppe 
         Where Cd_Doc_Identf = T2.Cd_Doc_Identf_Destorig
           And Cd_Tp_Identf  = T2.Cd_Tp_Identf_Destorig)
     Having Count(*) >= (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 68 And Nm_Campo_Param = 'pm_QtMovPPE')
  )
   
UNION
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_DOC_IDENTF_BNEFC IS NOT NULL
  And Cd_Tp_Identf_Bnefc Is Not Null
  AND CD_CONTRATO IS NOT NULL
  And Exists (
   	Select 1 From Tb_Ppe
	  Where	Cd_Doc_Identf = T.Cd_Doc_Identf_Bnefc
	  	And Cd_Tp_Identf = T.Cd_Tp_Identf_Bnefc
     )
) s; 
Select Count(1) As Total From Tb_Detlh_Apontamento Where Cd_Lote = 2014091801 And Cd_Regra = 68
