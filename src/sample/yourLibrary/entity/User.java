package sample.yourLibrary.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERTBL")
public class User implements IdEntity{
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	@Column(name="ACCOUNT",nullable=false,unique=true)
	private String account;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="NAME",nullable=false)
	private String name;
	@Column(name="E_MAIL")
	private String email;
	@Column(name="IS_ADMIN")
	private boolean isAdmin = false;
	@Column(name="THEME")
	private String theme;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	@OneToMany(mappedBy="lendUser")
	List<LendHistory> lendHistories;


	public List<LendHistory> getLendHistories() {
		return lendHistories;
	}

	public void setLendHistories(List<LendHistory> lendHistories) {
		this.lendHistories = lendHistories;
	}

	public void addLendHistory(LendHistory history) {
		lendHistories.add(history);
	}

	public boolean getisAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}



}