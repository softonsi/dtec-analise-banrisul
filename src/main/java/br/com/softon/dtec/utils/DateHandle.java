/**
 *
 */
package br.com.softon.dtec.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mario
 */
public class DateHandle {

    private final static Logger log = LoggerFactory.getLogger(DateHandle.class);

    private DateFormat dfm;
    private String format;

    private static final ThreadLocal<SimpleDateFormat> onlyDateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd/MM/yyyy");
        }
    };

    public DateHandle() {

    }

    /**
     * Recebe uma string contendo a DATA a ser parsiada e retorna
     * seu respectivo Date
     * <p/>
     * é utilizado como Helper dos métodos Staticos
     * @param date_text
     * @return
     */
    private Date parseDateImpl(String date_text) {
        Date data = null;
        try {
            data = new Date(this.dfm.parse(date_text).getTime());
        } catch (ParseException e) {
            log.error("Erro ao transformar a data. " + e);
        }
        return data;
    }

    /**
     * Retorna uma String com a data atual no formato: HH:mm:ss dd/MM/yyyy
     * @return
     */
    public static SimpleDateFormat getSimpleFormat(String format) {

        return new SimpleDateFormat(format);
    }

    /**
     * Retorna uma String com a data atual no formato: HH:mm:ss dd/MM/yyyy
     * @return
     */
    public static String getNow() {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("HH:mm:ss dd/MM/yyyy"));
        String data = dh.getDfm().format(new Date());

        return data;
    }

    /**
     * Retorna uma String com o date atual no formato informado no parametro
     * 'format'.
     * Este parâmetro deve ser ser um padrão Date Format válido.
     * <p/>
     * Parâmetros coringas podem ser utilizados:
     * <p/>
     * "full"		- HH:mm:ss dd/MM/yyyy
     * "week"	- Retorna dia da seman por extenso
     * "time"		- Retorna hora no formato HH:mm:ss
     * "hour"	- Retorna somente a hora atual
     * "minute"	- Retorna somente minuto atual
     * "second"- Retorna somente segundos atuais
     * @param format
     * @return
     */
    public static String getNow(String format) {
        if (format.equals("full")) {
            format = "HH:mm:ss dd/MM/yyyy";
        } else if (format.equals("week")) {
            format = "E";
        } else if (format.equals("time")) {
            format = "HH:mm:ss";
        } else if (format.equals("hour")) {
            format = "HH";
        } else if (format.equals("minute")) {
            format = "mm";
        } else if (format.equals("second")) {
            format = "ss";
        }

        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat(format));
        String data = dh.getDfm().format(new Date());

        return data;
    }

    /**
     * Retorna uma String com o date/time atual no formato SQL
     * @return
     */
    public static String getSqlNow() {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd HH:mm:ss"));
        String data = dh.getDfm().format(new Date());

        return data;
    }

    /**
     * Retorna uma String com o date atual no formato SQL yyyy-MM-dd
     * @return
     */
    public static String getSqlDate() {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd"));
        String data = dh.getDfm().format(new Date());

        return data;
    }

    public static Double getDecimalTime(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String x = dateFormat.format(date);

        String[] y = x.split(":");

        Integer hour = new Integer(y[0]);
        double minute = new Integer(y[1]);

        minute = ((minute * 10) / 6) / 100;

        return hour + minute;

    }

    /**
     * Retorna um objeto Date da String informada no parâmetro com o seguinte
     * formato dd/MM/yyyy
     * @param date_text
     * @return
     */
    public static Date parseDate(String date_text) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("dd/MM/yyyy"));
        Date data = dh.parseDateImpl(date_text);

        return data;
    }

    /**
     * Retorna um objeto Date da String informada no parâmetro com o seguinte
     * formato yyyy-MM-dd
     * @param date_text
     * @return
     */
    public static Date parseSqlDate(String date_text) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd"));
        Date data = dh.parseDateImpl(date_text);

        return data;
    }

    /**
     * Retorna um objeto Date da String informada no parâmetro 'dateText' sob o
     * formato informado na String do parâmetro 'format'
     * <p/>
     * Parâmetros coringas podem ser utilizados:
     * <p/>
     * "full"		- HH:mm:ss dd/MM/yyyy
     * "week"	- Retorna dia da seman por extenso
     * "time"		- Retorna hora no formato HH:mm:ss
     * "hour"	- Retorna somente a hora atual
     * "minute"	- Retorna somente minuto atual
     * "second"- Retorna somente segundos atuais
     * @param date_text
     * @param format
     * @return
     */
    public static Date parseDate(String dateText, String format) {
        if (format.equals("full")) {
            format = "HH:mm:ss dd/MM/yyyy";
        } else if (format.equals("week")) {
            format = "E";
        } else if (format.equals("time")) {
            format = "HH:mm:ss";
        } else if (format.equals("hour")) {
            format = "HH";
        } else if (format.equals("minute")) {
            format = "mm";
        } else if (format.equals("second")) {
            format = "ss";
        }
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat(format));
        Date data = dh.parseDateImpl(dateText);

        return data;
    }

    /**
     * Recebe uma String de data através de 'formatIn', realiza um parse da String,
     * transforma em Date, e retorna uma String formatada no padrão informado
     * em 'formatOut'
     * @param date_text
     * @param formatIn
     * @param formatOut
     * @return
     */
    public static String formatDate(String date_text, String formatIn, String formatOut) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat(formatIn));
        Date dataIn = dh.parseDateImpl(date_text);
        dh.setDfm(DateHandle.getSimpleFormat(formatOut));

        String dataOut = dh.getDfm().format(dataIn);

        return dataOut;
    }

    /**
     * Recebe um objeto Date e uma String 'formatOut'.
     * @param dataIn
     * @param formatOut
     * @return
     */
    public static String formatDate(Date dataIn, String formatOut) {
        DateHandle dh = new DateHandle();

        dh.setDfm(DateHandle.getSimpleFormat(formatOut));

        String dataOut = dh.getDfm().format(dataIn);

        return dataOut;
    }

    /**
     * Retorna uma String com o date atual no formato SQL
     * @return
     */
    public static String formatDateToSql(Date date) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd HH:mm:ss"));
        String data = dh.getDfm().format(date);

        return data;
    }

    /**
     * Retorna uma String com o date atual no formato SQL
     * @return
     */
    public static String formatDateToSqlNoTime(Date date) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd"));
        String data = dh.getDfm().format(date);

        return data;
    }

    /**
     * Retorna uma String com o date atual no formato SQL
     * @return
     */
    public static String formatDateToSql(Date date, String formatIn) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat(formatIn));
        String data = dh.getDfm().format(date);

        return data;
    }

    public static String formatDateToSqlmili(Date date) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat("yyyy-MM-dd HH:mm:ss.SS"));
        String data = dh.getDfm().format(date);

        return data;
    }

    /**
     * Método para comparar as datas e retornar o numero de dias de diferença entre elas
     * <p/>
     * Compare two date and return the difference between them in days.
     * @param dataLow The lowest date
     * @param dataHigh The highest date
     * @return int
     */
    public static int dataDiff(Date dataLow, Date dataHigh) {

        dataLow = new Date(dataLow.getTime());
        dataHigh = new Date(dataHigh.getTime());

        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();

        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();

        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);

        int dif_multiplier = 1;

        // Verifica a ordem de inicio das datas
        if (dataLow.compareTo(dataHigh) < 0) {
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        } else {
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }

        int result_years = 0;
        int result_months = 0;
        int result_days = 0;

        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissexto
        while (curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)) {

            int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);

        }

        // Marca que ï¿½ um saldo negativo ou positivo
        result_months = result_months * dif_multiplier;

        // Retorna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));

        return result_years + result_months + result_days;
    }

    public static Long getTimestamp(String date, String format) {
        Date dateParsed = DateHandle.parseDate(date, format);

        return dateParsed.getTime();
    }

    /**
     * Retorna uma data(String) do formato informado no parâmetro com o seguinte
     * @param date_text
     * @return
     */

    public static String getFromTimestamp(Long timestamp, String format) {
        DateHandle dh = new DateHandle();
        dh.setDfm(DateHandle.getSimpleFormat(format));
        String data = dh.getDfm().format(new Date(timestamp));

        return data;
    }

    public static Date getDateFromTimestamp(Long timestamp) {
        return new Date(timestamp);
    }

    /**
     * Método que soma o número de meses especificado na data(YYYY-MM-DD) solicitada
     * @param data formato yyyy-MM-dd
     * @param addMonths + ou - quantidade de meses
     * @return Data final no formato yyyy-MM-dd
     */
    public static String dateAddMonths(String data, int addMonths) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(DateHandle.parseSqlDate(data));
        gc.add(Calendar.MONTH, addMonths);

        return DateHandle.getFromTimestamp(gc.getTimeInMillis(), "yyyy-MM-dd");
    }

    /**
     * Método que soma o número de dias especificado na data(YYYY-MM-DD) solicitada
     * @param data formato yyyy-MM-dd
     * @param addDays + ou - quantidade de dias
     * @return Data final no formato yyyy-MM-dd
     */
    public static String dateAddDays(String data, int addDays) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(DateHandle.parseSqlDate(data));
        gc.add(Calendar.DAY_OF_MONTH, addDays);

        return DateHandle.getFromTimestamp(gc.getTimeInMillis(), "yyyy-MM-dd");
    }

    /**
     * Método que soma o número de meses especificado na data solicitada
     * @param data - java.util.Date
     * @param addMonths + ou - quantidade de meses
     * @return Data final
     */
    public static Date dateAddMonths(Date data, int addMonths) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(data);
        gc.add(Calendar.MONTH, addMonths);

        return gc.getTime();
    }

    /**
     * Método que soma o número de dias especificado na data solicitada
     * @param data - java.util.Date
     * @param addDays + ou - quantidade de dias
     * @return Data final
     */
    public static Date dateAddDays(Date data, int addDays) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(data);
        gc.add(Calendar.DAY_OF_MONTH, addDays);

        return gc.getTime();
    }

    /**
     * Método que soma o número de horas especificado na data solicitada
     * @param data - java.util.Date
     * @param addHours + ou - quantidade de horas
     * @return Data final
     */
    public static Date dateAddHours(Date data, int addHours) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(data);
        gc.add(Calendar.HOUR_OF_DAY, addHours);

        return gc.getTime();
    }

    /**
     * Método que soma o número de horas especificado na data solicitada
     * @param data - java.util.Date
     * @param addHours + ou - quantidade de horas
     * @return Data final
     */
    public static Timestamp dateAddHours(Timestamp data, int addHours) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.setTime(data);
        gc.add(Calendar.HOUR_OF_DAY, addHours);

        return new Timestamp(gc.getTimeInMillis());
    }

    /**
     * Método retorna um Timestamp de uma data em String, no formato passado (ex: "dd/mm/yyyy")
     * @param data a ser formatada
     * @param formato em que a "data" se encontra
     * @return Timestamp
     */
    public static Timestamp getTimestampFromString(String data, String formato) {

        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        Timestamp retorno;
        try {
            retorno = new Timestamp(formatador.parse(data).getTime());
        } catch (ParseException e) {
            retorno = null;
        }

        return retorno;
    }

    /**
     * @return the dfm
     */
    public DateFormat getDfm() {
        return this.dfm;
    }

    /**
     * @param dfm the dfm to set
     */
    public void setDfm(DateFormat dfm) {
        this.dfm = dfm;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * before &lt;= date &lt;= after
     * @param date
     * @param before
     * @param after
     * @return
     */
    public static boolean between(long date, long before, long after) {
        return date >= before && date <= after;
    }

    public static boolean between(Object date, Object before, long after) {
        return (Long) date >= (Long) before && (Long) date <= after;
    }

    /**
     * Adiciona X minutos a uma data (objeto Date);
     * Para subtrair aceita -X minutos;
     * <p/>
     * Retorna timestamp
     * @param date
     * @param minutes
     * @return
     */
    public static long addMinutes(Date date, int minutes) {

        return new Date(date.getTime() + minutes * 60000).getTime();
    }

    /**
     * Remove X minutos de uma data(objeto Date);
     * <p/>
     * Retorna timestamp
     * @param date
     * @param minutes
     * @return
     */
    public static long subtractMinutes(Date date, int minutes) {

        return addMinutes(date, minutes * -1);
    }

    /**
     * Checa se é uma data válida no formato dd/MM/yyyy apenas.
     * @param String data
     * @return boolean
     */
    public static boolean isStringComDataValida(String data) {
        String formato = "dd/MM/yyyy";
        return isStringComDataValida(data, formato);
    }

    /**
     * Checa se é uma data válida no formato passado segundo regras de "SimpleDateFormat".
     * @param String data, String formato
     * @return boolean
     */
    private static boolean isStringComDataValida(String data, String formato) {

        if (data == null) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

        if (data.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            dateFormat.parse(data.trim());
        } catch (ParseException pe) {
            return false;
        }

        return true;
    }

    public static String formatOnlyDate(Date dataTransacao) {
        SimpleDateFormat simpleDateFormat = onlyDateFormat.get();
        return simpleDateFormat.format(dataTransacao);
    }

    public static String somarMesComData(String data, int meses) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.clear();
        gc.set(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(5, 7)) - 1 + (meses), Integer.parseInt(data.substring(8, 10)));

        data = "" + gc.get(Calendar.YEAR);

        if (gc.get(Calendar.MONTH) < 9) {
            data += "-0" + (gc.get(Calendar.MONTH) + 1);

        } else {
            data += "-" + (gc.get(Calendar.MONTH) + 1);

        }

        if (gc.get(Calendar.DAY_OF_MONTH) <= 9) {
            data += "-0" + (gc.get(Calendar.DAY_OF_MONTH));

        } else {
            data += "-" + (gc.get(Calendar.DAY_OF_MONTH));
        }

        return data;

    }

    public static Timestamp setHorario(Timestamp data, int hora, int minuto) {
        return setHorario(data, hora, minuto, 0);
    }

    public static Timestamp setHorario(Timestamp data, int hora, int minuto, int segundo) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(data.getTime());

        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp getTimestampFromDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Retorna um objeto DATE recebendo por parâmetro
     * a data no formato "yyyy-mm-dd"
     * @param String data no formato yyyy-mm-dd
     * @return Date
     */
    public static Date createNewDate(String data_str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(data_str);
        } catch (ParseException e) {
            log.error("Erro ao transformar a data. " + e);
        }

        return date;
    }

    public static boolean verificaMesmoDia(Date dataA, Date dataB) {
        String data_a = formatDateToSqlNoTime(dataA);
        String data_b = formatDateToSqlNoTime(dataB);

        if (data_a.equals(data_b)) {
            return true;

        }
        return false;
    }
}
