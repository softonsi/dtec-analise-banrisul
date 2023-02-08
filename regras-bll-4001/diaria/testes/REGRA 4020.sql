/*DIARIA*/

SELECT DISTINCT last_day(trunc(T.DT_TRANS)) DT_APONTAMENTO, T.CD_DOC_IDENTF_CLIE,T.CD_TP_IDENTF_CLIE,  
(SELECT 'Análise: Regra ' || TO_CHAR(CD_REGRA) || ' - ' || NM_REGRA || '| Objetivo: ' || DS_REGRA || '|' FROM TB_REGRA WHERE CD_REGRA = 4020 AND CD_VERSAO_SISTEMA = 4) ||
'Cliente: ' ||  coalesce(T.NM_CLIE, 'Nome não informado') || '|' ||  
CASE T.CD_TP_IDENTF_CLIE   
	WHEN 2 THEN 'Tipo de Pessoa Física (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)   
	WHEN 3 THEN 'Tipo de Pessoa Jurídica (CNPJ): ' ||SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)    
	ELSE T.CD_DOC_IDENTF_CLIE   
END   
|| '|' ||    
'Lote: ' || TO_CHAR(T.CD_LOTE)  || '|' || 
'Data da transação:' || TO_CHAR(T.DT_TRANS) || '|' ||
'Valor da transação: '        || COALESCE(TO_CHAR(T.VL_OPER, 'L999G999G999G990D99'         ),'NULO') || '|' ||
'Valor da moeda: '            || COALESCE(TO_CHAR(T.VL_MOEDA, 'L999G999G999G990D99'        ),'NULO') || '|' ||
'Valor de mercado da moeda: ' || COALESCE(TO_CHAR(T.VL_MOEDA_MERCADO, 'L999G999G999G990D99'),'NULO') || '|' ||
'Tipo de operação: '          || COALESCE((SELECT NM_TP_OPER FROM TB_TP_OPER O WHERE O.CD_TP_OPER = T.CD_TP_OPER), TO_CHAR(T.CD_TP_OPER)) || '|' ||
CASE T.CD_TP_PESSOA WHEN 'F' THEN
'Ocupação: ' || COALESCE((SELECT O.NM_OCUP FROM TB_OCUP O WHERE O.CD_OCUP = C.CD_OCUP), 'Ocupação não informada') || '|'
ELSE
'Ramo de atividade: ' || COALESCE((SELECT O.NM_RAMO_ATIVID FROM TB_RAMO_ATIVID O WHERE O.CD_RAMO_ATIVID = T.CD_RAMO_ATIVID), 'Ramo de atividade não informada') || '|'
END
AS DS_INF_ANLSE  
FROM
TB_TRANS_ANLSE T
LEFT JOIN TB_CLIE_RENDA C ON T.CD_DOC_IDENTF_CLIE = C.CD_DOC_IDENTF_CLIE AND T.CD_TP_IDENTF_CLIE = C.CD_TP_IDENTF_CLIE
WHERE T.CD_LOTE = :cd_lote AND T.CD_SUBLOTE = :cd_sublote
  AND T.CD_TP_OPER in (
           2,  /*Operação de cambio */
          88, /*Operação de comércio internacional */
          89, /*Pagamento de importação */
          90, /*Recebimento de exportação */
          92, /*Compra de moeda estrangeira */
          93)  /*Venda de moeda estrangeira */
  AND (
      (T.CD_TP_PESSOA = 'F' AND
       C.CD_OCUP IN (SELECT CD_OCUP FROM TB_OCUP O WHERE O.CD_OCUP = C.CD_OCUP AND FL_MOEDA_ESTRG = 1)
       )
       OR
      (T.CD_TP_PESSOA = 'J' AND
       T.CD_RAMO_ATIVID IN (SELECT CD_RAMO_ATIVID FROM TB_RAMO_ATIVID R WHERE R.CD_RAMO_ATIVID = T.CD_RAMO_ATIVID AND FL_MOEDA_ESTRG = 1)
       )
      )
   
