package jag.webapp.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;

public class MarkConverter implements Converter{

	
	@Override
	public Object coerceToBean(Object value, Component comp, BindContext ctx) {
		//return long
		
		final String format = (String) ctx.getConverterArg("format");
        if (format == null) throw new NullPointerException("format attribute not found");
       
		
        try {
        	if(value==null){
        		return null;
        	}else{
        		String input=(String) value;
        		//input=input.substring(6);
        		String[] splits= input.split(":");
        		long minutos=  Long.parseLong(splits[0]);
        		splits=splits[1].split(Pattern.quote("."));
        		long segundos= Long.parseLong(splits[0]);
        		long decimas= Long.parseLong(splits[1]);
        		return (minutos*6000)+(segundos*100)+(decimas);
        	}
        	
            
        } catch (Exception e) {
            throw UiException.Aide.wrap(e);
        }
		
		
		
	}

	
	
	
	/**
     * Convert int to date.
     * @param value date in data form
     * @param comp associated component
     * @param ctx bind context for associate Binding and extra parameter (e.g. format)
     * @return the converted date as Integer
     */
	@Override
	public Object coerceToUi(Object value, Component comp, BindContext ctx) {
		//return string
		 final String format = (String) ctx.getConverterArg("format");
	        if (format == null) throw new NullPointerException("format attribute not found");
	        
			
	        try {
	        	if(value==null){
	        		return null;
	        	}else{
	        		long decimas=(long)value;
	        		long segundos=decimas/100;
	        		decimas=decimas%100;
	        		long minutos=segundos/60;
	        		segundos =segundos%60;
	        		return /*"time: "+*/minutos+":"+segundos+"."+decimas;
	        	}
	        	
	            
	        } catch (Exception e) {
	            throw UiException.Aide.wrap(e);
	        }
		
	}
}
