package jag.webapp.viewmodels;

import swim.entities.User;
import swim.daos.UserDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import jag.webapp.utils.DateConverter;
import jag.webapp.utils.DesktopEntityManagerManager;
import jag.webapp.utils.UserInterfaceUtils;

public class UserVM {

	private Set<User> users;

	private User user;

	private User currentUser = null;

	private UserDAO userDAO;

	public UserVM() {
		
		this.userDAO = new UserDAO(DesktopEntityManagerManager.getDesktopEntityManager());
		this.users = (Set<User>) userDAO.findAll();

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	@Command
	@NotifyChange("users")
	public void removeUser(@BindingParam("user") User user) {
		userDAO.remove(user.getId());
		UserInterfaceUtils.showNotification("Removed.", "Removing...");
	}

	@Command
	@NotifyChange("currentUser")
	public void newUser() {
		this.currentUser = new User();
	}

	@Command
	@NotifyChange("currentUser")
	public void cancel() {
		this.currentUser = null;
	}

	@Command
	@NotifyChange("currentUser")
	public void editUser(@BindingParam("user") User user) {
		this.currentUser = user;
	}

	@Command
	@NotifyChange({ "users", "currentUser" })
	public void save() {
		this.userDAO.create(this.currentUser);
		this.currentUser = null;
		
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public User getNewUser() {
		return this.user;
	}

	public void setNewUser(User newUser) {
		this.user = newUser;
	}

	public int getUsersCount() {
		return this.getUsers().size();
	}

	public List<String> getPossibleTypeUser() {
		
		return Arrays.asList(User.UserType.values()[0].toString(), User.UserType.values()[1].toString());
	}
	
	@Command
	@NotifyChange("users")
	public void submitUser() {
		this.userDAO.create(this.user);
		this.users = (Set<User>) userDAO.findAll();
		initNewUser();
	}

	public void initNewUser() {
		this.user = new User();

	}

}
