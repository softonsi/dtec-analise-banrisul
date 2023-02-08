package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.CONTRATO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoContratos;
import br.com.softon.dtec.entity.Contrato;

public class ContratoDAO {

    private final static Logger log = LoggerFactory.getLogger(ContratoDAO.class);

    private static final String QUERY = carregarQuery(CONTRATO, "query.sql");

    public static ConjuntoContratos getConjuntoContratos() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<Contrato> result = new LinkedList<Contrato>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final Contrato contrato = new Contrato(
                		rs.getString("CD_ANO_FABRIC") != null ? rs.getShort("CD_ANO_FABRIC") : null,
        				rs.getString("CD_ANO_MODELO") != null ? rs.getShort("CD_ANO_MODELO") : null,
        				rs.getString("CD_CANAL_VENDA_CONTRATO") != null ? rs.getString("CD_CANAL_VENDA_CONTRATO") : null,
        				rs.getString("CD_CONTRATO") != null ? rs.getString("CD_CONTRATO") : null,
        				rs.getString("CD_CONVENIO") != null ? rs.getString("CD_CONVENIO") : null,
        				rs.getString("CD_CPF_CNTAT") != null ? rs.getLong("CD_CPF_CNTAT") : null,
        				rs.getString("CD_CPF_CTR_CNTAT") != null ? rs.getByte("CD_CPF_CTR_CNTAT") : null,
        				rs.getString("CD_CPF_OFFICER") != null ? rs.getLong("CD_CPF_OFFICER") : null,
                        rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
        				rs.getString("CD_DOC_IDENTF_BNEFC") != null ? rs.getString("CD_DOC_IDENTF_BNEFC") : null,
        				rs.getString("CD_FILIAL_BCO") != null ? rs.getLong("CD_FILIAL_BCO") : null,
        				rs.getString("CD_GRP_CONSORCIO") != null ? rs.getString("CD_GRP_CONSORCIO") : null,
        				rs.getString("CD_LOTE") != null ? rs.getLong("CD_LOTE") : null,
        				rs.getString("CD_MARCA") != null ? rs.getShort("CD_MARCA") : null,
        				rs.getString("CD_MODELO") != null ? rs.getInt("CD_MODELO") : null,
        				rs.getString("CD_OFFICER") != null ? rs.getInt("CD_OFFICER") : null,
        				rs.getString("CD_ORGAO") != null ? rs.getInt("CD_ORGAO") : null,
        				rs.getString("CD_ORIG_CONTRATO") != null ? rs.getLong("CD_ORIG_CONTRATO") : null,
        				rs.getString("CD_PPSTA") != null ? rs.getString("CD_PPSTA") : null,
        				rs.getString("CD_PRODUTO") != null ? rs.getString("CD_PRODUTO") : null,
        				rs.getString("CD_SGMTO_CONTRATO") != null ? rs.getLong("CD_SGMTO_CONTRATO") : null,
        				rs.getString("CD_TP_CONVENIO") != null ? rs.getString("CD_TP_CONVENIO") : null,
                        rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
        				rs.getString("CD_TP_IDENTF_BNEFC") != null ? rs.getByte("CD_TP_IDENTF_BNEFC") : null,
        				rs.getString("CD_TP_PLANO") != null ? rs.getString("CD_TP_PLANO") : null,
        				rs.getString("DT_BASE_CONTRATO") != null ? rs.getDate("DT_BASE_CONTRATO") : null,
        				rs.getString("DT_CAD_CONTRATO") != null ? rs.getDate("DT_CAD_CONTRATO") : null,
        				rs.getString("DT_CANCEL_CONTRATO") != null ? rs.getDate("DT_CANCEL_CONTRATO") : null,
        				rs.getString("DT_LIB_CONTRATO") != null ? rs.getDate("DT_LIB_CONTRATO") : null,
        				rs.getString("DT_LIQUID_CONTRATO") != null ? rs.getDate("DT_LIQUID_CONTRATO") : null,
        				rs.getString("DT_PRIM_PGTO_CONTRATO") != null ? rs.getDate("DT_PRIM_PGTO_CONTRATO") : null,
        				rs.getString("DT_PRIM_VCTO_CONTRATO") != null ? rs.getDate("DT_PRIM_VCTO_CONTRATO") : null,
        				rs.getString("DT_VENCTO_CONTRATO") != null ? rs.getDate("DT_VENCTO_CONTRATO") : null,
        				rs.getString("FL_AVERB") != null ? rs.getByte("FL_AVERB") : null,
        				rs.getString("FL_BEM_ALIENADO") != null ? rs.getByte("FL_BEM_ALIENADO") : null,
        				rs.getString("FL_BEM_APREENDD") != null ? rs.getByte("FL_BEM_APREENDD") : null,
        				rs.getString("FL_CANCEL_CONTRATO") != null ? rs.getByte("FL_CANCEL_CONTRATO") : null,
        				rs.getString("FL_DUT") != null ? rs.getByte("FL_DUT") : null,
        				rs.getString("FL_GARNT_ALIEN") != null ? rs.getByte("FL_GARNT_ALIEN") : null,
        				rs.getString("FL_RFINAM") != null ? rs.getByte("FL_RFINAM") : null,
        				rs.getString("FL_TAR_CAD_FINCD") != null ? rs.getByte("FL_TAR_CAD_FINCD") : null,
        				rs.getString("NM_CLIE") != null ? rs.getString("NM_CLIE") : null,
        				rs.getString("NM_OFFICER") != null ? rs.getString("NM_OFFICER") : null,
        				rs.getString("NM_OPERD_CONTRATO") != null ? rs.getString("NM_OPERD_CONTRATO") : null,
        				rs.getString("NM_SIST_ORIG") != null ? rs.getString("NM_SIST_ORIG") : null,
        				rs.getString("QT_COTA_CONSORCIO") != null ? rs.getString("QT_COTA_CONSORCIO") : null,
        				rs.getString("QT_DIA_CARENCIA_CONTRATO") != null ? rs.getShort("QT_DIA_CARENCIA_CONTRATO") : null,
        				rs.getString("QT_MES_CONTT") != null ? rs.getShort("QT_MES_CONTT") : null,
        				rs.getString("QT_MES_PAGO") != null ? rs.getShort("QT_MES_PAGO") : null,
        				rs.getString("VL_FINCD_CONTRATO") != null ? rs.getDouble("VL_FINCD_CONTRATO") : null,
        				rs.getString("VL_IOF_CONTRATO") != null ? rs.getDouble("VL_IOF_CONTRATO") : null,
        				rs.getString("VL_LIQUID") != null ? rs.getDouble("VL_LIQUID") : null,
        				rs.getString("VL_OPER_CONTRATO") != null ? rs.getDouble("VL_OPER_CONTRATO") : null,
        				rs.getString("VL_PCELA_CONTRATO") != null ? rs.getDouble("VL_PCELA_CONTRATO") : null,
        				rs.getString("VL_PRINC_CONTRATO") != null ? rs.getDouble("VL_PRINC_CONTRATO") : null,
        				rs.getString("VL_REBATE_PGTO") != null ? rs.getDouble("VL_REBATE_PGTO") : null,
        				rs.getString("VL_SEGURO_CONTRATO") != null ? rs.getDouble("VL_SEGURO_CONTRATO") : null,
        				rs.getString("VL_TAR_CAD_CONTRATO") != null ? rs.getDouble("VL_TAR_CAD_CONTRATO") : null,
        				rs.getString("VL_TAXA_CONTRATO") != null ? rs.getDouble("VL_TAXA_CONTRATO") : null,
        				rs.getString("VL_TAXA_EFETIVO") != null ? rs.getDouble("VL_TAXA_EFETIVO") : null,
        				rs.getString("VL_TAXA_OPER_CONTRATO") != null ? rs.getDouble("VL_TAXA_OPER_CONTRATO") : null,
        				rs.getString("VL_TAXA_REBATE") != null ? rs.getDouble("VL_TAXA_REBATE") : null
                	);
                result.add(contrato);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Contratos carregados.");
            return new ConjuntoContratos(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Contratos...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Contratos...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
    }
}
