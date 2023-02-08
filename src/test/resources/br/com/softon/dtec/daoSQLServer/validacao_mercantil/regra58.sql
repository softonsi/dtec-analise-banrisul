/* regra 58 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where CD_LOTE = 2014091801 and cd_sublote = 1
  And cd_tp_oper is not null
  And cd_forma_oper is not null
  And Exists
  	(select 1 from Tb_Perfil_Mes_Calendario
  	 Where Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda  = T.Cd_Tp_Identf_Clie
       )
  And not Exists
    (Select 1 From Tb_Perfil_Mes_Calendario
     Where Cd_Identf_Perfil     = 9 /*perfil por cliente, tipo e forma, no mes calendario*/
       And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
       And Cd_Variavel_Segunda  = T.Cd_Tp_Identf_Clie
       And Cd_Variavel_Terceira = T.Cd_Tp_Oper
       And cd_variavel_quarta   = T.Cd_Forma_Oper
       and cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                        THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                        ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                       END
	        )         
      ) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 58

