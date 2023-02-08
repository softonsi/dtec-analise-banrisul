select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_OPER IN (4, 5, 8, 9)
  AND CD_LOTE = 2014091801
  AND EXISTS (
	select 1 from tb_ppe where cd_doc_identf = t.cd_doc_identf_destorig and cd_tp_identf = t.cd_tp_identf_destorig
  )
  AND EXISTS (
	select 1 from tb_trans_anlse 
		where cd_doc_identf_destorig = t.cd_doc_identf_destorig 
			and cd_tp_identf_destorig = t.cd_tp_identf_destorig
			and cd_transacao <> t.cd_transacao
			and cd_tp_oper in (4, 5, 8, 9)
		group by cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao
		having count(1) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 40 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtMovPPE')
  )
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 40