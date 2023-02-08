SELECT LAST_DAY(T.DT_TRANS) DT_APONTAMENTO, T.CD_DOC_IDENTF_CLIE,T.CD_TP_IDENTF_CLIE,    
(SELECT 'An�lise: Regra ' || TO_CHAR(CD_REGRA) || ' - ' || NM_REGRA || '| Objetivo: ' || DS_REGRA || '|' FROM TB_REGRA WHERE CD_REGRA = 4037 AND CD_VERSAO_SISTEMA = 4) ||
'Cliente: ' ||  coalesce(T.NM_CLIE, 'Nome n�o informado') || '|' ||  
CASE T.CD_TP_IDENTF_CLIE   
	WHEN 2 THEN 'Tipo de Pessoa F�sica (CPF): ' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 4, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 7, 3) || '-' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 10, 2)   
	WHEN 3 THEN 'Tipo de Pessoa Jur�dica (CNPJ): ' ||SUBSTR(T.CD_DOC_IDENTF_CLIE, 1, 2) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 3, 3) || '.' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 6, 3) || '/' || SUBSTR(T.CD_DOC_IDENTF_CLIE, 9, 4) || '-'  || SUBSTR(T.CD_DOC_IDENTF_CLIE, 13, 2)    
	ELSE T.CD_DOC_IDENTF_CLIE   
END   
|| '|' ||    
'Lote: ' || TO_CHAR(T.CD_LOTE)  || '|' || 
'Data da transa��o : '  ||  TO_CHAR(dt_trans) || '|' || 
'Tipo de transa��o: ' || coalesce((select nm_tp_oper from tb_tp_oper o where o.cd_tp_oper = t.cd_tp_oper), to_char(cd_tp_oper)) || '|' || 
'Valor do faturamento do cliente: ' || TO_CHAR(T.VL_RENDA_FAT, 'L999G999G999G990D99') || '|' || 
'S�cio(s) sem capacidade financeira: |' || SOCIOS || '|' ||
COALESCE((':DS-pm_codCandidato' || ' (' || ':NM-pm_PercParticipacao' || '):' || :pm_PercParticipacao),'Par�metro n�o cadastrado|' ) || '|' ||   
COALESCE((':DS-pm_codCandidato' || ' (' || ':NM-pm_PercFaturamento' || '):' || :pm_PercFaturamento),'Par�metro n�o cadastrado|' ) || '|' 
AS DS_INF_ANLSE  
FROM
(
SELECT A.dt_trans, A.CD_DOC_IDENTF_CLIE, A.CD_TP_IDENTF_CLIE, A.NM_CLIE, A.CD_LOTE, A.VL_RENDA_FAT, A.CD_TP_OPER,
xmlcast(  xmlagg(xmlelement(SOCIOS, ('CPF/CNPJ ' || C.CD_DOC_IDENTF_SOCIO) || ': Renda/Faturamento ' || LTRIM(TO_CHAR(C.VL_RENDA_FAT_SOCIO, 'L999G999G999G990D99')) || ', Percentual de participa��o ' || to_char(C.PC_PARTC_SOCIO) || '% | ')  order by CD_DOC_IDENTF_SOCIO) as clob) SOCIOS
FROM TB_CADEIA_SOCTRIA C  
INNER JOIN
(SELECT trunc(DT_TRANS) dt_trans, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, NM_CLIE, CD_LOTE, VL_RENDA_FAT, cd_tp_oper
 FROM TB_TRANS_ANLSE 
 WHERE CD_LOTE = :cd_lote AND CD_SUBLOTE = :cd_sublote
   AND CD_TP_PESSOA = 'J'
   AND CD_TP_OPER IN 
       (25, /*Emiss�o de cart�o pr�-pago*/
        61, /*Abertura de conta*/
        62, /*Atualiza��o de conta*/
        74, /*Formaliza��o de Contrato*/
        78 ) /*Atualiza��o Cadastral*/
 group by trunc(DT_TRANS), CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, NM_CLIE, CD_LOTE, VL_RENDA_FAT, cd_tp_oper
 ) A 
ON C.CD_DOC_IDENTF_SOCIEDD = A.CD_DOC_IDENTF_CLIE AND C.CD_TP_IDENTF_SOCIEDD = A.CD_TP_IDENTF_CLIE 
WHERE C.PC_PARTC_SOCIO IS NOT NULL
  AND C.PC_PARTC_SOCIO > (:pm_PercParticipacao) 
  AND C.VL_RENDA_FAT_SOCIO <= (A.VL_RENDA_FAT * (:pm_PercFaturamento/100)) 
group by A.dt_trans, A.CD_DOC_IDENTF_CLIE, A.CD_TP_IDENTF_CLIE, A.NM_CLIE, A.CD_LOTE, A.VL_RENDA_FAT, A.CD_TP_OPER
) T


 