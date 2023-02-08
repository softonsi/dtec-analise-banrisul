
/* regra 63 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  and cd_tp_pessoa      = 'F'
  and cd_natur_oper     = 1
  and cd_tp_oper        = 4
  and vl_oper           is not null
  and sg_uf_ag_destorig is not null
  and sg_uf_resid       is not null  
  and Exists
    (Select 1 From Tb_Trans_Anlse
     Where Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
       and Cd_Tp_Identf_Clie  = T.Cd_Tp_Identf_Clie
       and Cd_Transacao      <> T.Cd_Transacao
       and vl_oper           is not null
       And Sg_Uf_Ag_Destorig Is Not Null
       and sg_uf_resid       is not null	   
       and cd_natur_oper     = 1
       and cd_tp_oper        = 4
	   and sg_uf_ag_destorig <> t.sg_uf_ag_destorig
	   and sg_uf_ag_destorig <> t.sg_uf_resid
	   and ((vl_renda_fat IS NOT NULL AND vl_oper > (vl_renda_fat * (Select Vl_Param/100  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 63 And Nm_Campo_Param = 'pm_percRenda')))
	        OR
	        vl_renda_fat IS NULL)
       having sum(vl_oper) > (Select Vl_Param  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 63 And Nm_Campo_Param = 'pm_vlConsPF')
      )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  and cd_tp_pessoa      = 'J'
  and cd_natur_oper     = 1
  and cd_tp_oper        = 4
  and vl_oper           is not null
  and sg_uf_ag_destorig is not null
  And Sg_Uf_Resid       Is Not Null  
  and Exists
    (Select 1 From Tb_Trans_Anlse
     Where Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
       and Cd_Tp_Identf_Clie  = T.Cd_Tp_Identf_Clie
       and Cd_Transacao      <> T.Cd_Transacao
       and vl_oper           is not null
       And Sg_Uf_Ag_Destorig Is Not Null
       and sg_uf_resid       is not null	   
       and cd_natur_oper     = 1
       and cd_tp_oper        = 4
	   and sg_uf_ag_destorig <> t.sg_uf_ag_destorig
	   and sg_uf_ag_destorig <> t.sg_uf_resid
	   and ((vl_renda_fat is not null and vl_oper > (vl_renda_fat * (Select Vl_Param/100  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 63 And Nm_Campo_Param = 'pm_percFaturamento')))
	        or
	        vl_renda_fat is null)
       having sum(vl_oper) > (Select Vl_Param  From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 63 And Nm_Campo_Param = 'pm_vlConsPJ')
      )	  
) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 63

