package com.tenis.tournament.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

  public static final String DATE_ONLY_FORMAT = "dd/MM/yyyy";

  public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

  public static LocalDate parseToDate(String dateString) {
    DateTimeFormatter formatter;
    formatter = DateTimeFormatter.ofPattern(DATE_ONLY_FORMAT);
    return LocalDate.parse(dateString, formatter);
  }

  public static LocalDateTime parseToDateTime(String dateString) {
    if (dateString == null) {
      return null;
    } else {
      LocalDate temp = parseToDate(dateString);
      return LocalDateTime.of(temp, LocalDateTime.MIN.toLocalTime());
    }

  }

}