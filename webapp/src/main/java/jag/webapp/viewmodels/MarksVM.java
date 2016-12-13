package jag.webapp.viewmodels;

import swim.entities.Club;
import swim.entities.Event;
import swim.entities.Mark;
import swim.entities.Swimmer;
import swim.entities.SwimmingPool;
import swim.daos.ClubDAO;
import swim.daos.EventDAO;
import swim.daos.MarkDAO;
//import swim.entities.TransactionUtils;
//import swim.entities.TransactionUtils;
import swim.daos.SwimmerDAO;
import swim.daos.SwimmingPoolDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.HashSet;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.MarkConverter;
import jag.webapp.utils.UserInterfaceUtils;

public class MarksVM {

	private Set<Mark> marks;
	
	ListModelList swimmers;
	ListModelList clubs;
	ListModelList swimmingPools;
	ListModelList events;
	
	private DateConverter dateConverter;
	private MarkConverter markConverter;
	
	private Mark currentMark=null;

	private MarkDAO mDAO;
	private SwimmerDAO sDAO;
	private ClubDAO cDAO;
	private SwimmingPoolDAO spDAO;
	private EventDAO eDAO;
	
	
	public MarksVM() {
		this.dateConverter = new DateConverter();
		this.markConverter = new MarkConverter();
		this.mDAO = new MarkDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.sDAO = new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.cDAO = new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.spDAO = new SwimmingPoolDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.eDAO = new EventDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		
		this.marks = (Set<Mark>) mDAO.findAll();
		this.swimmers = new ListModelList<>(sDAO.findAll());
		this.clubs = new ListModelList<>(cDAO.findAll());
		this.swimmingPools = new ListModelList<>( spDAO.findAll());
		this.events = new ListModelList<>( eDAO.findAll());

	}

	public DateConverter getDateConverter() {
			return dateConverter;
		}
		
		
	public MarkConverter getMarkConverter() {
		return markConverter;
	}

	public Set<Mark> getMarks() {
		return marks;
	}

	public void setMarks(Set<Mark> Marks) {
		this.marks = Marks;
	}

	public Mark getCurrentMark() {
		return currentMark;
	}

	public void setCurrentMark(Mark currentMark) {
		this.currentMark = currentMark;
	}

	public ListModelList getSwimmers() {
		return swimmers;
	}

	public ListModelList getClubs() {
		return clubs;
	}

	public ListModelList getSwimmingPools() {
		return swimmingPools;
	}

	public ListModelList getEvents() {
		return events;
	}

	@Command
	@NotifyChange("marks")
	public void removeMark(@BindingParam("mark") Event mark) {
		mDAO.remove(mark.getId());
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	

	@Command
	@NotifyChange("currentMark")
	public void newMark() {
		this.currentMark = new Mark();
	}
	@Command
	@NotifyChange("currentMark")
	public void cancel() {
		this.currentMark = null;
	}
	
	@Command
	@NotifyChange("currentMark")
	public void editMark(@BindingParam("mark") Mark mark) {
		this.currentMark = mark;
	}
	
	@Command
	@NotifyChange({"marks", "currentMark"})
	public void save() {
		this.mDAO.create(this.currentMark);
		this.currentMark = null;
	}

	
	/*public List<Boolean> getPossibleChrono() {
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
	public List<Integer> getPossibleSizes(){
		List<Integer> meters = new ArrayList<>();
		meters.add(25);
		meters.add(50);
		
		return meters;
	}

	public List<Event.strokes> getPossibleStrokes(){
		
		
		
		return Arrays.asList(Event.strokes.values()); 
	}*/
}
  