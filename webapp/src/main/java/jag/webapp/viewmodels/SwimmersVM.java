package jag.webapp.viewmodels;


import swim.entities.Swimmer;
//import swim.entities.TransactionUtils;
import swim.daos.SwimmerDAO;

import java.util.HashSet;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import jag.webapp.utils.DesktopEntityManagerManager;

public class SwimmersVM {

	private Set<Swimmer> swimmers= new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager()).findAllSwimmers();
	// new HashSet<Swimmer>();
	
	
	private SwimmerDAO sDAO;
	
	public SwimmersVM(){
		//sDAO=new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		//this.swimmers=(Set<Swimmer>) new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager()).findAllSwimmers();
		
	}
	
	public Set<Swimmer> getSwimmers(){
		return this.swimmers;//Set<Swimmer>) sDAO.findAllSwimmers();
	}
	
	public int getSwimmersCount() {
		return this.getSwimmers().size();
	}
	
	@Command
	@NotifyChange("sData")
	public void removeSwimmer(@BindingParam("swimmer") Swimmer swimmer){
		//sDAO.removeSwimmer(swimmer.getId());
	}
	
	
}
