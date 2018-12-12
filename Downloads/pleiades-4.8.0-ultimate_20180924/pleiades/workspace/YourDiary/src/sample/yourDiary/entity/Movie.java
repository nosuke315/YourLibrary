package sample.yourDiary.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE")
public class Movie {
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

	public List<LendHistory> getLendHistories() {
		return lendHistories;
	}

	public void setLendHistories(List<LendHistory> lendHistories) {
		this.lendHistories = lendHistories;
	}

	@OneToMany(mappedBy = "movie",cascade=CascadeType.ALL)
	List<LendHistory> lendHistories;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public void addLendHistory(LendHistory history) {
		lendHistories.add(history);
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

}
