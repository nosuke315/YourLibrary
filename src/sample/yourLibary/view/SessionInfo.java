package sample.yourLibary.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import sample.yourLibrary.entity.User;

@ManagedBean(name="sessionInfo")
@SessionScoped
public class SessionInfo implements Serializable{
	private static final long serialVersionUID = 9186759612086888662L;

	private User loginUser;

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}



	public String logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
		try {
			request.logout();
		}catch(ServletException e) {
			e.printStackTrace();
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public String getTheme()
	{
		if( loginUser == null || loginUser.getTheme() == null )
			return "aristo";
		return loginUser.getTheme();
	}
}
