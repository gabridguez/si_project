package jag.webapp.viewmodels;

import swim.entities.Event;
import swim.daos.EventDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.RollbackException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class EventsVM {

	//private Set<Event> events;

	private Event currentEvent = null;

	private EventDAO eDAO;

	public EventsVM() {
		this.eDAO = new EventDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		//this.events = (Set<Event>) eDAO.findAll();
	}

	public Set<Event> getEvents() {
		return (Set<Event>) eDAO.findAll();
	}

/*	public void setEvents(Set<Event> events) {
		this.events = events;
	}
*/
	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	@Command
	@NotifyChange("events")
	public void removeEvent(@BindingParam("event") Event event) {
		
		try{
			eDAO.remove(event.getId());
			UserInterfaceUtils.showNotification("Removed.", "Removing...");
		}
		catch (RollbackException e) {
			
			UserInterfaceUtils.showError("No se puede eliminar debido a una dependencia","Error.");
		}
	}

	@Command
	@NotifyChange("currentEvent")
	public void newEvent() {
		this.currentEvent = new Event();
	}

	@Command
	@NotifyChange("currentEvent")
	public void cancel() {
		this.currentEvent = null;
	}

	@Command
	@NotifyChange("currentEvent")
	public void editEvent(@BindingParam("event") Event event) {
		this.currentEvent = event;
	}

	@Command
	@NotifyChange({ "events", "currentEvent" })
	public void save() {
		this.eDAO.create(this.currentEvent);
		this.currentEvent = null;
	}

	public List<Boolean> getPossibleChrono() {
		return Arrays.asList(true, false);
	}

	public List<Boolean> getPossibleLap() {
		return Arrays.asList(true, false);
	}

	public List<Integer> getPossibleMeters() {
		List<Integer> meters = new ArrayList<>();
		meters.add(50);
		meters.add(100);
		meters.add(200);
		meters.add(400);
		meters.add(800);
		meters.add(1500);
		return meters;
	}

	public List<Integer> getPossibleSizes() {
		List<Integer> meters = new ArrayList<>();
		meters.add(25);
		meters.add(50);
		return meters;
	}

	public List<Event.strokes> getPossibleStrokes() {
		return Arrays.asList(Event.strokes.values());
	}
}
