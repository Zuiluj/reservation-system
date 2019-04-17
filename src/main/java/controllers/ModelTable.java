package main.java.controllers;

// this is the class for the tableView to hold
// this acquires and returns the necessary data
public class ModelTable {
    
    String eventType, name, contact, venue, signUpDate, eventDate, packageInclusion, price, clientBudget, notes;

    public ModelTable(String eventType, String name, String contact, String venue, String signUpDate, String eventDate, String packageInclusion, String price, String clientBudget, String notes) {
        this.eventType = eventType;
        this.name = name;
        this.contact = contact;
        this.venue = venue;
        this.signUpDate = signUpDate;
        this.eventDate = eventDate;
        this.packageInclusion = packageInclusion;
        this.price = price;
        this.clientBudget = clientBudget;
        this.notes = notes;
    }


    // setter and getter
    
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getPackageInclusion() {
        return packageInclusion;
    }

    public void setPackageInclusion(String packageInclusion) {
        this.packageInclusion = packageInclusion;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClientBudget() {
        return clientBudget;
    }

    public void setClientBudget(String clientBudget) {
        this.clientBudget = clientBudget;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
