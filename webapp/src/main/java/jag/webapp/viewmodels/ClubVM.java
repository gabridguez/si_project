    package jag.webapp.viewmodels;

    import swim.entities.Club;
    import swim.daos.ClubDAO;

    import java.util.HashSet;
    import java.util.Set;

    import org.zkoss.bind.annotation.BindingParam;
    import org.zkoss.bind.annotation.Command;
    import org.zkoss.bind.annotation.NotifyChange;

    import jag.webapp.utils.DateConverter;
    import jag.webapp.utils.DesktopEntityManagerManager;

    public class ClubVM {

    	private Set<Club> clubs;

    	private DateConverter dateConverter;

    	private Club club;

    	private ClubDAO clubDAO;

    	public ClubVM(){
    		this.dateConverter=new DateConverter();
    		this.clubDAO=new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
    		this.clubs=(Set<Club>) clubDAO.findAllClubs();

    	}

    	public DateConverter getDateConverter() {
    		return dateConverter;
    	}

    	public Set<Club> getClubs(){
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
    	public void removeClub(@BindingParam("club") Club club){
    		clubDAO.removeClub(club.getId());
    		this.clubs=(Set<Club>) clubDAO.findAllClubs();
    	}

    	@Command
    	@NotifyChange("clubs")
    	public void submitClub() {
    		this.clubDAO.createClub(this.club);
    		this.clubs=(Set<Club>) clubDAO.findAllClubs();
    		initNewClub();
    	}


    	public void initNewClub(){
    		this.club=new Club();

    	}

    }
