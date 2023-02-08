select count(1) as contador from pg_locks b
	join pg_stat_activity a on a.pid = b.pid
	where b.mode in ('RowExclusiveLock', 'ExclusiveLock')
	and a.query like 'select perfil.saffn132_perfil_cnta_estbo_trmnl()'