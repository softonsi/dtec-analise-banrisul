select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
Where Cd_Lote = 2014091801 
  And Cd_Tp_Oper = 6
  And Vl_Oper Is Not Null
  And Vl_Pcela_Contrato Is Not Null
  AND VL_RENDA_FAT IS NOT NULL
  and vl_oper > vl_pcela_contrato
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 90 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat') )
  AND DT_LIQUID IS NOT NULL
  And Cd_Pais_Oper = 1058
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
Where Cd_Lote = 2014091801 
  And Cd_Tp_Oper   = 6
  And Cd_Contrato  Is Not Null  
  And Vl_Oper      Is Not Null
  And Vl_Renda_Fat Is Not Null
  And Cd_Pais_Oper = 1058 
  And EXISTS 
 (Select 1 From Tb_Trans_Anlse
   Where Cd_Contrato        = T.Cd_Contrato
     And Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
     and cd_tp_identf_clie  = T.Cd_Tp_Identf_Clie
     And Cd_Tp_Oper = 6
     And Vl_Oper Is Not Null
     And Cd_Pais_Oper = 1058
     group by cd_doc_identf_clie, cd_tp_identf_clie
     having sum(vl_oper) > (t.vl_renda_fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where cd_regra = 90 and Nm_Campo_Param = 'pm_percRendaFat'))
  )
) S;   
Select Count(1) As Total From Tb_Detlh_Apontamento Where Cd_Lote = 2014091801 And Cd_Regra = 90