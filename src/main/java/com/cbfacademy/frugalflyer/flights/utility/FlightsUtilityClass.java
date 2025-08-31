package com.cbfacademy.frugalflyer.flights.utility;

import java.time.LocalDate;

// Final, so cannot be inherited of instantiated.
public final class FlightsUtilityClass {

    private static LocalDate latestDate = null;
    private static LocalDate earliestDate = null;
    private static LocalDate today = LocalDate.now();

    /**
     * Utility method to calculate the desired departure date range
     * If departure date and flexiday value have been supplied, subtracts and adds the flexidays from and to the departure date to find range of dates.
     * If earliest date is before today's date, the value is set to today's date.
     * If only departure date is supplied, set the range of dates to that departureDate value.
     * @param departureDate Date that the flight will depart from the departure airport
     * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
     * @return Range of acceptable departure dates.
     */
    public static LocalDate[] setDateRange(LocalDate departureDate, Integer flexiDays) {

        if (departureDate == null) {
            return new LocalDate[] {null,null};
        }
        // if departureDate and flexiDays have values, calculate earliest and latest dates
        // If earliest date ends up being before today, set it to today
        if (departureDate != null && flexiDays != null) {

            latestDate = departureDate.plusDays(flexiDays);
            earliestDate = departureDate.minusDays(flexiDays);
            if (earliestDate.isBefore(today)) {
                earliestDate = today;
            }
            
        } else if (departureDate != null) {
            earliestDate = departureDate;
            latestDate = departureDate;
        }
        
        return new LocalDate[] {earliestDate,latestDate};
    }
}
