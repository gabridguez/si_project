package jag.webapp.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;

public class DateConverter implements Converter {

	@Override
	public Object coerceToBean(Object value, Component comp, BindContext ctx) {
		// return LocalDate
		final String format = (String) ctx.getConverterArg("format");
		if (format == null)
			throw new NullPointerException("format attribute not found");
		try {
			if (value == null) {
				return null;
			} else {
				Date input = (Date) value;
				return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
		} catch (Exception e) {
			throw UiException.Aide.wrap(e);
		}
	}

	/**
	 * Convert int to date.
	 * 
	 * @param value
	 *            date in data form
	 * @param comp
	 *            associated component
	 * @param ctx
	 *            bind context for associate Binding and extra parameter (e.g.
	 *            format)
	 * @return the converted date as Integer
	 */
	@Override
	public Object coerceToUi(Object value, Component comp, BindContext ctx) {
		// return Date
		final String format = (String) ctx.getConverterArg("format");
		if (format == null)
			throw new NullPointerException("format attribute not found");
		try {
			if (value == null) {
				return null;
			} else {
				LocalDate input = (LocalDate) value;
				return Date.from(input.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
		} catch (Exception e) {
			throw UiException.Aide.wrap(e);
		}
	}

}
