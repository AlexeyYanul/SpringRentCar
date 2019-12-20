package itacademy.converter.dozer;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends DozerConverter<LocalDateTime, String> {


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LocalDateTimeConverter() {
        super(LocalDateTime.class, String.class);
    }

    @Override
    public String convertTo(LocalDateTime localDateTime, String s) {
        return localDateTime.format(formatter);
    }

    @Override
    public LocalDateTime convertFrom(String s, LocalDateTime localDateTime) {
        return LocalDateTime.parse(s, formatter);
    }
}
