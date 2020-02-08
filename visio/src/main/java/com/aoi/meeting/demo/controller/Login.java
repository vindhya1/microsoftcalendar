package com.aoi.meeting.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aoi.meeting.demo.Entity.Event;
import com.aoi.meeting.demo.service.CalendarDataService;
import com.aoi.meeting.demo.service.ProfileService;
import com.aoi.meeting.demo.service.TokenAuthenticationService;
import com.aoi.meeting.demo.vo.Profile;

@RestController
@RequestMapping("/meeting")

public class Login {
	@Autowired
	TokenAuthenticationService tokenAuthenticationService;
	@Autowired
	ProfileService profileService;
	@Autowired
	CalendarDataService calendarDataService;
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Event meeting( @Valid AuthenticationToken authenticationToken,@RequestParam String email)
	{
		Event event=null;
		
		System.out.println("Inside meeting ");
		System.out.println("Email "+ email);
		if(email!=null)
		{
		//Get the token
		String tokenValue =tokenAuthenticationService.getTokenData();
		System.out.println("TokenValue"+tokenValue);
		//To get Profile ID
		String id=profileService.getClientId(email);
		if(id!=null)
		{
		
		System.out.println("id"+id);
		//To get the calendar view.
		event=calendarDataService.getCalendarInfo(tokenValue,id);
		System.out.println("event" +event);
		return event;
		}
		}
		
        return event;
        
       		
	}
	
	
}
