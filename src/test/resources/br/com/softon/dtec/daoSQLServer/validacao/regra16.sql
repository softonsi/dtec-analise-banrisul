select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		= 4 
	and CD_LOTE 		= 2014091801
	and cd_Forma_Oper 	= 7 
	and vl_oper      	>= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlAltoDepEspecie')   
	and exists (
		select 1 from tb_agencia where cd_ag = t.cd_ag_oper and fl_reg_crime = 1
	)
	and (
		select count(1) from tb_trans_anlse t2
		where cd_Doc_Identf_clie = t.cd_Doc_Identf_clie 
	        and cd_Tp_Identf_clie  = t.cd_Tp_Identf_clie 
	        and cd_Tp_Oper         = 4 
	        and cd_Forma_Oper      = 7 
	        and vl_Oper            >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlAltoDepEspecie') 
	        and cd_Doc_Identf_destorig = t.cd_Doc_Identf_destorig
	        and cd_Tp_Identf_destorig  = t.cd_Tp_Identf_destorig	
	        and exists (
				select 1 from tb_agencia where cd_ag = t2.cd_ag_oper and fl_reg_crime = 1
	        	)
	) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdOcor') 
union
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_Oper 		= 4 
	and CD_LOTE 		= 2014091801
	and cd_Forma_Oper 	= 7 
	and vl_oper      	>= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlAltoDepEspecie')   
	and exists (
		select 1 from tb_agencia where cd_ag = t.cd_ag_oper and fl_reg_crime = 1
	)
	and (
		select count(1) from tb_trans_anlse t2
		where  cd_Doc_Identf_clie = t.cd_Doc_Identf_clie 
	        and cd_Tp_Identf_clie  = t.cd_Tp_Identf_clie 
	        and cd_Tp_Oper         = 4 
	        and cd_Forma_Oper      = 7 
	        and vl_Oper            >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlAltoDepEspecie') 
	        and cd_cta_destorig <> t.cd_cta_destorig
	        and ( cd_ag_destorig <> t.cd_ag_destorig or sg_uf_ag_destorig <> t.sg_uf_ag_destorig )	
	        and exists (
				select 1 from tb_agencia where cd_ag = t2.cd_ag_oper and fl_reg_crime = 1
	        	)
	) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 16 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdOcor') 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 16