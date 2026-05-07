package com.Main;

import java.time.LocalDate;

public class Crime {

	// ===== ANSI COLOR CODES =====
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	private int crimeId;
	private String name;
	private String crimeType;
	private String location;
	private String status;
	private String investigationNotes;
	private LocalDate date;
	private String dayName;
	private String assignedPolice;

	private String punishment;

	// ===== MAIN CONSTRUCTOR (FOR ADDING CRIME) =====
	public Crime(String name, int crimeId, String crimeType, String location, String status) {

		this.name = name;
		this.crimeId = crimeId;
		this.crimeType = crimeType;
		this.location = location;
		this.status = status;
		this.punishment = "Not Decided";
		this.investigationNotes = "Not Started";
		this.assignedPolice = "Not Assigned";
		this.date = LocalDate.now();
		this.dayName = LocalDate.now().getDayOfWeek().toString();
	}

	// ===== FULL CONSTRUCTOR (FOR FETCHING FROM DATABASE) =====
	public Crime(int crimeId, String name, String crimeType, String location, String status, String assignedPolice,
			String investigationNotes, LocalDate date, String dayName, String punishment) {

		this.crimeId = crimeId;
		this.name = name;
		this.crimeType = crimeType;
		this.location = location;
		this.punishment = punishment;
		this.status = status;
		this.assignedPolice = assignedPolice;
		this.investigationNotes = investigationNotes;
		this.date = date;
		this.dayName = dayName;
	}

	// ===== DISPLAY METHOD =====
	public String displayCrime() {

		// Dynamic status color
		String statusColor = GREEN;
		if (status.equalsIgnoreCase("Pending"))
			statusColor = YELLOW;
		else if (status.equalsIgnoreCase("Critical"))
			statusColor = RED;
		else if (status.equalsIgnoreCase("Solved"))
			statusColor = GREEN;

		return CYAN + "══════════════════════════════════════════\n" + RESET + YELLOW
				+ "              🚨 CRIME DETAILS 🚨\n" + RESET + CYAN + "══════════════════════════════════════════\n"
				+ RESET +

				BLUE + "Crime ID        : " + RESET + crimeId + "\n" + GREEN + "Criminal Name   : " + RESET + name
				+ "\n" + RED + "Crime Type      : " + RESET + crimeType + "\n" + PURPLE + "Location        : " + RESET
				+ location + "\n" + statusColor + "Status          : " + RESET + status + "\n" + CYAN
				+ "Assigned Police : " + RESET + assignedPolice + "\n" + WHITE + "Notes           : " + RESET
				+ investigationNotes + "\n" + GREEN + "Date            : " + RESET + date + "\n" + BLUE
				+ "Day             : " + RESET + dayName + "\n" + RED + "Punishment      : " + RESET + punishment + "\n"
				+ CYAN + "══════════════════════════════════════════\n" + RESET;
	}

	// ===== GETTERS =====
	public int getCrimeId() {
		return crimeId;
	}

	public String getName() {
		return name;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public String getLocation() {
		return location;
	}

	public String getStatus() {
		return status;
	}

	public String getInvestigationNotes() {
		return investigationNotes;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDayName() {
		return dayName;
	}

	public String getAssignedPolice() {
		return assignedPolice;
	}

	// ===== SETTERS =====
	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setCrimeId(int crimeId) {
		this.crimeId = crimeId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setInvestigationNotes(String investigationNotes) {
		this.investigationNotes = investigationNotes;
	}

	public void setAssignedPolice(String assignedPolice) {
		this.assignedPolice = assignedPolice;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public void setName(String name2) {
		this.name = name2;

	}

	public String getPunishment() {
		return punishment;
	}

	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}
}
