/* regra 120 */
Select Count(*) As Total From
( 
Select Cd_Transacao From Tb_Trans_Anlse T
Where Cd_Lote = 2014091801
  And cd_pais_destorig is not null  
  And Exists
    (Select 1 From tb_pais
     where cd_pais = t.cd_pais_destorig
	   and fl_susp = 1
      )
) S;
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 120;




