package sample.yourDiary.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import sample.yourDiary.entity.User;
import sample.yourDiary.logic.UserManager;

@SuppressWarnings("deprecation")
@ManagedBean(name="loginView")
@ViewScoped
public class LoginView {
	@Getter @Setter
	private String account;
	@Getter @Setter
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
