package com.Main;

public class PoliceDetails {

    private String username;
    private String police_rank;
    private String station;
    private String contact;

    public PoliceDetails(String username, String police_rank, String station, String contact) {
        this.username = username;
        this.police_rank = police_rank;
        this.station = station;
        this.contact = contact;
    }

    public String getUsername() { return username; }
    public String getRank() { return police_rank; }
    public String getStation() { return station; }
    public String getContact() { return contact; }
}
