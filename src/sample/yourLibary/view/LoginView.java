package sample.yourLibary.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@ManagedBean(name="loginView")
@ViewScoped
public class LoginView {
	private String account;
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
