select count(*) as total from
(SELECT T.cd_doc_identf_clie, t.cd_tp_identf_clie FROM TB_TRANS_ANLSE T, TB_PERFIL_MES_CALENDARIO P
WHERE P.CD_IDENTF_PERFIL = 8
  AND P.CD_VARIAVEL_PRIMEIRA = t.cd_doc_identf_clie
  AND P.CD_VARIAVEL_SEGUNDA  = t.cd_tp_identf_clie
  AND P.vl_total >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 8 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlCons_CataoPPago')
  AND P.vl_total >= (t.vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 8 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_perc_CartaoPPago'))
  AND T.CD_LOTE = 2014091801
group by T.cd_doc_identf_clie, t.cd_tp_identf_clie
having count(*) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 8 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtTiposDiferentes')); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 8