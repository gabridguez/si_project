package jag.webapp.viewmodels;

import swim.entities.Club;
import swim.daos.ClubDAO;
import java.util.Set;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class ClubVM {

	private Set<Club> clubs;

	private DateConverter dateConverter;

	private Club club;

	private Club currentClub = null;

	private ClubDAO clubDAO;

	public ClubVM() {
		this.dateConverter = new DateConverter();
		this.clubDAO = new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.clubs = (Set<Club>) clubDAO.findAll();

	}

	public Club getCurrentClub() {
		return currentClub;
	}

	public void setCurrentClub(Club currentClub) {
		this.currentClub = currentClub;
	}

	@Command
	@NotifyChange("Clubs")
	public void removeClub(@BindingParam("club") Club club) {
		clubDAO.remove(club.getId());
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	@Command
	@NotifyChange("currentClub")
	public void newClub() {
		this.currentClub = new Club();
	}

	@Command
	@NotifyChange("currentClub")
	public void cancel() {
		this.currentClub = null;
	}

	@Command
	@NotifyChange("currentClub")
	public void editClub(@BindingParam("club") Club club) {
		this.currentClub = club;
	}

	@Command
	@NotifyChange({ "Clubs", "currentClub" })
	public void save() {
		this.clubDAO.create(this.currentClub);
		this.currentClub = null;
	}

	public DateConverter getDateConverter() {
		return dateConverter;
	}

	public Set<Club> getClubs() {
		return this.clubs;
	}

	public Club getNewClub() {
		return this.club;
	}

	public void setNewClub(Club newClub) {
		this.club = newClub;
	}

	public int getClubsCount() {
		return this.getClubs().size();
	}

	@Command
	@NotifyChange("clubs")
	public void submitClub() {
		this.clubDAO.create(this.club);
		this.clubs = (Set<Club>) clubDAO.findAll();
		initNewClub();
	}

	public void initNewClub() {
		this.club = new Club();

	}

}
