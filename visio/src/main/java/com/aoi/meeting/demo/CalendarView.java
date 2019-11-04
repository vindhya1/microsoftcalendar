package com.aoi.meeting.demo;

import java.util.ArrayList;

public class CalendarView {
	
	
	private String subject;
	private String startDateTime;
	private String endDateTime;
	private String location;
	private String organizerName;
	private String organizerEmailId;
	public String getOrganizerEmailId() {
		return organizerEmailId;
	}


	public void setOrganizerEmailId(String organizerEmailId) {
		this.organizerEmailId = organizerEmailId;
	}


	public String getOrganizerName() {
		return organizerName;
	}
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}


	@Override
	public String toString() {
		return "CalendarView [subject=" + subject + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime
				+ ", location=" + location + ", organizerName=" + organizerName + ", organizerEmailId="
				+ organizerEmailId + "]";
	}
	
	
	


		

}
