package com.jdbc.demo.services.planning.analyzers;

import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.domain.psql.Vehicle;
import com.jdbc.demo.services.planning.VehicleAnalyzer;
import com.jdbc.demo.services.planning.exceptions.PlanningException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by owen on 08/03/16.
 */
@Service
public class BasicVehicleAnalyzer extends Analyzer implements VehicleAnalyzer {

    class PayloadDeltasComparator implements Comparator<Vehicle> {

        double totalPayload;
        double targetPayload;
        double remainingPayload;

        public PayloadDeltasComparator(double total_payload, double target_payload) {
            this.totalPayload = total_payload;
            this.targetPayload = target_payload;
            this.remainingPayload = target_payload - total_payload;
        }

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            if (Math.abs(o1.getMaxPayload() - remainingPayload) >
                    Math.abs(o2.getMaxPayload() - remainingPayload)){
                return 1;
            }
            else if(o1.getMaxPayload().equals(o2.getMaxPayload())){
                return 0;
            }
            else{
                return -1;
            }
        }
    }

    @Override
    public List<Vehicle> getVehiclesForPlannedTransport(List<Vehicle> vehicles, FreightTransport transportPlan)
            throws PlanningException {

        List<Vehicle> availableVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (isVehicleAvailable(vehicle, transportPlan.getLoadDate(), transportPlan.getUnloadDate())) {
                availableVehicles.add(vehicle);
            }
        }

        if (availableVehicles.isEmpty())
            throw new PlanningException(String.format("There are no available vehicles for transport:\n %s.",
                    transportPlan));

        double totalAvailablePayload = getVehiclesMaxPayload(availableVehicles);

        if (totalAvailablePayload < transportPlan.getPayloadWeight())
            throw new PlanningException(String.format("Total maximum payload of available vehicles: %s is lower than " +
                    "planned Transport:\n %s \n payload weight.", totalAvailablePayload, transportPlan));

        List<Vehicle> suggestedVehicles = new ArrayList<>();
        double totalPayload = 0;
        double targetPayload = transportPlan.getPayloadWeight();
        availableVehicles.sort(new PayloadDeltasComparator(totalPayload, targetPayload));
        while(!availableVehicles.isEmpty()){
            Vehicle availableVehicle = availableVehicles.get(0);

            if(totalPayload < targetPayload){
                suggestedVehicles.add(availableVehicle);
                totalPayload += availableVehicle.getMaxPayload();
            }

            else if(availableVehicle.getMaxPayload() >= targetPayload
                    && suggestedVehicles.size() > 1){
                suggestedVehicles.clear();
                suggestedVehicles.add(availableVehicle);
                break;
            }

            availableVehicles.remove(availableVehicle);
            availableVehicles.sort(new PayloadDeltasComparator(totalPayload, targetPayload));
        }

        return suggestedVehicles;
    }

    private boolean isVehicleAvailable(Vehicle vehicle, Date start_date, Date end_date) {
       return checkTimePeriodAvailability(vehicle.getTransports(), start_date, end_date);
    }

    private double getVehiclesMaxPayload(List<Vehicle> vehicles) {
        double payload = 0;
        for (Vehicle vehicle : vehicles) {
            if(vehicle.getMaxPayload() != null)
                payload += vehicle.getMaxPayload();
        }
        return payload;
    }
}
