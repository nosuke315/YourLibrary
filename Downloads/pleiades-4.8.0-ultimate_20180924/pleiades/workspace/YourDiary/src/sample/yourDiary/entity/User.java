package sample.yourDiary.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USERTBL")
public class User {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Getter @Setter
	private long id;
	@Column(name="ACCOUNT",nullable=false,unique=true)
	@Getter @Setter
	private String account;
	@Column(name="PASSWORD")
	@Getter @Setter
	private String password;
	@Column(name="NAME",nullable=false)
	@Getter @Setter
	private String name;
	@Column(name="E_MAIL")
	@Getter @Setter
	private String email;
	@Column(name="IS_ADMIN")
	private boolean isAdmin = false;
	@Column(name="THEME")
	@Getter @Setter
	private String theme;

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