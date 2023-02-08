/* Contas PF, exceto FOPA, com >= saques no dia*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER   = 7
  AND CD_TP_PESSOA = 'F'
  AND CD_TP_CTA   <> 9 
  AND CD_LOTE = 2014091801 
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_INFORMACAO
       WHERE CD_IDENTF_PERFIL = 15
         AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
         AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
         AND QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QSaqueDia' and cd_regra = 25 and cd_versao_sistema = 3)
        )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 25




  				