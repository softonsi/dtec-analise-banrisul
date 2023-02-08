/* Contas PF com saldo >= x, no dia. */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_LOTE = 2014091801 
  AND VL_SLD_INIC >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VSaldoDia' and cd_regra = 26 and cd_versao_sistema = 3)       
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 26




  				