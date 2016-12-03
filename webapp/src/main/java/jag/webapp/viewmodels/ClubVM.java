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

    	private Set<Swimmer> swimmers;

    	private DateConverter dateConverter;

    	private Swimmer newSwimmer;

    	private ClubDAO sDAO;

    	public ClubVM(){
    		this.dateConverter=new DateConverter();
    		this.sDAO=new ClubDAO(DesktopEntityManagerManager.getDesktopEntityManager());
    		this.Clubs=(Set<Club>) sDAO.findAllClubs();

    	}

    	public DateConverter getDateConverter() {
    		return dateConverter;
    	}

    	public Set<Club> getClubs(){
    		return this.Clubs;//Set<Club>) sDAO.findAllClubs();
    	}


    	public Club getNewClub() {
    		return newClub;
    	}

    	public void setNewClub(Club newClub) {
    		this.newClub = newClub;
    	}

    	public int getClubsCount() {
    		return this.getClubs().size();
    	}

    	@Command
    	@NotifyChange("Clubs")
    	public void removeClub(@BindingParam("Club") Club club){
    		sDAO.removeClub(Club.getId());
    		this.Clubs=(Set<Club>) sDAO.findAllClubs();
    	}

    	@Command
    	@NotifyChange("cluba")
    	public void submitClub() {
    		this.sDAO.createClub(this.newClub);
    		this.Clubs=(Set<Club>) sDAO.findAllClubs();
    		initNewClub();
    	}


    	public void initNewClub(){
    		this.newClub=new Club();

    	}

    }
