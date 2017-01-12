package jag.webapp.viewmodels;

import swim.entities.Swimmer;
import swim.daos.ClubDAO;
import swim.daos.SwimmerDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.RollbackException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class SwimmersVM {

	//private Set<Swimmer> swimmers;

	private Swimmer currentSwimmer=null;

	private SwimmerDAO sDAO;
	
	private ClubDAO cDAO;
	
	ListModelList clubs;//= new ListModelList(Arrays.asList(cDAO.findAllClubs()));
	
	public SwimmersVM() {
		this.sDAO = new SwimmerDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.cDAO = new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.clubs= new ListModelList<>(cDAO.findAll()); //(ListModelList)(Set<Club>) cDAO.findAllClubs();
	}

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
		return currentSwimmer;
	}

	public void setCurrentSwimmer(Swimmer currentSwimmer) {
		this.currentSwimmer = currentSwimmer;
	}

	@Command
	@NotifyChange("swimmers")
	public void removeSwimmer(@BindingParam("swimmer") Swimmer swimmer) {
		
		try{
			sDAO.remove(swimmer.getId());
			UserInterfaceUtils.showNotification("Removed.", "Removing...");
		}
		catch (RollbackException e) {
			
			UserInterfaceUtils.showError("No se puede eliminar debido a una dependencia","Error.");
		}
	}

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
