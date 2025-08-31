package com.cbfacademy.frugalflyer.flights.utility;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName(value = "Flights Utility Class Test Suite")
class FlightsUtilityClassTest {

    private final LocalDate today = LocalDate.now();

    @Test
    @DisplayName("setDateRange returns nulls when both departureDate and flexiDays are null")
    void test_setDateRange_returnsNullWhenDateIsNull() {

        assertEquals(null,FlightsUtilityClass.setDateRange(null, null)[0]);
        assertEquals(null,FlightsUtilityClass.setDateRange(null, null)[1]);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 15, 36, 289, 105})
    @DisplayName("setDateRange returns departureDate for both earliest and latest when flexiDays is null")
    void test_setDateRange_withoutFlexiDays(int daysToAdd) {
        LocalDate departureDate = today.plusDays(daysToAdd);

        LocalDate[] result = FlightsUtilityClass.setDateRange(departureDate, null);

        assertEquals(departureDate, result[0], "Earliest date should equal departureDate");
        assertEquals(departureDate, result[1], "Latest date should equal departureDate");
    }

    @ParameterizedTest
    @CsvSource({"4, 4", "70, 5", "90, 0", "97, 30"})
    @DisplayName("setDateRange returns correct earliest/latest when flexiDays is provided")
    void test_setDateRange_withFlexiDays(final int daysToAdd, final int flexiDays) {
        LocalDate departureDate = today.plusDays(daysToAdd);

        LocalDate[] result = FlightsUtilityClass.setDateRange(departureDate, flexiDays);

        assertEquals(departureDate.minusDays(flexiDays), result[0], "Earliest date should be departureDate - flexiDays");
        assertEquals(departureDate.plusDays(flexiDays), result[1], "Latest date should be departureDate + flexiDays");
    }   

    @ParameterizedTest
    @CsvSource({"4, 21", "70, 100", "90, 203", "7, 30"})
    @DisplayName("setDateRange returns today's date when earliest date is calculated to be earlier than today")
    void test_setDateRange_withEarliestDateBeforeToday(final int daysToAdd, final int flexiDays) {
        LocalDate departureDate = today.plusDays(daysToAdd);

        LocalDate[] result = FlightsUtilityClass.setDateRange(departureDate, flexiDays);

        assertEquals(today, result[0], "Earliest date should be departureDate - flexiDays");
        assertEquals(departureDate.plusDays(flexiDays), result[1], "Latest date should be departureDate + flexiDays");
    } 
}
