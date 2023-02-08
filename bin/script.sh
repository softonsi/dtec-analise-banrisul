#!/bin/bash
file='sftp://softon@10.10.10.32/home/softon/docker/ld-banrisul/dtec-banrisul/conf/context.xml'      #PATH on remote host
ssh softon@10.10.10.32 "sed -i.bak "s/DTEC_LDCR_BANRISUL_PRD_180810/DTEC_BANRISUL_PRD_SEM_AG/g" $file" 