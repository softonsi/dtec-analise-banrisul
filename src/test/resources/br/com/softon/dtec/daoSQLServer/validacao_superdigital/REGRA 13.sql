/* Operações de saques em localidades de atividades terroristas, no mês calendário fechado */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER = 7
  AND CD_LOTE = 2014091801 
  AND NM_CID_OPER IS NOT NULL
  AND SG_UF_AG_OPER IS NOT NULL
  AND EXISTS 
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR
        WHERE CD_LISTA_AUXILIAR = 4
          AND UPPER(LTRIM(RTRIM(DS_CONTEUDO))) = UPPER(LTRIM(RTRIM(NM_CID_OPER + '-' + SG_UF_AG_OPER)))
       )
 ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 13    
		
				 

  				