/* Troca de endereço por diferentes canais*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER = 62
  AND CD_LOTE    = 2014091801 
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_INFORMACAO
        WHERE CD_IDENTF_PERFIL = 13
          AND CD_DOC_IDENTF_CLIE = T.CD_DOC_IDENTF_CLIE
          AND CD_TP_IDENTF_CLIE  = T.CD_TP_IDENTF_CLIE            	
          HAVING COUNT(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QTrocaEndereco' and cd_regra = 19 and cd_versao_sistema = 3)
       )
 ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 19


				 

  				