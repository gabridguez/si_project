package jag.webapp.utils;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;

public class DateConverter implements Converter{

	@Override
	public Object coerceToBean(Object value, Component comp, BindContext ctx) {
		// TODO Auto-generated method stub
		
		final String format = (String) ctx.getConverterArg("format");
        if (format == null) throw new NullPointerException("format attribute not found");
        Calendar toret=Calendar.getInstance();
		
        try {
        	if(value==null){
        		return null;
        	}else{
        		toret.setTime((Date) value);
        		return toret.get(Calendar.YEAR);
        	}
        	
            //return value == null ? null : toret.setTime((Date) value);
        } catch (Exception e) {
            throw UiException.Aide.wrap(e);
        }
		
		//long aux=(long)value;
		//toret.set((Integer)value, 1, 1);
		
		//return toret.getTime();
		
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
		
		 final String format = (String) ctx.getConverterArg("format");
	        if (format == null) throw new NullPointerException("format attribute not found");
	        Calendar toret=Calendar.getInstance();
			
	        try {
	        	if(value==null){
	        		return null;
	        	}else{
	        		toret.set((int) value, 1, 1);//((int) value,1,1);
	        		return toret;
	        	}
	        	
	            //return value == null ? null : toret.setTime((Date) value);
	        } catch (Exception e) {
	            throw UiException.Aide.wrap(e);
	        }
		// TODO Auto-generated method stub
		
		//return toret.get(Calendar.YEAR);
	}

}
