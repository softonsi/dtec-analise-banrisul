UPDATE coorp.saftb026_sublote saftb026
SET ic_processamento = 'P',
qt_tentativa =
case
    when qt_tentativa is null
    then 1
    else qt_tentativa + 1
end,
ts_inicio_analise = now(),
no_maquina = '%s'

        WHERE EXISTS
        (     SELECT nu_lote, nu_sublote
            FROM coorp.saftb026_sublote temp
            WHERE temp.ic_processamento IS NULL and temp.ts_fim_carga_analise is not null
            AND (saftb026.nu_lote = temp.nu_lote AND saftb026.nu_sublote = temp.nu_sublote)
            ORDER BY temp.nu_lote, temp.nu_sublote
         )
            LIMIT 1
