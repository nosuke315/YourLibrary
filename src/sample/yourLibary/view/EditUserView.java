package sample.yourLibary.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;

import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@SuppressWarnings("deprecation")
@ManagedBean(name="editUserView")
@ViewScoped
public class EditUserView {
	private String account;
	private String name;
	private String password;
	private String email;
	private boolean isAdmin;
	private List<User> users;
	private IdEntityListDataModel<User> userModel;
	private List<User>  selectedUsers;
	private String newPassword;

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public IdEntityListDataModel<User> getUserModel() {
		return userModel;
	}
	public void setUserModel(IdEntityListDataModel<User> userModel) {
		this.userModel = userModel;
	}
	public List<User> getSelectedUsers() {
		return selectedUsers;
	}
	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@PostConstruct
	public void init() {
		users = UserManager.findAll();
		userModel = new IdEntityListDataModel<User>(UserManager.findAll());
	}
	public String addUser() {
		if(account == null || name == null)
			return null;
		User user = UserManager.createUser(account,name);
		user.setPassword(password);
		user.setEmail(email);
		user.setAdmin(isAdmin);
		user = UserManager.updateUser(user);
		users = UserManager.findAll();
		return "succes";
	}
	public String removeUser() {
		if(selectedUsers == null || selectedUsers.isEmpty())
			return "success";
		UserManager.removeUser(selectedUsers);
		users.removeAll(selectedUsers);
		userModel.setWrappedData(users);
		ViewUtil.AddMessage("ユーザの削除",selectedUsers.size()+"件のユーザーを削除");
		isSelected = false;
		return "succes";
	}
	public boolean isSelected;
	public boolean getIsSelected() {
		return isSelected;
	}
	public void handleSelect(SelectEvent event) {
		isSelected = (getSelectedUsers().size()>0);
	}
	public void handleUnSelect(SelectEvent event) {
		isSelected = (getSelectedUsers().size()>0);
	}
	public void handleToggleSelect(ToggleSelectEvent event) {
		isSelected = (getSelectedUsers().size()>0);
	}
	public String updatePassword() {
		if(selectedUsers==null || selectedUsers.isEmpty())
			return "success";
		User user = selectedUsers.get(0);
		if(newPassword.equals(user.getPassword()))
			return "success";
		user.setPassword(newPassword);
		UserManager.updateUser(user);
		ViewUtil.AddMessage("change password", user.getName()+"changed password!");
		return "success";
	}
	public void onRowEdit(RowEditEvent event) {
		User user=(User)event.getObject();
		UserManager.updateUser(user);
		ViewUtil.AddMessage("ユーザの編集", "ユーザ"+ user.getName() + "を更新");
	}
	public void onRowEditCancel(RowEditEvent event) {
		ViewUtil.AddMessage("ユーザの編集", "ユーザの編集をキャンセル");
	}
}
