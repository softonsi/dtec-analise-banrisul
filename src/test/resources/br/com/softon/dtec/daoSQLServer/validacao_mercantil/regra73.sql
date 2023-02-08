Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote    = 2014091801
  And Cd_tp_oper = 34 /*investimento*/
  And vl_oper    is not null
  
  And Exists
    (Select 1 From Tb_Produto
     Where cd_produto          = t.cd_produto
	   and vl_med_invest is not null
	   and t.vl_oper           > (vl_med_invest * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 73 And Nm_Campo_Param = 'pm_percInvestimento'))
	   and fl_bx_rentabilidade = 1
	   and fl_bx_liquidez      = 1
      )
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 73