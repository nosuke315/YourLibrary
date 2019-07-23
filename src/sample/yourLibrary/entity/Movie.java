package sample.yourLibrary.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "MOVIE")
@Getter
@Setter
public class Movie implements IdEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	@Column(name="TITLE",nullable=false)
	private String title;
	@Column(name="CATEGORY")
	private String Category;
	@Column(name = "IS_LENT")
	private boolean isLent = false;
	@Column(name = "OUTLINE")
	private String outline;

	public boolean getisLent() {
		return isLent;
	}

	public void setLent(boolean isLent) {
		this.isLent = isLent;
	}

	@OneToMany(mappedBy = "movie",cascade=CascadeType.ALL)
	List<LendHistory> lendHistories;


	public void addLendHistory(LendHistory history) {
		lendHistories.add(history);
	}

}
