/* Regra 22 */

Select Count(*) As Total From
(
Select * From Tb_Trans_Anlse T 
Where Cd_Lote 	   = 2014091801 and cd_sublote = 1
  And Cd_Natur_Oper = 2
  And Cd_Doc_Identf_Destorig Is Not Null
  And Cd_Doc_Identf_Clie <> Cd_Doc_Identf_Destorig
  And Exists
   (Select 1 From Tb_Trans_Anlse
      Where Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie
        And Cd_Tp_Identf_Clie  = T.Cd_Tp_Identf_Clie
        And Cd_Transacao <> T.Cd_Transacao      
        And Cd_Natur_Oper = 1 /*credito*/
        And Cd_Forma_Oper = 7 /*especie*/
        And Cd_Tp_Oper    = 4 /*deposito*/
        And Dt_Trans < T.Dt_Trans /*credito ocorreu antes da transferencia*/
      Having T.Vl_Oper > (Sum(Vl_Oper) * (1- (Select Vl_Param/100 From Tb_Regra_Parametro Where Nm_Campo_Param = 'pm_percDepTransf' And Cd_Regra = 22 And Cd_Versao_Sistema = 3 )))
             And
             T.Vl_Oper < (Sum(Vl_Oper) * (1+ (Select Vl_Param/100 From Tb_Regra_Parametro Where Nm_Campo_Param = 'pm_percDepTransf' And Cd_Regra = 22 And Cd_Versao_Sistema = 3 )))    
    )	 	
union
Select * From Tb_Trans_Anlse t
 Where Cd_Lote 	     = 2014091801 and cd_sublote = 1
   And Cd_Tp_Oper    = 4 /*deposito*/
   And Cd_Forma_Oper = 7 /*especie*/   
   And Exists 	/* Verifica se houve aumento substancial nos depositos em especie */
  (Select *
   From Tb_Perfil_Mes_Calendario c
   Where Cd_Identf_Perfil     = 9
	 And c.cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                        THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                        ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                        END
     And Cd_Variavel_Primeira = T.Cd_Doc_Identf_Clie
     And Cd_Variavel_Segunda  = T.Cd_tp_Identf_Clie
     And Cd_Variavel_Terceira = 4 /*deposito*/
     And Cd_Variavel_Quarta   = 7 /*especie*/         
     And Exists /*Aumento substancial no mês calendário, em relacao aos ultimos meses fechados*/
       (Select *
          From Tb_Perfil_Mes_Calendario
          Where Cd_Identf_Perfil     = 9
	        And cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                        THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                        ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                        END
            And Cd_Variavel_Primeira = c.Cd_Variavel_Primeira 
            And Cd_Variavel_Segunda  = c.Cd_Variavel_Segunda
            And Cd_Variavel_Terceira = 4 /*deposito*/
            And Cd_Variavel_Quarta   = 7 /*especie*/
            And Qt_Total < (C.Qt_Total * (Select Vl_Param/100 From Tb_Regra_Parametro Where Nm_Campo_Param = 'pm_percQtdDepEspecie' And Cd_Regra = 22 And Cd_Versao_Sistema = 3))
         )
    )
    
) S; 
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 22 And Cd_Versao_Sistema = 3;
