package john.eventpromoter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathan Seegmiller on 4/30/2017.
 */

public class EventQuery {
    public static Map<String, Event> filter(Map<String, Event> eventMap,
                                            int year, int month, int day,
                                            int hour, int minute,
                                            ArrayList<String> building,
                                            ArrayList<String> orgName,
                                            boolean food){
        Map<String, Event> filteredEvents = new HashMap<>();
        boolean filterDate = false;
        boolean filterTime = false;
        boolean filterBuilding = false;
        boolean filterOrg = false;
//        boolean filterFood = food;
        if (year != -1){
            filterDate = true;
        }
        if (hour != -1){
            filterTime = true;
        }
        if (building != null){
            filterBuilding = true;
        }
        if (orgName != null){
            filterOrg = true;
        }
//        if (food != null){
//            filterFood = true;
//        }

        for (Event event: eventMap.values()) {
            boolean passFilterDate = false;
            boolean passFilterTime = false;
            boolean passFilterBuilding = false;
            boolean passFilterOrg = false;
            boolean passFilterFood = false;
            // ================================================DATE================================
            if(filterDate){
                if (event.getYear() == year && event.getMonth() == month && event.getDay() == day){
                    passFilterDate = true;
                }
            }
            else{ // If date is not one of the parameters events are being filtered on it automatically passes
                passFilterDate = true;
            }
            // ================================================TIME=================================
            if(filterTime){
                if (event.getHour() == hour && event.getMinute() == minute){
                    passFilterTime = true;
                }
            }
            else{
                passFilterTime = true;
            }
            // ============================================BUILDING=================================
            if(filterBuilding){
                if (building.contains(event.getBuildingCode())){
                    passFilterBuilding = true;
                }
            }
            else{
                passFilterBuilding = true;
            }
            // ==============================================ORG====================================
            if(filterOrg){
                if (orgName.contains(event.getOrgName())){
                    passFilterOrg = true;
                }
            }
            else{
                passFilterOrg = true;
            }
            // ==============================================FOOD===================================
            if(food){
                if (event.getFoodProvided() != null)
                    passFilterFood = true;
            }
            else{
                passFilterFood = true;
            }


            if (passFilterBuilding && passFilterDate && passFilterFood && passFilterOrg && passFilterTime){
                filteredEvents.put(event.getEventID(), event);
            }
        }
        return filteredEvents;
    }
}
