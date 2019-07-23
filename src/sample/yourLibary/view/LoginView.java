package sample.yourLibary.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@ManagedBean(name="loginView")
@ViewScoped
@Getter
@Setter
public class LoginView {
	private String account;
	private String password;

	public String login() {
		User loginUser = UserManager.login(account, password);
		if(loginUser == null) {
			ViewUtil.AddErrorMessage("ログイン失敗","アカウントまたはパスワードが一致しません");
			return "failure";
		}
		SessionInfo sessionInfo = ViewUtil.getSessionInfo();
		sessionInfo.setLoginUser(loginUser);
		return "success";
	}
}
