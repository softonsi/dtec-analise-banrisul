ALTER TABLE TB_DETLH_APONTAMENTO MODIFY CONSTRAINT "FkApontamento_DetlhApontamento" DISABLE; 


INSERT INTO TB_DETLH_APONTAMENTO (ID_DETLH_APONTAMENTO, DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_LOTE, CD_REGRA, DT_DISP_REGRA, CD_VERSAO_SISTEMA, CD_LOTE_APONTAMENTO, DS_INF_ANLSE)
SELECT SQ_DETLH_APONT.NEXTVAL, DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, 2020092201 CD_LOTE, 4090 CD_REGRA, SYSDATE DT_DISP_REGRA, 4 CD_VERSAO_SISTEMA, 2020092201 CD_LOTE_APONTAMENTO, DS_INF_ANLSE FROM (
/* BANRISUL - MENSAL */ 
SELECT T.CD_DOC_IDENTF_CLIE, T.CD_TP_IDENTF_CLIE,   
(SELECT 'Análise: Regra ' || TO_CHAR(CD_REGRA) || ' - ' || NM_REGRA || '| Objetivo: ' || DS_REGRA || '|' FROM TB_REGRA WHERE CD_REGRA = 4090 AND CD_VERSAO_SISTEMA = 4) ||  
'Cliente: ' ||  COALESCE(T.NM_CLIE, 'Nome não informado') || '|' || 
CASE T.CD_TP_IDENTF_CLIE  
	WHEN 2 THEN 'Tipo de Pessoa Física (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)  
	WHEN 3 THEN 'Tipo de Pessoa Jurídica (CNPJ): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)   
	ELSE T.CD_DOC_IDENTF_CLIE  
END  
|| '|' ||    
'Mês/Ano: ' || SUBSTR(T.CD_MES_ANO,5,2) || '/' || SUBSTR(T.CD_MES_ANO,1,4) ||  '|' ||   
'Contrato:' || COALESCE(T.CD_CONTRATO,'Não encontrada') ||  '|' ||
'Sistema de Origem: ' || COALESCE(T.NM_SIST_ORIG,'Não encontrada') ||  '|' ||
'Valor da Renda/Faturamento: ' || COALESCE(TO_CHAR(T.VL_RENDA_FAT, 'L999G999G999G990D99'),'Não encontrada') || '|' ||
'Valor consolidado de parcelas antecipadas no mês calendário: ' || COALESCE(TO_CHAR(T.VALOR_ANTECIPADO_MES, 'L999G999G999G990D99'),'Não encontrada') || '|' ||
'Quantidade de parcelas antecipadas no mês calendário:' || COALESCE(TO_CHAR(T.QT_PARCELA_ANTECIPADA),'Não encontrada') || '|' ||
'Quantidade de parcelas pagas:' || COALESCE(TO_CHAR(T.QT_MES_PAGO),'Não encontrada') || '|' ||
'Quantidade de parcelas contratadas: ' || COALESCE(TO_CHAR(T.QT_MES_CONTT),'Não encontrada') || '|' ||
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_CodGrupoProduto'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_CodGrupoProduto'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_CodGrupoProduto'))),'Parâmetro não cadastrado' ) || '|'   ||
CASE CD_TP_IDENTF_CLIE 
WHEN 2 THEN 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercRenda'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercRenda'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercRenda'), '999G999G999G990D99') || '%'),'Parâmetro não cadastrado' ) || '|' || 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_ValorMinimoPF'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_ValorMinimoPF'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_ValorMinimoPF'), 'L999G999G999G990D99')),'Parâmetro não cadastrado' ) || '|' || 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_QtdeMesesPagosPF'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_QtdeMesesPagosPF'')' || '):' || (select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_QtdeMesesPagosPF')),'Parâmetro não cadastrado' ) || '|' ||  
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercMesesFaltantesPF'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercMesesFaltantesPF'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercMesesFaltantesPF'), '999G999G999G990D99')  || '%'),'Parâmetro não cadastrado' ) || '|'    
WHEN 3 THEN 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercFaturamento'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercFaturamento'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercFaturamento'), '999G999G999G990D99') || '%'),'Parâmetro não cadastrado' ) || '|' || 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_ValorMinimoPJ'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_ValorMinimoPJ'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_ValorMinimoPJ'), 'L999G999G999G990D99')),'Parâmetro não cadastrado' ) || '|' || 
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_QtdeMesesPagosPJ'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_QtdeMesesPagosPJ'')' || '):' || (select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_QtdeMesesPagosPJ')),'Parâmetro não cadastrado' ) || '|' ||   
COALESCE(('(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercMesesFaltantesPJ'')' || ' (' || '(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = ''pm_PercMesesFaltantesPJ'')' || '):' || TO_CHAR((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercMesesFaltantesPJ'), '999G999G999G990D99')  || '%'),'Parâmetro não cadastrado' ) || '|'    
END
AS DS_INF_ANLSE 
FROM  (SELECT T.CD_MES_ANO, T.CD_TP_PESSOA, T.CD_CONTRATO, T.NM_SIST_ORIG, T.CD_DOC_IDENTF_CLIE, T.CD_TP_IDENTF_CLIE, T.QT_MES_CONTT,
       MAX(T.NM_CLIE) NM_CLIE, MAX(T.VL_RENDA_FAT) VL_RENDA_FAT, SUM(T.QT_PARCELA_ANTECIPADA) QT_PARCELA_ANTECIPADA, SUM(T.VL_OPER) VALOR_ANTECIPADO_MES, MAX(T.QT_MES_PAGO) QT_MES_PAGO      
       FROM TB_TRANS T    
	   WHERE T.DT_TRANS >=  TO_DATE(TO_CHAR(2020092201), 'yyyy/mm')  
       AND   T.DT_TRANS <   ADD_MONTHS((TO_DATE(TO_CHAR(2020092201), 'yyyy/mm')), 1)  
       AND   T.FL_ANALISADO = 1  
       AND   T.CD_TP_OPER = 73 /*Antecipação de parcela*/
       AND   T.CD_STTUS_CONTRATO = 1 /*ativo*/ 
       AND   T.CD_GRP_PRODUTO = ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_CodGrupoProduto'))
       AND   T.CD_CONTRATO IS NOT NULL
       AND   T.DT_LIQUID IS NULL
	   GROUP BY T.CD_MES_ANO, T.CD_TP_PESSOA, T.CD_CONTRATO, T.NM_SIST_ORIG, T.CD_DOC_IDENTF_CLIE, T.CD_TP_IDENTF_CLIE, T.QT_MES_CONTT
      ) T 
WHERE (T.CD_TP_PESSOA = 'F' 
       AND T.QT_MES_CONTT IS NOT NULL 
       AND T.QT_MES_PAGO IS NOT NULL 
       AND T.QT_PARCELA_ANTECIPADA > ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_QtdeMesesPagosPF')) 
       AND ((1-(T.QT_MES_PAGO / T.QT_MES_CONTT)) * 100) >= ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercMesesFaltantesPF')) 
       AND T.VALOR_ANTECIPADO_MES > ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_ValorMinimoPF')) 
       AND ((T.VL_RENDA_FAT IS NOT NULL 
             AND T.VALOR_ANTECIPADO_MES > (T.VL_RENDA_FAT * (((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercRenda'))/100)))
             OR 
             T.VL_RENDA_FAT IS NULL)
      )
OR (T.CD_TP_PESSOA = 'J' 
    AND T.QT_MES_CONTT IS NOT NULL 
    AND T.QT_MES_PAGO IS NOT NULL 
    AND T.QT_PARCELA_ANTECIPADA > ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_QtdeMesesPagosPJ')) 
    AND ((1-(T.QT_MES_PAGO / T.QT_MES_CONTT)) * 100) >= ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercMesesFaltantesPJ')) 
    AND T.VALOR_ANTECIPADO_MES > ((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_ValorMinimoPJ')) 
    AND ((T.VL_RENDA_FAT IS NOT NULL 
          AND T.VALOR_ANTECIPADO_MES > (T.VL_RENDA_FAT * (((select vl_param from tb_regra_parametro where cd_regra = 4090 and nm_campo_param = 'pm_PercFaturamento'))/100)))
          OR 
          T.VL_RENDA_FAT IS NULL)
   )


 
 
);
ROLLBACK;

ALTER TABLE TB_DETLH_APONTAMENTO MODIFY CONSTRAINT "FkApontamento_DetlhApontamento" ENABLE; 
