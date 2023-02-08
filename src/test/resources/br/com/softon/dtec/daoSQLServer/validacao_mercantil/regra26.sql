/* Regra 26 */
Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		  = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa  = 'J'
  And Cd_Natur_Oper = 1 /*credito*/
  And Cd_Tp_Oper    = 4 /*deposito*/
  And Cd_Forma_Oper = 7 /*especie*/  
  AND CD_RAMO_ATIVID IN (SELECT * from splitstring((select DS_PARAM from tb_regra_parametro where cd_regra = 26 and cd_versao_sistema = 3 and NM_CAMPO_PARAM = 'pm_Lista_Setores')))
union
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 		  = 2014091801 and cd_sublote = 1
  And Cd_Tp_Pessoa  = 'F'
  And Cd_Natur_Oper = 1 /*credito*/
  And Cd_Tp_Oper    = 4 /*deposito*/
  And Cd_Forma_Oper = 7 /*especie*/ 
  AND EXISTS 
    (SELECT 1 FROM TB_CLIE_RENDA
      WHERE T.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE
        AND T.CD_TP_IDENTF_CLIE  = CD_TP_IDENTF_CLIE
        AND CD_OCUP IN (SELECT * from splitstring((select DS_PARAM from tb_regra_parametro where cd_regra = 26 and cd_versao_sistema = 3 and NM_CAMPO_PARAM = 'pm_lista_Ocup')))
        )
  
) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 26 And Cd_Versao_Sistema = 3;

