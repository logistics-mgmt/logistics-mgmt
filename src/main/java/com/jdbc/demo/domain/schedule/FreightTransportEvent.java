package com.jdbc.demo.domain.schedule;

import com.jdbc.demo.domain.psql.FreightTransport;

/**
 * Created by Mateusz on 21-Feb-16.
 */
public class FreightTransportEvent extends ScheduleEvent {

    private final static String API_URL = "/transports/";
    private final static String ACTIVE_COLOR = "green";
    private final static String FINISHED_COLOR = "red";
    private final static String PLANNED_COLOR = "blue";

    public FreightTransportEvent(FreightTransport transport){
        super(transport.getName(), transport.getLoadDate());
        this.setEnd(transport.getUnloadDate());
        this.setUrl(API_URL + transport.getId());
        if(transport.getFinished())
            this.setColor(FINISHED_COLOR);
        else if(transport.isPlanned())
            this.setColor(PLANNED_COLOR);
        else
            this.setColor(ACTIVE_COLOR);

    }
}
