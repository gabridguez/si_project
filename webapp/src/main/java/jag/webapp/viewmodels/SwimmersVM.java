package jag.webapp.viewmodels;

import swim.entities.Club;
import swim.entities.Swimmer;
import swim.daos.ClubDAO;
//import swim.entities.TransactionUtils;
//import swim.entities.TransactionUtils;
import swim.daos.SwimmerDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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



	private Swimmer currentSwimmer=null;

	private SwimmerDAO sDAO;
	
	private ClubDAO cDAO;

	// modal dialog to edit swimmer
	

	
	
	ListModelList clubs;//= new ListModelList(Arrays.asList(cDAO.findAllClubs()));
	
	
	public SwimmersVM() {
		//this.dateConverter = new DateConverter();
		this.sDAO = new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.cDAO = new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.swimmers = (Set<Swimmer>) sDAO.findAll();
		this.clubs= new ListModelList<>(cDAO.findAll()); //(ListModelList)(Set<Club>) cDAO.findAllClubs();

	}

	//public DateConverter getDateConverter() {
	//	return dateConverter;
	//}

	public ListModelList getClubs() {
		return clubs;
	}

	

	public Set<Swimmer> getSwimmers() {
		return (Set<Swimmer>) sDAO.findAll();
	}


	public int getSwimmersCount() {
		return this.getSwimmers().size();
	}

	public Swimmer getCurrentSwimmer() {
		//System.out.println(this);
		return currentSwimmer;
	}

	public void setCurrentSwimmer(Swimmer currentSwimmer) {
		this.currentSwimmer = currentSwimmer;
	}

	@Command
	@NotifyChange("swimmers")
	public void removeSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		sDAO.remove(swimmer.getId());
		// this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	/*@Command
	@NotifyChange("currentSwimmer")
	public void editSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		System.out.println(this);
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
		//modalDialog.detach();
	}

	@Command
	@NotifyChange("swimmers")
	public void submitSwimmer() {
		this.sDAO.createSwimmer(this.newSwimmer);
		// this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		UserInterfaceUtils.showNotification("Swimmer " + this.newSwimmer.getName() + " was created.", "Creating...");
		initNewSwimmer();
	}*/

	@Command
	@NotifyChange("currentSwimmer")
	public void newSwimmer() {
		this.currentSwimmer = new Swimmer();
	}
	@Command
	@NotifyChange("currentSwimmer")
	public void cancel() {
		this.currentSwimmer = null;
	}
	
	@Command
	@NotifyChange("currentSwimmer")
	public void editSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		this.currentSwimmer = swimmer;
	}
	
	@Command
	@NotifyChange({"swimmers", "currentSwimmer"})
	public void save() {
		this.sDAO.create(this.currentSwimmer);
		this.currentSwimmer = null;
	}

	
	public List<Boolean> getPossibleSex() {
		return Arrays.asList(true, false);
	}
	
	public List<Integer> getPossibleYears() {
		List<Integer> years = new ArrayList<>();
		for (int i = 1950; i<2025; i++) {
			years.add(i);
		}
		return years;
	}

}
  