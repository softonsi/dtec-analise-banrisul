select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_OPER = 28 /*visita cofre */
  and exists 
      (select 1 from TB_PERFIL_INFORMACAO a
       where a.CD_IDENTF_PERFIL = 16
         and a.CD_VARIAVEL_PRIMEIRA = t.CD_DOC_IDENTF_CLIE
         and a.CD_VARIAVEL_SEGUNDA  = t.CD_TP_IDENTF_CLIE
         and a.CD_VARIAVEL_TERCEIRA = 28
         and exists
             (select 1 from TB_PERFIL_INFORMACAO b
              where b.CD_IDENTF_PERFIL = 17
                and b.CD_VARIAVEL_PRIMEIRA = t.CD_DOC_IDENTF_CLIE
                and b.CD_VARIAVEL_SEGUNDA  = t.CD_TP_IDENTF_CLIE
                and b.CD_VARIAVEL_TERCEIRA = 28
                and a.QT_TOTAL > (b.QT_TOTAL - a.QT_TOTAL)
                having COUNT(*) >= 1)
        )       
     ) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 56