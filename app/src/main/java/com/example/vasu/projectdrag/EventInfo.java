package com.example.vasu.projectdrag;

public class EventInfo {
    private String name,description;

    EventInfo()
    {

    }

    public EventInfo(String name,String desc)
    {
        this.name=name;
        this.description=desc;
        //this.contact=contact;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /*public String getContact() {
        return contact;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public void setContact(String contact) {
        this.contact = contact;
    }*/
}
