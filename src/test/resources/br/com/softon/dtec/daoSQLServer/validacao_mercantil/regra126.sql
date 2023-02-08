/* regra 126 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801

  and cd_natur_oper = 2
  and cd_doc_identf_destorig is not null
  and cd_doc_identf_destorig = cd_doc_identf_clie
  and cd_tp_identf_destorig  = cd_tp_identf_clie
  and cd_pais_destorig is not null
  and cd_pais_destorig <> 24
  and vl_oper is not null
  and (( vl_renda_fat is not null and 
         vl_oper > (vl_renda_fat * (select vl_param/100 from tb_regra_parametro where cd_regra = 126 and cd_versao_sistema = 3 and nm_campo_param = 'pm_percRendaFat')))
         or vl_renda_fat is null)
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 126;