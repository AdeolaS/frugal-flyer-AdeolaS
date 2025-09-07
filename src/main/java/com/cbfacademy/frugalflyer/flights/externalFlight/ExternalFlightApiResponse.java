package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.util.List;

public class ExternalFlightApiResponse {
    
    private List<Data> data;

    public ExternalFlightApiResponse(List<Data> data) {
        this.data = data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }
}
