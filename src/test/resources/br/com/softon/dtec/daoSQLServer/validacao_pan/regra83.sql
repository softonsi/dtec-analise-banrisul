select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and VL_OPER is not null  
  and CD_TP_CARTAO = 3 /*cartao pre-pago*/
  and (CD_FORMA_OPER in (11,12) or CD_TP_OPER in (25,26))
  and CD_CARTAO is not null  
  and CD_PAIS_OPER is not null
  and SG_UF_AG_OPER is not null
  and exists 
      (select 1 from TB_PERFIL_MES_CALENDARIO a
       where CD_IDENTF_PERFIL = 13
         and CD_VARIAVEL_PRIMEIRA = t.CD_CARTAO
         and CD_VARIAVEL_SEGUNDA =  t.CD_PAIS_OPER
         and CD_VARIAVEL_TERCEIRA = t.SG_UF_AG_OPER
         and CD_ANO_MES <> CONVERT(VARCHAR(6),GETDATE(),112)
         and VL_MEDIO_DIARIO is not null
         and t.VL_OPER > (VL_MEDIO_DIARIO * (select vl_param/100 from TB_REGRA_PARAMETRO where CD_REGRA = 83 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_percPerfil') )         
        ) 
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_CARTAO = 3 /*cartao pre-pago*/
  and (CD_FORMA_OPER in (11,12) or CD_TP_OPER in (25,26))
  and CD_CARTAO is not null  
  and CD_PAIS_OPER is not null
  and SG_UF_AG_OPER is not null
  and exists 
      (select 1 from TB_PERFIL_MES_CALENDARIO a
       where CD_IDENTF_PERFIL = 13
         and CD_VARIAVEL_PRIMEIRA = t.CD_CARTAO
         and CD_VARIAVEL_SEGUNDA =  t.CD_PAIS_OPER
         and CD_VARIAVEL_TERCEIRA = t.SG_UF_AG_OPER
         and CD_ANO_MES <> CONVERT(VARCHAR(6),GETDATE(),112)        
        )         
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 83