/* Contas PF com cargas por outros canais, cujo valor é >= R$ x, individual.*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER    = 26
  AND CD_VIA_OPER NOT IN (1,2)
  AND CD_TP_PESSOA = 'F'
  AND VL_OPER >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VCarga' and cd_regra = 21 and cd_versao_sistema = 3)
  AND CD_LOTE = 2014091801 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 21



  				