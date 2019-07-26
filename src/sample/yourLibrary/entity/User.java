package sample.yourLibrary.entity;

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
@Getter
@Setter
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
	@Column(name="ADMIN_FLG")
	private boolean adminflg = false;
	@Column(name="THEME")
	private String theme;

	@OneToMany(mappedBy="lendUser")
	List<LendHistory> lendHistories;

	public void addLendHistory(LendHistory history) {
		lendHistories.add(history);
	}



}