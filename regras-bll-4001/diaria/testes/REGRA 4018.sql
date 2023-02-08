/*DIARIA*/
SELECT last_day(trunc(t.dt_trans)) DT_APONTAMENTO, T.CD_DOC_IDENTF_CLIE,T.CD_TP_IDENTF_CLIE,    
(SELECT 'An�lise: Regra ' || TO_CHAR(CD_REGRA) || ' - ' || NM_REGRA || '| Objetivo: ' || DS_REGRA || '|' FROM TB_REGRA WHERE CD_REGRA = 4018 AND CD_VERSAO_SISTEMA = 4) ||
'Cliente: ' ||  coalesce(T.NM_CLIE, 'Nome n�o informado') || '|' ||  
CASE T.CD_TP_IDENTF_CLIE   
	WHEN 2 THEN 'Tipo de Pessoa F�sica (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)   
	WHEN 3 THEN 'Tipo de Pessoa Jur�dica (CNPJ): ' ||SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)    
	ELSE T.CD_DOC_IDENTF_CLIE   
END   
|| '|' ||    
'Lote: ' || TO_CHAR(T.CD_LOTE)  || '|' || 
'Data da transa��o:' || TO_CHAR(DT_TRANS) || '|' ||
'Valor da transa��o: '        || COALESCE(TO_CHAR(VL_OPER, 'L999G999G999G990D99'         ),'NULO') || '|' ||
'Valor da moeda: '            || COALESCE(TO_CHAR(VL_MOEDA, 'L999G999G999G990D99'        ),'NULO') || '|' ||
'Valor de mercado da moeda: ' || COALESCE(TO_CHAR(VL_MOEDA_MERCADO, 'L999G999G999G990D99'),'NULO') || '|' ||
'Tipo de opera��o: '          || COALESCE((SELECT NM_TP_OPER FROM TB_TP_OPER O WHERE O.CD_TP_OPER = T.CD_TP_OPER), TO_CHAR(T.CD_TP_OPER)) || '|' ||
CASE CD_TP_PESSOA WHEN 'F' THEN

COALESCE((SELECT DS_CAMPO_PARAM || ' (' || NM_CAMPO_PARAM || '):' || (SELECT TO_CHAR(:pm_ValorMinimoPF, 'L999G999G999G990D99') FROM DUAL)  FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4018 AND CD_VERSAO_SISTEMA = 4 AND NM_CAMPO_PARAM = 'pm_ValorMinimoPF'),'Par�metro n�o cadastrado|' ) || '|' ||
COALESCE((SELECT DS_CAMPO_PARAM || ' (' || NM_CAMPO_PARAM || '):' || (SELECT TO_CHAR(:pm_PercMoedaMercadoPF) FROM DUAL)  FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4018 AND CD_VERSAO_SISTEMA = 4 AND NM_CAMPO_PARAM = 'pm_PercMoedaMercadoPF'),'Par�metro n�o cadastrado|' ) || '|' 
ELSE
COALESCE((SELECT DS_CAMPO_PARAM || ' (' || NM_CAMPO_PARAM || '):' || (SELECT TO_CHAR(:pm_ValorMinimoPF, 'L999G999G999G990D99') FROM DUAL)  FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4018 AND CD_VERSAO_SISTEMA = 4 AND NM_CAMPO_PARAM = 'pm_ValorMinimoPJ'),'Par�metro n�o cadastrado|' ) || '|' ||
COALESCE((SELECT DS_CAMPO_PARAM || ' (' || NM_CAMPO_PARAM || '):' || (SELECT TO_CHAR(:pm_PercMoedaMercadoPF) FROM DUAL)  FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 4018 AND CD_VERSAO_SISTEMA = 4 AND NM_CAMPO_PARAM = 'pm_PercMoedaMercadoPJ'),'Par�metro n�o cadastrado|' ) || '|' 
END
AS DS_INF_ANLSE  
FROM
TB_TRANS_ANLSE T
WHERE CD_LOTE = :cd_lote AND CD_SUBLOTE = :cd_sublote
  AND CD_TP_OPER in (
           2,  /*Opera��o de cambio */
          88, /*Opera��o de com�rcio internacional */
          89, /*Pagamento de importa��o */
          90, /*Recebimento de exporta��o */
          92, /*Compra de moeda estrangeira */
          93)  /*Venda de moeda estrangeira */
  AND (
      (CD_TP_PESSOA = 'F' AND
       VL_MOEDA IS NOT NULL AND VL_MOEDA_MERCADO IS NOT NULL AND
       VL_OPER >= (:pm_ValorMinimoPF) 
       AND
         (VL_MOEDA >= (VL_MOEDA_MERCADO * (:pm_PercMoedaMercadoPF/100))
          OR
         (VL_MOEDA IS NULL AND VL_MOEDA_MERCADO IS NULL )          
       ))
       OR
      (CD_TP_PESSOA = 'J' AND
       VL_MOEDA IS NOT NULL AND VL_MOEDA_MERCADO IS NOT NULL AND      
       VL_OPER >= (:pm_ValorMinimoPJ) 
       AND (
         VL_MOEDA >= (VL_MOEDA_MERCADO * (:pm_PercMoedaMercadoPJ/100))       
         OR 
         (VL_MOEDA IS NULL AND VL_MOEDA_MERCADO IS NULL )
       ))
       )
   
   

