package jag.webapp.viewmodels;

import swim.entities.Club;
import swim.entities.Swimmer;
import swim.daos.ClubDAO;
//import swim.entities.TransactionUtils;
//import swim.entities.TransactionUtils;
import swim.daos.SwimmerDAO;

import java.util.Arrays;
//import java.util.HashSet;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class SwimmersVM {

	private Set<Swimmer> swimmers;

	//private DateConverter dateConverter;

	private Swimmer newSwimmer;

	private Swimmer currentSwimmer;

	private SwimmerDAO sDAO;
	
	private ClubDAO cDAO;

	// modal dialog to edit swimmer
	@Wire
	Window modalDialog;

	
	
	ListModelList clubs;//= new ListModelList(Arrays.asList(cDAO.findAllClubs()));
	
	
	public SwimmersVM() {
		//this.dateConverter = new DateConverter();
		this.sDAO = new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.cDAO = new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.swimmers = (Set<Swimmer>) sDAO.findAllSwimmers();
		this.clubs= new ListModelList<>(cDAO.findAllClubs()); //(ListModelList)(Set<Club>) cDAO.findAllClubs();

	}

	//public DateConverter getDateConverter() {
	//	return dateConverter;
	//}

	public ListModelList getClubs() {
		return clubs;
	}

	public void setClubs(ListModelList clubs) {
		this.clubs = clubs;
	}

	public Set<Swimmer> getSwimmers() {
		return (Set<Swimmer>) sDAO.findAllSwimmers();
	}

	public Swimmer getNewSwimmer() {
		return newSwimmer;
	}

	public void setNewSwimmer(Swimmer newSwimmer) {
		this.newSwimmer = newSwimmer;
	}

	public int getSwimmersCount() {
		return this.getSwimmers().size();
	}

	public Swimmer getCurrentSwimmer() {
		return currentSwimmer;
	}

	public void setCurrentSwimmer(Swimmer currentSwimmer) {
		this.currentSwimmer = currentSwimmer;
	}

	@Command
	@NotifyChange("swimmers")
	public void removeSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		sDAO.removeSwimmer(swimmer.getId());
		// this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	@Command
	@NotifyChange("currentSwimmer")
	public void editSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		this.currentSwimmer = swimmer;
		System.out.println("EDIT SWIMMER "+swimmer.getName()+"nombre current: "+this.currentSwimmer.getName());
		
		this.modalDialog = (Window) Executions.createComponents("swimmer_dialog.zul", null, null);
		this.modalDialog.doModal();
	}

	
	@Command
	@NotifyChange("swimmers, currentSwimmer, modalDialog")
	public void updateSwimmer() {
		this.sDAO.createSwimmer(this.currentSwimmer);
		// this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		UserInterfaceUtils.showNotification("Swimmer " + this.newSwimmer.getName() + " was edited.", "Editing...");
		this.currentSwimmer = null;
		modalDialog.detach();
	}

	@Command
	@NotifyChange("swimmers")
	public void submitSwimmer() {
		this.sDAO.createSwimmer(this.newSwimmer);
		// this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		UserInterfaceUtils.showNotification("Swimmer " + this.newSwimmer.getName() + " was created.", "Creating...");
		initNewSwimmer();
	}

	public void initNewSwimmer() {
		this.newSwimmer = new Swimmer();

	}

}
