package jag.webapp.viewmodels;

import swim.entities.SwimmingPool;

import swim.daos.SwimmingPoolDAO;

import java.util.Set;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class SwimmingPoolVM{

	private Set<SwimmingPool> swimmingPools;


	private SwimmingPool swimmingPool;

	private SwimmingPool currentSwimmingPool = null;

	private SwimmingPoolDAO swimmingPoolDAO;

	public SwimmingPoolVM() {
	
		this.swimmingPoolDAO = new SwimmingPoolDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.swimmingPools = (Set<SwimmingPool>) swimmingPoolDAO.findAll();

	}

	public SwimmingPool getCurrentSwimmingPool() {
		return currentSwimmingPool;
	}

	public void setCurrentSwimmingPool(SwimmingPool currentSwimmingPool) {
		this.currentSwimmingPool = currentSwimmingPool;
	}

	@Command
	@NotifyChange("swimmingPools")
	public void removeSwimmingPool(@BindingParam("swimmingPool") SwimmingPool swimmingPool) {
		swimmingPoolDAO.remove(swimmingPool.getId());
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	@Command
	@NotifyChange({"currentSwimmingPool","swimmingPools"})
	public void newSwimmingPool() {
		this.currentSwimmingPool = new SwimmingPool();
	}

	@Command
	@NotifyChange("currentSwimmingPool")
	public void cancel() {
		this.currentSwimmingPool = null;
	}

	@Command
	@NotifyChange("currentSwimmingPool")
	public void editSwimmingPool(@BindingParam("swimmingPool") SwimmingPool swimmingPool) {
		this.currentSwimmingPool = swimmingPool;
	}

	@Command
	@NotifyChange({ "swimmingPools", "currentSwimmingPool" })
	public void save() {
		this.swimmingPoolDAO.create(this.currentSwimmingPool);
		this.currentSwimmingPool = null;
	}


	public Set<SwimmingPool> getSwimmingPools() {
		return this.swimmingPools;
	}

	public SwimmingPool getNewSwimmingPool() {
		return this.swimmingPool;
	}

	public void setNewSwimmingPool(SwimmingPool newSwimmingPool) {
		this.swimmingPool = newSwimmingPool;
	}

	public int getSwimmingPoolCount() {
		return this.getSwimmingPools().size();
	}

	@Command
	@NotifyChange("swimmingPools")
	public void submitSwimmingPool() {
		this.swimmingPoolDAO.create(this.swimmingPool);
		this.swimmingPools = (Set<SwimmingPool>) swimmingPoolDAO.findAll();
		initNewSwimmingPool();
	}

	public void initNewSwimmingPool() {
		this.swimmingPool = new SwimmingPool();

	}

}
