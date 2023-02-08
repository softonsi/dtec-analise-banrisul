/*DIARIA*/
SELECT last_day(trunc(T.dt_trans)) DT_APONTAMENTO, T.CD_DOC_IDENTF_CLIE,T.CD_TP_IDENTF_CLIE,    
(SELECT 'Análise: Regra ' || TO_CHAR(CD_REGRA) || ' - ' || NM_REGRA || '| Objetivo: ' || DS_REGRA || '|' FROM TB_REGRA WHERE CD_REGRA = 4019 AND CD_VERSAO_SISTEMA = 4) ||
'Cliente: ' ||  coalesce(T.NM_CLIE, 'Nome não informado') || '|' ||  
CASE T.CD_TP_IDENTF_CLIE   
	WHEN 2 THEN 'Tipo de Pessoa Física (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)   
	WHEN 3 THEN 'Tipo de Pessoa Jurídica (CNPJ): ' ||SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)    
	ELSE T.CD_DOC_IDENTF_CLIE   
END   
|| '|' ||    
'Lote: ' || TO_CHAR(T.CD_LOTE)  || '|' || 
'Data da transação:'          || TO_CHAR(DT_TRANS) || '|' ||
'Valor da transação: '        || COALESCE(TO_CHAR(VL_OPER, 'L999G999G999G990D99'         ),'NULO') || '|' ||
'Valor da moeda: '            || COALESCE(TO_CHAR(VL_MOEDA, 'L999G999G999G990D99'        ),'NULO') || '|' ||
'Valor de mercado da moeda: ' || COALESCE(TO_CHAR(VL_MOEDA_MERCADO, 'L999G999G999G990D99'),'NULO') || '|' ||
'Tipo de operação: '          || COALESCE((SELECT NM_TP_OPER FROM TB_TP_OPER O WHERE O.CD_TP_OPER = T.CD_TP_OPER), TO_CHAR(T.CD_TP_OPER)) || '|' 
AS DS_INF_ANLSE  
FROM
TB_TRANS_ANLSE T
WHERE CD_LOTE = :cd_lote AND CD_SUBLOTE = :cd_sublote
  AND CD_TP_OPER in (
           2,  /*Operação de cambio */
          92, /*Compra de moeda estrangeira */
          93)  /*Venda de moeda estrangeira */
  AND CD_FORMA_OPER = 8 /*moeda estrangeira*/
  AND FL_CEDULA_DANIFICADA = 1
 