select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_pessoa 		= 'J'
	and CD_LOTE 		= 2014091801
	and exists (
		select 1 from tb_transmissao_ordem to1 where t.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE and t.CD_TP_IDENTF_CLIE = CD_TP_IDENTF_CLIE 
			and (
				select count(1) from tb_transmissao_ordem to2
					where to1.CD_TP_IDENTF_REPRE = to2.CD_TP_IDENTF_REPRE
				 	  and to1.CD_DOC_IDENTF_REPRE = to2.CD_DOC_IDENTF_REPRE
			) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 46 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeRepresentacao')
	)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 46