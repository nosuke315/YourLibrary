package sample.yourLibary.view;

import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@ManagedBean(name="changeThemeView")
@ViewScoped
@Getter
@Setter
public class ChangeThemeView {
	private String selectedTheme;
	private TreeMap<String, String> themes;

	public String getSelectedTheme() {
		return selectedTheme;
	}

	public void setSelectedTheme(String selectedTheme) {
		this.selectedTheme = selectedTheme;
	}

	public TreeMap<String, String> getThemes() {
		return themes;
	}

	public void setThemes(TreeMap<String, String> themes) {
		this.themes = themes;
	}

	@PostConstruct
	public void init()
	{
        themes = new TreeMap<String, String>();
        themes.put("Afterdark", "afterdark");
        themes.put("Afternoon", "afternoon");
        themes.put( "Afterwork", "afterwork");
        themes.put( "Aristo", "aristo");
        themes.put( "Black-Tie", "black-tie");
        themes.put( "Blitzer", "blitzer");
        themes.put( "Bluesky", "bluesky");
        themes.put( "Bootstrap", "bootstrap");
        themes.put( "Casablanca", "casablanca");
        themes.put( "Cupertino", "cupertino");
        themes.put( "Cruze", "cruze");
        themes.put( "Dark-Hive", "dark-hive");
        themes.put( "Delta", "delta");
        themes.put( "Dot-Luv", "dot-luv");
        themes.put( "Eggplant", "eggplant");
        themes.put( "Excite-Bike", "excite-bike");
        themes.put( "Flick", "flick");
        themes.put( "Glass-X", "glass-x");
        themes.put( "Home", "home");
        themes.put( "Hot-Sneaks", "hot-sneaks");
        themes.put( "Humanity", "humanity");
        themes.put( "Le-Frog", "le-frog");
        themes.put( "Midnight", "midnight");
        themes.put( "Mint-Choc", "mint-choc");
        themes.put( "Overcast", "overcast");
        themes.put( "Pepper-Grinder", "pepper-grinder");
        themes.put( "Redmond", "redmond");
        themes.put( "Rocket", "rocket");
        themes.put( "Sam", "sam");
        themes.put( "Smoothness", "smoothness");
        themes.put( "South-Street", "south-street");
        themes.put( "Start", "start");
        themes.put( "Sunny", "sunny");
        themes.put( "Swanky-Purse", "swanky-purse");
        themes.put( "Trontastic", "trontastic");
        themes.put( "UI-Darkness", "ui-darkness");
        themes.put( "UI-Lightness", "ui-lightness");
        themes.put( "Vader", "vader");

        selectedTheme = ViewUtil.getSessionInfo().getLoginUser().getTheme();
	}

	public String saveTheme()
	{
		SessionInfo sessionInfo = ViewUtil.getSessionInfo();
		User loginUser = sessionInfo.getLoginUser();
		loginUser.setTheme(selectedTheme);
		UserManager.updateUser(loginUser);
		ViewUtil.AddMessage("テーマ変更", "選択されたテーマを適用しました");
		return "success";
	}
}