insert into tb_detlh_apontamento 
(
ID_DETLH_APONTAMENTO, 
CD_CLASSE_REGRA, 
CD_DOC_IDENTF_CLIE, 
CD_TP_IDENTF_CLIE, 
DS_INF_ANLSE, 
CD_LOTE, 
CD_REGRA, 
CD_VERSAO_SISTEMA, 
DT_APONTAMENTO, 
DT_DISP_REGRA, 
VL_PONTO)  
SELECT SQ_DETLH_APONT.NEXTVAL, 3, T.CD_DOC_IDENTF_CLIE, T.CD_TP_IDENTF_CLIE,    
'Análise: Regra 0 - Apontamentos sequenciais |' ||   
'Objetivo: Penalizar clientes com vários apontamentos sem análise em histórico |' ||   
'Conclusão: Cliente com vários apontamentos sem análise em histórico |' ||   
CASE T.CD_TP_IDENTF_CLIE   
	WHEN 2 THEN 'Tipo de Pessoa Física (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)    
	WHEN 3 THEN 'Tipo de Pessoa Jurídica (CNPJ): ' ||SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)     
	ELSE T.CD_DOC_IDENTF_CLIE   
END   
|| '|' ||   
'Mês/Ano: ' || SUBSTR(':cd_lote',5,2) || '/' || SUBSTR(':cd_lote',1,4) ||  '|' ||   
'Penalização: ' || TO_CHAR(penalizacao) || '|' || 
'Parâmetro que indica qtd de meses para a verificação dos apontamentos sequenciais sem análise (pm_QMesApontSeq): ' || COALESCE(TO_CHAR((select vl_param_real from tb_param_global where nm_campo_param = 'pm_QMesApontSeq') ,  '999999990'),'Parâmetro não cadastrado' )  || '|' || 
'Parâmetro que indica o valor da penalização, para cada apontamento sequencial sem análise (pm_VApontSeq): ' || COALESCE(TO_CHAR((select vl_param_real from tb_param_global where nm_campo_param = 'pm_VApontSeq') ,  '999999990D99'),'Parâmetro não cadastrado' )  || '|'  
AS DS_INF_ANLSE, :cd_lote, 0, 3, a.dt_apontamento, SYSTIMESTAMP, 0    
FROM  
(select  
	c.cd_doc_identf_clie,  
	c.cd_tp_identf_clie,  
	(count(1) * (select vl_param_real from tb_param_global where nm_campo_param = 'pm_VApontSeq')) as penalizacao  
from tb_apontamento_hist c 
inner join (SELECT distinct dt_apontamento, cd_doc_identf_clie, cd_tp_identf_clie from tb_apontamento where to_char(dt_apontamento, 'YYYYMM') = :cd_lote and cd_sttus_evento_atual = 0) a  
      on a.cd_doc_identf_clie = c.cd_doc_identf_clie and a.cd_tp_identf_clie = c.cd_tp_identf_clie  
where 	
	cd_sttus_evento_atual = 30 and 
	months_between(a.dt_apontamento, c.dt_apontamento) <= (select vl_param_real from tb_param_global where nm_campo_param = 'pm_QMesApontSeq') and  	
	exists 
	    (select 1 from 
	            (select cd_doc_identf_clie, cd_tp_identf_clie, max(dt_apontamento) apont_tt 
	             from tb_apontamento_hist 
	             where cd_sttus_evento_atual not in (1,2,30) 
	             and months_between(a.dt_apontamento, dt_apontamento) <= (select vl_param_real from tb_param_global where nm_campo_param = 'pm_QMesApontSeq') 
	             group by cd_doc_identf_clie, cd_tp_identf_clie 
	             ) h 
	      where c.dt_apontamento > h.apont_tt and h.cd_doc_identf_clie = c.cd_doc_identf_clie and h.cd_tp_identf_clie = c.cd_tp_identf_clie 
	      ) 
group by c.cd_doc_identf_clie, c.cd_tp_identf_clie 
Union 
Select c.cd_doc_identf_clie, c.cd_tp_identf_clie, Count(*) * (select vl_param_real from tb_param_global where nm_campo_param = 'pm_VApontSeq') Penalizacao  
From Tb_apontamento_Hist C  
inner join (SELECT distinct dt_apontamento, cd_doc_identf_clie, cd_tp_identf_clie from tb_apontamento where to_char(dt_apontamento, 'YYYYMM') = :cd_lote and cd_sttus_evento_atual = 0) a  
      on a.cd_doc_identf_clie = c.cd_doc_identf_clie and a.cd_tp_identf_clie = c.cd_tp_identf_clie  
Where c.Cd_Sttus_Evento_Atual = 30 
 And Months_Between(a.dt_apontamento, c.dt_apontamento) <= (select vl_param_real from tb_param_global where nm_campo_param = 'pm_QMesApontSeq')  
 and not Exists 
 (Select 1 From tb_apontamento_hist H 
   Where C.cd_doc_identf_clie = H.cd_doc_identf_clie 
     And C.cd_tp_identf_clie  = H.cd_tp_identf_clie 
     And H.Cd_Sttus_Evento_Atual not in (1,2,30) 
     And Months_Between(a.dt_apontamento, h.dt_apontamento) <= (select vl_param_real from tb_param_global where nm_campo_param = 'pm_QMesApontSeq'))  
Group By c.cd_doc_identf_clie, c.cd_tp_identf_clie) T 
inner join tb_apontamento a 
on a.cd_doc_identf_clie = T.cd_doc_identf_clie and a.cd_tp_identf_clie = T.cd_tp_identf_clie and to_char(a.dt_apontamento, 'YYYYMM') = :cd_lote 
where cd_sttus_evento_atual = 0 