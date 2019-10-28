package ee.energia.testassignment;

import ee.energia.testassignment.planning.ChargePlan;
import ee.energia.testassignment.price.EnergyPrice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChargePlanner {

    // the capability of the charger to charge certain amount of energy into the battery in 1 hour
    public final static int CHARGER_POWER = 50;

    // maximum battery level possible
    public final static int MAX_LEVEL = 100;

    // battery level required by the end of the charging
    public final static int REQUIRED_LEVEL = 100;

    /**
     * Method calculates the optimal hourly charge plan.
     * Method finds the cheapest hour to charge the battery (if multiple then the earliest)
     * and uses it to charge the battery up to the {@link ChargePlanner#REQUIRED_LEVEL}.
     * If {@link ChargePlanner#CHARGER_POWER} limitation does not allow to do this in one hour,
     * then method finds the next cheapest opportunities and uses them until {@link ChargePlanner#REQUIRED_LEVEL} is met.
     *
     * Method returns the array of {@link ChargePlan} objects that represent the hourly time slot
     * and the capacity that we need to charge during that hour to charge the battery.
     *
     * @param batteryLevel initial battery level when the charger is connected
     * @param energyPrices the list of the energy prices from the moment when charger is connected until the moment when battery needs to be charged
     *                     there is an assumption that battery is connected the first second of the first given hour and disconnected the last second of the last given hour
     * @return
     */
    public static ArrayList<ChargePlan> calculateChargePlan(int batteryLevel, ArrayList<EnergyPrice> energyPrices) {
        // todo: implement the function that will be calculating the optimal hourly charge plan
    	ArrayList<ChargePlan> chargePlans = new ArrayList<>();
    	if(energyPrices !=null && !energyPrices.isEmpty()) {
    	energyPrices.sort(Comparator.comparing(EnergyPrice::getAskPrice));
        for(EnergyPrice energyPrice:energyPrices) {
    		int capacity = 0;
    		 capacity = Math.min(ChargePlanner.REQUIRED_LEVEL - batteryLevel, ChargePlanner.CHARGER_POWER);
        	if(batteryLevel<= REQUIRED_LEVEL && REQUIRED_LEVEL<=MAX_LEVEL) {
        		ChargePlan chargePlan= new ChargePlan(capacity, energyPrice.getHour(), energyPrice.getMonth(), energyPrice.getYear());
        		chargePlans.add(chargePlan);
        		batteryLevel = capacity + batteryLevel;
        	}else {
        		capacity = 0;
        		ChargePlan chargePlan= new ChargePlan(capacity, energyPrice.getHour(), energyPrice.getMonth(), energyPrice.getYear());
        		chargePlans.add(chargePlan);

        	}
        	
        }
        chargePlans.sort(Comparator.comparing(ChargePlan::getHour));
    	
    	}
    	return chargePlans;
    }
}
