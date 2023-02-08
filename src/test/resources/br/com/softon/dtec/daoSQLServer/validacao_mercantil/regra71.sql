Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801
  And Cd_tp_oper in (35, 36, 37, 38)  /*Compra títulos, Venda títulos, Compra imóvel, Venda Imóvel*/  
  And vl_oper is not null
  And Exists
    (Select 1 From Tb_Produto
     Where cd_produto = t.cd_produto
	   and vl_mercado is not null
	   and t.vl_oper > (vl_mercado * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 71 And Nm_Campo_Param = 'pm_percVlMercado'))
     )
union
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801
  And Cd_tp_oper in (35, 36, 37, 38)  /*Compra títulos, Venda títulos, Compra imóvel, Venda Imóvel*/    
  And Exists
     (Select 1 From Tb_Perfil_Mes_Calendario
      Where Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
        And Cd_Variavel_Segunda  = T.Cd_tp_identf_clie )
  And not Exists
     (Select 1 From Tb_Perfil_Mes_Calendario
      Where cd_identf_perfil = 9
	    And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
        And Cd_Variavel_Segunda  = T.Cd_tp_identf_clie
        And Cd_Variavel_Terceira = T.Cd_Tp_Oper
        And cd_ano_mes <> CASE WHEN MONTH(t.dt_trans) < 10
                               THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                               ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                          END
	   )
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 71