package john.eventpromoter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ndseeg on 4/2/17.
 */

public class Event implements Serializable{

    private String eventID;

    private int hour, minute, year, month, day;

    private String eventName;
    private String orgName;
    private String extraDetails;
    private String foodProvided;
//    private String location;
    // TODO: 4/2/17 change location so that it is two fields, and possibly have the building be an enum
    private String buildingCode;
    private String roomNumber;

    public Event(int hour, int minute,
                 int year, int month, int day,
                 String eventName,
                 String orgName,
                 String extraDetails,
                 String foodProvided,
                 String buildingCode,
                 String roomNumber) {
        this();
        this.hour = hour;
        this.minute = minute;
        this.year = year;
        this.month = month;
        this.day = day;
        this.eventName = eventName;
        this.orgName = orgName;
        this.extraDetails = extraDetails;
        this.foodProvided = foodProvided;
        this.buildingCode = buildingCode;
        this.roomNumber = roomNumber;
    }

    public Event(int hour, int minute, int year, int month, int day, String eventName, String orgName, String extraDetails) {
        this();
        this.hour = hour;
        this.minute = minute;
        this.year = year;
        this.month = month;
        this.day = day;
        this.eventName = eventName;
        this.orgName = orgName;
        this.extraDetails = extraDetails;
    }


    public Event() {
        eventID = UUID.randomUUID().toString();
    }

    public String getEventID() {
        return eventID;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public String getFoodProvided() {
        return foodProvided;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getEventTime(){
        String minuteString = String.valueOf(minute);
        String AM_PM = "AM";
        if(minute < 10){
            minuteString = "0" + minute;
        }
        if (hour >= 12){
            if (hour > 12){
                hour = hour - 12;
            }
            AM_PM = "PM";
        }
        if (hour == 0){
            hour = 12;
        }
        return hour + ": " + minuteString + " " + AM_PM;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", eventName='" + eventName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", extraDetails='" + extraDetails + '\'' +
                ", foodProvided='" + foodProvided + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
