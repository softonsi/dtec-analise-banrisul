select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND FL_PATROCINIO = 1
  AND VL_OPER is not NULL 
  AND VL_RENDA_FAT is not NULL 
  AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 102 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda') ) 
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND FL_PATROCINIO = 1
  AND not exists (
  	select 1 from TB_PERFIL_INFORMACAO
  	where CD_IDENTF_PERFIL = 2
	  AND CD_VARIAVEL_PRIMEIRA = t.CD_DOC_IDENTF_CLIE
	  AND CD_VARIAVEL_SEGUNDA = t.CD_TP_IDENTF_CLIE
	  AND CD_VARIAVEL_TERCEIRA = t.CD_TP_OPER
  )  	
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 102