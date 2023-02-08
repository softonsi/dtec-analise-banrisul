select rp.cd_regra, nm_campo_param, vl_param 
from tb_regra_parametro rp
join tb_regra r on r.cd_regra = rp.cd_regra
where fl_apagado = 0 order by cd_regra