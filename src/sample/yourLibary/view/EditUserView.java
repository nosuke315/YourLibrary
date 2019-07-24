package sample.yourLibary.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@ManagedBean(name="editUserView")
@ViewScoped
@Setter
@Getter
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
	public boolean isSelected;

	@PostConstruct
	public void init() {
		users = UserManager.findAll();
		userModel = new IdEntityListDataModel<User>(UserManager.findAll());
	}
	/* ユーザーの追加 */
	public String addUser() {
		if(account == null || name == null)
			return null;
		User user = UserManager.createUser(account,name);
		user.setPassword(password);
		user.setEmail(email);
		user.setAdmin(isAdmin);
		user = UserManager.updateUser(user);
		users = UserManager.findAll();
		//reset処理
		this.setPassword("");
		this.setEmail("");
		this.setAccount("");
		this.setName("");
		this.setAdmin(false);
		return "success";
	}
	/* ユーザーの削除 */
	public String removeUser() {
		if(selectedUsers == null || selectedUsers.isEmpty())
			return "success";
		UserManager.removeUser(selectedUsers);
		users.removeAll(selectedUsers);
		userModel.setWrappedData(users);
		ViewUtil.AddMessage("ユーザの削除",selectedUsers.size()+"件のユーザーを削除");
		isSelected = false;
		return "success";
	}
	//isSelectedのSetter
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

	/* パスワードのUpdate */
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
