package com.aoi.meeting.demo;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestController
@RequestMapping("/meeting")
public class Login {
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Event meeting( @Valid AuthenticationToken authenticationToken)
	{
		  RestTemplate restTemplate=new RestTemplate();
		System.out.println("Inside meeting ");
		
		String authenticationUrl="https://login.microsoftonline.com/e15d5fc2-6c27-4734-85ea-44c315031dbf/oauth2/v2.0/token";
		String calendarUrl="https://graph.microsoft.com/v1.0/users/9755c699-1184-43c0-8787-25d165009ee2/calendar/calendarView";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      
		String tokenValue=null;

       MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
       map.add("client_id", "57a13bb3-0b05-4412-9801-7ecb14b937f0");
       map.add("scope", "https://graph.microsoft.com/.default");
       map.add("client_secret", "D3MQNDIGbV4BTZNB7M_le5?X.zUr:Z3:");
       map.add("grant_type", "client_credentials");

       HttpEntity<MultiValueMap<String, String>> request1 = new HttpEntity<MultiValueMap<String, String>>(map, headers);
       System.out.println("authenticationUrl ----  "+ authenticationUrl);
       System.out.println("request1 ----  "+ request1);
      
       ResponseEntity<Token> response  = restTemplate.exchange( authenticationUrl, HttpMethod.POST, request1, Token.class );
    
        Token token=response.getBody();
        if(token!=null)
        {
        	 tokenValue=token.getAccess_token();
        }
        System.out.println("Token"+ token.toString());
       
        //To get the calendar view.
        
        HttpHeaders headersView = new HttpHeaders();
		headersView.setContentType(MediaType.TEXT_HTML);
		headersView.add("Authorization", tokenValue);
		HttpEntity<String> requestEntity = new HttpEntity<String>( headersView);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(calendarUrl)
		        
		        .queryParam("startDateTime", getCurrentISODate())
		        .queryParam("endDateTime", getNextISODate());

		
		
		ResponseEntity<String> resp=restTemplate.exchange(builder.toUriString() , HttpMethod.GET,
		        requestEntity, String.class);

		System.out.println("resp"+ resp.getBody());
		JSONObject jsonObj = new JSONObject(resp.getBody());
		JSONArray arr = jsonObj.getJSONArray("value");
		Event event=new Event();
		List<CalendarView> calViewList=new ArrayList<CalendarView>();
		for(int i=0;i<arr.length();i++)
		{
			CalendarView calView=new CalendarView();
			String subject=arr.getJSONObject(i).getString("subject");
			calView.setSubject(subject);
			String startDateTime= arr.getJSONObject(i).getJSONObject("start").getString("dateTime");
			calView.setStartDateTime(startDateTime);
			String endDateTime=arr.getJSONObject(i).getJSONObject("end").getString("dateTime");
			calView.setEndDateTime(endDateTime);
			String location=arr.getJSONObject(i).getJSONObject("location").getString("displayName");
			calView.setLocation(location);
			String organizerName =arr.getJSONObject(i).getJSONObject("organizer").getJSONObject("emailAddress").getString("name");
			calView.setOrganizerName(organizerName);
			String organizerEmailId =arr.getJSONObject(i).getJSONObject("organizer").getJSONObject("emailAddress").getString("address");
			calView.setOrganizerEmailId(organizerEmailId);
			System.out.println("calView"+ calView);
			calViewList.add(calView);
			
		
		
			
		}
		event.setList(calViewList);
        
        return event;
		
	}
	
	public String getCurrentISODate()
	{
		 Calendar c = new GregorianCalendar();
		//Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    Date d1 = c.getTime();
		TimeZone tz = TimeZone.getTimeZone("EST");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.0000000"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		String nowAsISO = df.format(d1);
		System.out.println("Start----"+nowAsISO);
		return nowAsISO;
	}
	public String getNextISODate()
	{
		
		Date dt1 = new Date();
		Calendar c = Calendar.getInstance();
		//Calendar c = Calendar.getInstance(TimeZone.getTimeZone("EST"));
		c.setTime(dt1); 
		c.add(Calendar.DATE, 1);
		dt1 = c.getTime();
		
		TimeZone tz = TimeZone.getTimeZone("EST");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.0000000"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		String nextAsISO = df.format(dt1);
		System.out.println("end----"+nextAsISO);
		return nextAsISO;
	}
}
