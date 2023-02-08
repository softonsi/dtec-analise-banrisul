SELECT Cd_Transacao 
From Tb_Trans_Anlse t
Where Cd_Lote = ? and cd_sublote = ?
   And Cd_tp_Oper = 4
   AND (Abs(Dt_Abert_Cta - to_date(substr(dt_trans, 0, 9))) <= (SELECT Vl_Param 
                                                                FROM Tb_Regra_Parametro
                                                                WHERE Cd_Versao_Sistema = 3
                                                                  And Cd_Regra = 49
                                                                  And Nm_Campo_Param = 'pm_tpCurto'))
   And Exists
     (Select 1 From Tb_Trans_Anlse
      Where Cd_Tp_Oper = 4
        And T.Vl_Oper < (Vl_Oper * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 49 And Nm_Campo_Param = 'pm_percValor_Sup'))
        And T.Vl_Oper > (Vl_Oper * (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 49 And Nm_Campo_Param = 'pm_percValor_Inf'))      
        And t.cd_cta       <> cd_cta
        And T.Cd_Transacao <> Cd_Transacao
        And Dt_Abert_Cta Is Not Null
        And Dt_Abert_Cta >= (T.Dt_Abert_Cta + (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 49 And Nm_Campo_Param = 'pm_tpCurto'))
        And T.Dt_Abert_Cta < Dt_Abert_Cta        
        And (
          (Cd_Doc_Identf_Clie = T.Cd_Doc_Identf_Clie And Cd_Tp_Identf_Clie = T.Cd_Tp_Identf_Clie) or
          (Nm_Clie is not null          and Nm_Clie = T.Nm_Clie) Or
          (Nm_Movd is not null          and Nm_Movd = T.Nm_Movd) Or
          (Nm_Ender_Resid is not null   and Nm_Ender_Resid = T.Nm_Ender_Resid) Or
          (Cd_Fone_Clie is not null     and Cd_Fone_Clie   = T.Cd_Fone_Clie) Or
          (Cd_Fone_Cel_Clie is not null and Cd_Fone_Cel_Clie = T.Cd_Fone_Cel_Clie)
        ))
        
        
        