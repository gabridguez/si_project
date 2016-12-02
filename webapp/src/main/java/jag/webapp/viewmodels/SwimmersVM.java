package jag.webapp.viewmodels;


import swim.entities.Swimmer;
//import swim.entities.TransactionUtils;
//import swim.entities.TransactionUtils;
import swim.daos.SwimmerDAO;

//import java.util.HashSet;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;

public class SwimmersVM {

	private Set<Swimmer> swimmers;
	
	private DateConverter dateConverter;
	
	private Swimmer newSwimmer;
	
	private SwimmerDAO sDAO;
	
	public SwimmersVM(){
		this.dateConverter=new DateConverter();
		this.sDAO=new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		
	}
	
	public DateConverter getDateConverter() {
		return dateConverter;
	}

	public Set<Swimmer> getSwimmers(){
		return this.swimmers;//Set<Swimmer>) sDAO.findAllSwimmers();
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
	
	@Command
	@NotifyChange("swimmers")
	public void removeSwimmer(@BindingParam("swimmer") Swimmer swimmer){
		sDAO.removeSwimmer(swimmer.getId());
		this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
	}
	
	@Command
	@NotifyChange("swimmers")
	public void submitSwimmer() {
		this.sDAO.createSwimmer(this.newSwimmer);
		this.swimmers=(Set<Swimmer>) sDAO.findAllSwimmers();
		initNewSwimmer();
	}
	
	
	public void initNewSwimmer(){
		this.newSwimmer=new Swimmer();
		
	}
	
}
