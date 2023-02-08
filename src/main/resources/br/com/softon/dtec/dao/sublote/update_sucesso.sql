UPDATE tb_sub_lote
  SET cd_processamento = 'X', dt_fim_analise = sysdate
  WHERE cd_lote = ? and cd_sublote = ?