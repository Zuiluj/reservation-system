package main.java.models;


public class HistoryModelTable {
    String eventType, name, eventDate;
    
    public HistoryModelTable(String eventType, String name, String eventDate) {
        this.eventType = eventType;
        this.name = name;
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    
    
    
}
