SELECT 
CD_SEQ_PPE,
DT_ATULZ_PPE,
CD_DOC_IDENTF,
CD_TP_IDENTF,
NM_PPE,
DS_PPE_GRAU_ENVOLV,
SG_ORG_INTERNL,
CD_PAIS_ORIG,
NU_PASSRTE,
CD_FONTE_INF 
FROM TB_PPE
WHERE 1 = 1 OR (1 = ? AND 1 = ?)