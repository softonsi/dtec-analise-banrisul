Select Count(*) As Total From
( 
/* regra 30 */
Select cd_transacao From Tb_Trans_Anlse 
Where Cd_Tp_Pessoa = 'F'
 And Cd_Forma_Oper = 8
 And Vl_Oper Is Not Null
 And ((Vl_Renda_Fat Is Not Null
        And Vl_Oper > (Vl_Renda_Fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 30 And Nm_Campo_Param = 'pm_percRenda')))
        or
        Vl_Renda_Fat Is Null)
union   
SELECT T.CD_TRANSACAO 
From Tb_Trans_Anlse T, Tb_Clie_Renda R, Tb_Med_Ocup M
 Where T.CD_LOTE = 2014091801 and cd_sublote = 1
   And T.Cd_Tp_Pessoa = 'F'
   And T.Cd_Forma_Oper = 8
   AND T.CD_DOC_IDENTF_CLIE = R.CD_DOC_IDENTF_CLIE
   And T.Cd_Tp_Identf_Clie = R.Cd_Tp_Identf_Clie
   And R.Cd_Ocup = M.Cd_Ocup
   And T.Vl_Oper > (M.Vl_Med_Credito * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Regra = 30 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_percOcupacao') )  
Union
Select Cd_transacao From Tb_Trans_Anlse 
Where Cd_Tp_Pessoa = 'J'
 And Cd_Forma_Oper = 8
 And Vl_Oper Is Not Null
 And ((Vl_Renda_Fat Is Not Null
       And Vl_Oper > (Vl_Renda_Fat * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 30 And Nm_Campo_Param = 'pm_percFat')))
       or 
       Vl_Renda_Fat Is Null)
union      
Select T.Cd_Transacao 
From Tb_Trans_Anlse T, Tb_Med_Ramo_Ativid M
 Where T.CD_LOTE = 2014091801 and cd_sublote = 1
   And T.Cd_Tp_Pessoa = 'J'
   And T.Cd_Forma_Oper = 8
   And T.Cd_Ramo_Ativid = M.Cd_Ramo_Ativid
   And T.Vl_Oper > (M.Vl_Med_Credito * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Regra = 30 And Cd_Versao_Sistema = 3 And Nm_Campo_Param = 'pm_percRamo_ativid') )  
 ) S;
select count(1) as total from tb_detlh_apontamento where CD_LOTE = 2014091801 and cd_regra = 30





