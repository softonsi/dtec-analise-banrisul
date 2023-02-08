select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND CD_TP_OPER = 4 
  AND CD_FORMA_OPER = 7 
  AND CD_RAMO_ATIVID IN 
('4212-0/00','4649-4/10','6810-2/01','6821-8/01','5099-8/99',
 '7719-5/02','5112-9/01','4614-1/00','3041-5/00','3042-3/00',
 '3316-3/01','3316-3/02','4511-1/01','4511-1/02','4511-1/03',
'2910-7/01','2910-7/02','2910-7/03')
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 18