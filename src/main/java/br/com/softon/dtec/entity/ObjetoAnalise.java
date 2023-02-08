package br.com.softon.dtec.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.MaskFormatter;

public class ObjetoAnalise {

	/**
	* CD_DOC_IDENTF_CLIE - Número do documento de identificação, de acordo com o campo CD_TP_DOC_IDENTF
	*/
	private	String	codDocIdentificacaoCliente;

	/** 
	* CD_TP_IDENTF_CLIE - Indica que tipo de documento o campo NU_DOC_IDENTF contêm, podendo ser 1-RG; 2-CPF; 3-CNPJ; 4-RNE; 5-Passaporte; 6-ID.
	*/
	private	Byte	codTipoIdentificacaoCliente;

	/**
	* CD_LOTE - Número do lote de processamento.
	*/
	private	Long	codLote;

	/**
	* CD_SUBLOTE - Número do sub-lote de processamento.
	*/
	private	Short	codSublote;	

	/**
	* DT_APONTAMENTO - Data e hora do apontamento. Campo adicionado ao objeto de análise pois receberá da regra a data de apontamento devido ao atraso de 
	* transações que podem chegar dias após a real data de operação. Exemplo - Lote do dia 05 de fevereiro recebendo transações do dia 31 de janeiro.
	*/
	private Timestamp dataApontamento;

	/**
	* PENALIZACAO - Valor da penalização de regras pós processamento.
	*/
	private	Short	valorPenalizacao;	
	
	

    /**
     * *************************Variáveis de apoio à análise********************************
     */

    private transient final Map<Short, Boolean> regras = new HashMap<Short, Boolean>();

    public void addRegra(Short coRegra) {
        regras.put(coRegra, false);
    }

    public void addRegra(Short coRegra, Boolean flagMostrarTransacao) {
        regras.put(coRegra, flagMostrarTransacao);
    }

    public Map<Short, Boolean> getRegras() {
        return regras;
    }

    private transient final Map<Short, String> informacoesDisparoRegra = new HashMap<Short, String>();


    public void addInformacoesDisparoRegra(Short regra, String informacoes) {
    	if(informacoesDisparoRegra.containsKey(regra)) {
    		informacoes += informacoesDisparoRegra.get(regra);
    	}
    	informacoesDisparoRegra.put(regra, informacoes);
    }

    public String getInformacoesPorRegra(Short coRegra) {
        return informacoesDisparoRegra.get(coRegra);
    }

    /***************************************** Fim do trecho de variáveis de apoio à análise ******************************************/
    
    
	public ObjetoAnalise() {
		// Dummy just to pass serialization tool "Kryo"...
		super();
	}
	
	
	/**
	 * Método que concatena o código do documento identificador do cliente com o tipo de identificação do cliente.
	 * Será utilizado como índice no redis ao inserir e buscar transações por cliente.
	 */
	public String getCodigoIdentificadorCliente() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dataApontamento) + codTipoIdentificacaoCliente + codDocIdentificacaoCliente + "";
	}


	public String getCodDocIdentificacaoCliente() {
		return codDocIdentificacaoCliente;
	}


	public Long getCodLote() {
		return codLote;
	}


	public Short getCodSublote() {
		return codSublote;
	}


	public Byte getCodTipoIdentificacaoCliente() {
		return codTipoIdentificacaoCliente;
	}


	public String getCodTipoIdentificacaoClienteString() {
		return codTipoIdentificacaoCliente + "";
	}
	

	public Timestamp getDataApontamento() {
		return dataApontamento;
	}


	public Short getValorPenalizacao() {
		return valorPenalizacao;
	}

    /**
     * *************************Funções de apoio à análise********************************
     * *************************Máscaras, formatos, etc...********************************
     */

    public String formatarCEP(Integer cep) throws ParseException {
    	
    	if(cep == null) {
    		return "0";
    	}
        
        MaskFormatter mf = new MaskFormatter("#####-###");
        mf.setValueContainsLiteralCharacters(false);
		
        return mf.valueToString(String.format("%08d", cep));
    	
    }

    public String formatarValor(Double valor) throws ParseException {
    	
    	if(valor == null) {
    		return "";
    	}
    	
    	NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
		
        return nf.format(valor);
    	
    }

    public String formatarData(Date data) throws ParseException {
    	
    	if(data == null) {
    		return "";
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
        return sdf.format(data);
    	
    }

    public String formatarData(Timestamp data) throws ParseException {
    	
    	if(data == null) {
    		return "";
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
        return sdf.format(data);
    	
    }

    private transient final Map<String, Object> buscaInfoAccumulate = new HashMap<String, Object>();

    public void addInfoAccumulate(String nome, Object valor) {
    	buscaInfoAccumulate.put(nome, valor);
    }

    public Object getInfoAccumulate(String nome) {
        return buscaInfoAccumulate.get(nome);
    }

	/**
     * Constroi uma instância do objeto ObjetoAnalise a partir de uma linha do resultaset
     * @param rs (java.sql.ResultSet)
     * @return ObjetoAnalise
     * @throws SQLException
     */
	public static ObjetoAnalise build(ResultSet rs, Short cdRegra, Sublote sublote) throws SQLException {
		ObjetoAnalise tr = new ObjetoAnalise();
		
		try {
			tr.dataApontamento = rs.getString("DT_APONTAMENTO") != null ? rs.getTimestamp("DT_APONTAMENTO") : null;
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(tr.dataApontamento.getTime());
			if(cal.get(GregorianCalendar.DATE) != cal.getActualMaximum(GregorianCalendar.DATE)) {
				cal.set(GregorianCalendar.DATE, cal.getActualMaximum(GregorianCalendar.DATE));
				tr.dataApontamento = new Timestamp(cal.getTimeInMillis());
			}
		} catch (Exception e) {
    		// Modificado data do apontamento para devolver o último dia do mês baseado no lote processado
    		int year = Integer.parseInt(sublote.getCoLote().toString().substring(0, 4));
    		int month = Integer.parseInt(sublote.getCoLote().toString().substring(4, 6)) - 1;
    		GregorianCalendar gc = new GregorianCalendar(year, month, 1);
    		gc.set(GregorianCalendar.DATE, gc.getActualMaximum(GregorianCalendar.DATE));
			tr.dataApontamento = new Timestamp(gc.getTimeInMillis());
		}
		tr.codDocIdentificacaoCliente = rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null;
		tr.codTipoIdentificacaoCliente = rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null;

		try {
			tr.valorPenalizacao = rs.getString("PENALIZACAO") != null ? rs.getShort("PENALIZACAO") : (short) 0;
		} catch (SQLException e) {
			// Só irá funcionar para regras pós processamento...
			// Para todas as outras entrará aqui na exceção e continuará.
		}
		
		tr.addInformacoesDisparoRegra(cdRegra, rs.getString("DS_INF_ANLSE") != null ? rs.getString("DS_INF_ANLSE") : null);
		
		return tr;
	}
}