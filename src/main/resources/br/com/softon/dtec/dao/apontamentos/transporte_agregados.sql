MERGE into TB_DETLH_APONTAMENTO d
USING
(select distinct a.dt_apontamento, t.dt_apontamento dt_apontamento_subs, t.cd_lote cd_lote_subs, a.cd_doc_identf_clie, a.cd_tp_identf_clie, a.cd_lote
from tb_apontamento a
inner join 
   (select distinct dt_apontamento, cd_lote, cd_doc_identf_clie, cd_tp_identf_clie from tb_apontamento t  
   		where t.cd_sttus_evento_atual = 2 and to_char(t.dt_apontamento, 'YYYYMM') = ':cd_lote') t
    on t.cd_doc_identf_clie = a.cd_doc_identf_clie and t.cd_tp_identf_clie = a.cd_tp_identf_clie
 where  
((a.cd_sttus_evento_atual in (select cd_sttus_evento from tb_sttus_evento where fl_fim_invtga = 0))
   or a.cd_sttus_evento_atual = 0) 
  and a.cd_sttus_evento_atual <> 1
) a
on (a.cd_doc_identf_clie = d.cd_doc_identf_clie and a.cd_tp_identf_clie = d.cd_tp_identf_clie
) 
when matched then update set d.dt_apontamento = a.dt_apontamento, d.cd_lote_apontamento = a.cd_lote 
where d.dt_apontamento = a.dt_apontamento_subs and d.cd_lote_apontamento = a.cd_lote_subs; 

merge into tb_apontamento a
using (
SELECT DT_APONTAMENTO, CD_LOTE_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, COALESCE(SUM(VL_ACUM),0) + COALESCE(SUM(VL_NACUM),0) vl_apontamento
FROM (
SELECT D.DT_APONTAMENTO, D.CD_LOTE_APONTAMENTO, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA, 
CASE R.FL_ACUM_REGRA WHEN 1 THEN SUM(D.VL_PONTO) END VL_ACUM,
CASE R.FL_ACUM_REGRA WHEN 0 THEN 
     (SELECT MIN(VL_PONTO) FROM TB_DETLH_APONTAMENTO T WHERE T.DT_APONTAMENTO = D.DT_APONTAMENTO AND T.CD_LOTE_APONTAMENTO = D.CD_LOTE_APONTAMENTO AND T.CD_DOC_IDENTF_CLIE = D.CD_DOC_IDENTF_CLIE AND T.CD_TP_IDENTF_CLIE = D.CD_TP_IDENTF_CLIE AND T.CD_REGRA = D.CD_REGRA) END VL_NACUM
FROM TB_DETLH_APONTAMENTO D
INNER JOIN TB_REGRA R ON D.CD_REGRA = R.CD_REGRA
where exists 
    (select 1 from tb_apontamento t 
     where d.dt_apontamento      = t.dt_apontamento 
       and d.cd_lote_apontamento = t.cd_lote
       and t.cd_doc_identf_clie  = d.cd_doc_identf_clie 
       and t.cd_tp_identf_clie   = d.cd_tp_identf_clie
       and (t.cd_sttus_evento_atual in (select cd_sttus_evento from tb_sttus_evento where fl_fim_invtga = 0)
       or t.cd_sttus_evento_atual = 0 ) and t.cd_sttus_evento_atual <> 1 )
GROUP BY D.DT_APONTAMENTO, D.CD_LOTE_APONTAMENTO, D.CD_DOC_IDENTF_CLIE, D.CD_TP_IDENTF_CLIE, D.CD_REGRA, FL_ACUM_REGRA 
) GROUP BY DT_APONTAMENTO, CD_LOTE_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE) b
on (a.dt_apontamento = b.dt_apontamento and a.cd_lote = b.cd_lote_apontamento and a.cd_doc_identf_clie = b.cd_doc_identf_clie and a.cd_tp_identf_clie = b.cd_tp_identf_clie)
when matched then update
set a.vl_apontamento = case when b.vl_apontamento > 999 then 999 else b.vl_apontamento end,
a.dt_atualz_calculo = sysdate; 

delete from tb_evento a
where not exists 
(select 1 from tb_detlh_apontamento d
 where d.dt_apontamento     = a.dt_apontamento 
   and d.cd_doc_identf_clie = a.cd_doc_identf_clie 
   and d.cd_tp_identf_clie  = a.cd_tp_identf_clie 
   and d.cd_lote_apontamento = A.Cd_Lote_Apontamento); 

delete from tb_apontamento a
where Cd_Sttus_Evento_Atual = 2
and not exists 
(select 1 from tb_detlh_apontamento d
 where d.dt_apontamento      = a.dt_apontamento 
   and d.cd_lote_apontamento = a.cd_lote
   and d.cd_doc_identf_clie  = a.cd_doc_identf_clie 
   and d.cd_tp_identf_clie   = a.cd_tp_identf_clie );