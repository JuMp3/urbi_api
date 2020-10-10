package it.jump3.urbi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtil {

    private DateTimeFormatter dateTimeFormatter;
    private SimpleDateFormat simpleDateFormat;
    private DateFormat dateFormat;
    private static DateTimeFormatter dateTimeFormatterDefault = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static SimpleDateFormat simpleDateTimeFormatDefault = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static SimpleDateFormat simpleDateTimeFormatTimestampDefault = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static DateTimeFormatter dateFormatterDefault = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static DateFormat dateFormatDefault = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat simpleDateFormatDefault = new SimpleDateFormat("dd/MM/yyyy");
    public static String FE_DATE_FORMAT = "yyyy-MM-dd";

    public void init(String formatData) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(formatData);
        this.simpleDateFormat = new SimpleDateFormat(formatData);
        this.dateFormat = new SimpleDateFormat(formatData);
    }

    public LocalDateTime getFromString(String date) {
        Assert.notNull(this.dateTimeFormatter, "Il date formatter deve essere valorizzato");
        return LocalDateTime.parse(date, this.dateTimeFormatter);
    }

    public String getFromLocalDateTime(LocalDateTime date) {
        Assert.notNull(this.dateTimeFormatter, "Il date formatter deve essere valorizzato");
        return date.format(this.dateTimeFormatter);
    }

    public String getFromTimestamp(Timestamp timestamp) {
        Assert.notNull(this.simpleDateFormat, "Il date formatter deve essere valorizzato");
        return this.simpleDateFormat.format(timestamp);
    }

    public String getDateDefault(String date) {
        Assert.notNull(this.dateFormat, "Il date format deve essere valorizzato");
        return this.dateFormat.format(date);
    }

    public static LocalDateTime getDateTimeFromStringDefault(String date) {
        if (date == null) return null;
        return LocalDateTime.parse(date, dateTimeFormatterDefault);
    }

    public static Timestamp getTimestampFromStringDefault(String date) throws ParseException {
        if (date == null) return null;
        Date parsedDate = simpleDateTimeFormatTimestampDefault.parse(date);
        return new Timestamp(parsedDate.getTime());
    }

    public static LocalDate getDateFromStringDefault(String date) {
        if (date == null) return null;
        return LocalDate.parse(date, dateFormatterDefault);
    }

    public static String getFromLocalDateTimeDefault(LocalDateTime date) {
        if (date == null) return null;
        return date.format(dateTimeFormatterDefault);
    }

    public static String getFromLocalDateDefault(LocalDate date) {
        if (date == null) return null;
        return date.format(dateFormatterDefault);
    }

    public static String getDateTimeFromTimestampDefault(Timestamp timestamp) {
        if (timestamp == null) return null;
        return simpleDateTimeFormatTimestampDefault.format(timestamp);
    }

    public static String getDateFromTimestampDefault(Timestamp timestamp) {
        if (timestamp == null) return null;
        return simpleDateFormatDefault.format(timestamp);
    }

    public static String getDateStringDefault(Date date) {
        if (date == null) return null;
        return dateFormatDefault.format(date);
    }

    public static String getDateTimeFromXMLGregorianCalendarDefault(XMLGregorianCalendar timestamp) {
        if (timestamp == null) return null;
        Calendar calendar = timestamp.toGregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(calendar.getTimeZone());
        return formatter.format(calendar.getTime());
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(Date parse) throws DatatypeConfigurationException {
        Instant i = parse.toInstant();
        String dateTimeString = i.toString();
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        return date;
    }

    public static Date getUtilDateFromStringDefault(String stringDate) {
        if (stringDate == null) return null;
        try {
            return simpleDateTimeFormatDefault.parse(stringDate);
        } catch (ParseException var2) {
            log.error("ParseException for date {}", stringDate);
            return null;
        }
    }

    public static LocalDateTime getFromString(String formatter, String date) {
        Assert.notNull(formatter, "The date formatter is required");
        DateTimeFormatter dateTimeFormatterDefault = DateTimeFormatter.ofPattern(formatter);
        return LocalDateTime.parse(date, dateTimeFormatterDefault);
    }

    public static String getFromLocalDateTime(String formatter, LocalDateTime date) {
        Assert.notNull(formatter, "The date formatter is required");
        DateTimeFormatter dateTimeFormatterDefault = DateTimeFormatter.ofPattern(formatter);
        return date.format(dateTimeFormatterDefault);
    }

    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static LocalDateTime getFirstDayOfYear() {
        return LocalDate.of(getCurrentYear(), Month.JANUARY, 1).atStartOfDay();
    }

    public static LocalDateTime getLastDayOfYear() {
        return LocalDate.of(getCurrentYear(), Month.DECEMBER, 31).atStartOfDay().plusDays(1).minusSeconds(1);
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp, Locale locale) {
        ZoneId zoneId = Calendar.getInstance(locale).getTimeZone().toZoneId();
        return timestamp.toInstant().atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static Timestamp getTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }


    public static LocalDateTime nowLocalDateTimeUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDate nowLocalDateUtc() {
        return LocalDate.now(ZoneOffset.UTC);
    }

    public static LocalDateTime nowLocalDateTime(String zoneId) {
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

    public static LocalDate nowLocalDate(String zoneId) {
        return LocalDate.now(ZoneId.of(zoneId));
    }

    public static LocalDateTime nowLocalDateTime(Locale locale) {
        return LocalDateTime.now(getZoneIdFromLocale(locale));
    }

    public static LocalDate nowLocalDate(Locale locale) {
        return LocalDate.now(getZoneIdFromLocale(locale));
    }


    public static LocalDateTime nowSystemLocalDateTime(Locale locale) {
        return convertInSystemZoneFromUTC(nowLocalDateTimeUtc(), locale);
    }

    public static LocalDate nowSystemLocalDate(Locale locale) {
        return nowSystemLocalDateTime(locale).toLocalDate();
    }

    public static ZoneId getZoneIdFromLocale(Locale locale) {
        return Calendar.getInstance(locale).getTimeZone().toZoneId();
    }

    public static LocalDateTime convertInSystemZoneFromUTC(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(getZoneIdFromLocale(locale)).toLocalDateTime();
    }

    public static LocalDateTime convertInZoneFromUTC(LocalDateTime localDateTime, String zoneId) {
        return localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime();
    }

    public static LocalDateTime convertInSystemZoneFromZoneId(LocalDateTime localDateTime, Locale locale, String zoneId) {
        return localDateTime.atZone(ZoneId.of(zoneId)).withZoneSameInstant(getZoneIdFromLocale(locale)).toLocalDateTime();
    }

    public static LocalDateTime convertInUTC(LocalDateTime localDateTime, String zoneIdFrom) {
        return localDateTime.atZone(ZoneId.of(zoneIdFrom)).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDateTime convertInUTC(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.atZone(getZoneIdFromLocale(locale)).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}
