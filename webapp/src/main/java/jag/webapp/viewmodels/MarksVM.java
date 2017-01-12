package jag.webapp.viewmodels;

import swim.entities.Event;
import swim.entities.Mark;
import swim.daos.ClubDAO;
import swim.daos.EventDAO;
import swim.daos.MarkDAO;
import swim.daos.SwimmerDAO;
import swim.daos.SwimmingPoolDAO;
import java.util.Set;

import javax.persistence.RollbackException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.MarkConverter;
import jag.webapp.utils.UserInterfaceUtils;

public class MarksVM {

	//private Set<Mark> marks;

	ListModelList swimmers;
	ListModelList clubs;
	ListModelList swimmingPools;
	ListModelList events;

	private DateConverter dateConverter;
	private MarkConverter markConverter;

	private Mark currentMark = null;

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

	/*	this.marks = (Set<Mark>) mDAO.findAll(); */
		this.swimmers = new ListModelList<>(sDAO.findAll());
		this.clubs = new ListModelList<>(cDAO.findAll());
		this.swimmingPools = new ListModelList<>(spDAO.findAll());
		this.events = new ListModelList<>(eDAO.findAll());
	 
	}

	public DateConverter getDateConverter() {
		return dateConverter;
	}

	public MarkConverter getMarkConverter() {
		return markConverter;
	}

	public Set<Mark> getMarks() {
		return (Set<Mark>) mDAO.findAll();
	}

/*	public void setMarks(Set<Mark> Marks) {
		this.marks = Marks;
	}
*/
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
	public void removeMark(@BindingParam("mark") Mark mark) {
		
		try{
			mDAO.remove(mark.getId());
			UserInterfaceUtils.showNotification("Removed.", "Removing...");
		}
		catch (RollbackException e) {
			
			UserInterfaceUtils.showError("No se puede eliminar debido a una dependencia","Error.");
		}
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
	@NotifyChange({ "marks", "currentMark" })
	public void save() {
		this.mDAO.create(this.currentMark);
		this.currentMark = null;
	}

}
