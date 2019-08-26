package sample.yourLibrary.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@ManagedBean(name="changePasswordView")
@ViewScoped
@Setter
@Getter
public class ChangePasswordView {

	String newPassword;
	/* パスワードのUpdate */
	public String changePassword() {
		SessionInfo sessioninfo = ViewUtil.getSessionInfo();
		User user = sessioninfo.getLoginUser();
		//同じ場合は非更新
		if(newPassword.equals(user.getPassword()))
			return "success";
		user.setPassword(newPassword);
		UserManager.updateUser(user);
		ViewUtil.AddMessage("change password", "" + user.getName()+" : changed password!");
		return "success";
	}

}
