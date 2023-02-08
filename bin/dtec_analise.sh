#!/bin/bash

DIRETORIO_DTEC_LD_V3=/opt
LOG_HOME=$DIRETORIO_DTEC_LD_V3/dtec-ld-v3/batch/log


ps_analise_inicio=`ps cax | grep dtec_analise.sh | grep -v grep | wc -l`

if [ "$ps_analise_inicio" -gt 2 ]; then
	echo "$(date +%d/%m/%Y) - $(date +%H:%M:%S) : ===================== JÁ EXISTE UM SCRIPT DO MóDULO DE ANÁLISE RODANDO! ===================" >> $LOG_HOME/dtecMessageSimula.log
	echo "$(ps -ef | grep dtec_analise.sh)" >> $LOG_HOME/dtecMessage.log
	echo "$(date +%d/%m/%Y) - $(date +%H:%M:%S) : ===========================================================================================" >> $LOG_HOME/dtecMessageSimula.log
exit 1
fi

BASEDIR=`dirname "$0"`
cd "$BASEDIR"
DIR=`pwd`

echo ===== $DIR

CLASSPATH=.
for f in `ls ../lib/*.jar`
do
	CLASSPATH=$CLASSPATH:$f
done

java -DlogFileName=dtecMessage -Xmx2G -Xms512M -cp $CLASSPATH br.com.softon.dtec.service.DtecLDService

echo $? > $LOG_HOME/retorno_analise.log
