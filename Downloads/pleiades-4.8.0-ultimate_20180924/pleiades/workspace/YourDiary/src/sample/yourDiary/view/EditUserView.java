package sample.yourDiary.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import sample.yourDiary.entity.User;
import sample.yourDiary.logic.UserManager;

@SuppressWarnings("deprecation")
@ManagedBean(name="editUserView")
@ViewScoped
public class EditUserView {
	@Getter @Setter
	private String account;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String password;
	@Getter @Setter
	private String email;
	@Getter @Setter
	private boolean isAdmin;
	@Getter @Setter
	private List<User> users;

	@PostConstruct
	public void init() {users = UserManager.findAll();}
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
}
