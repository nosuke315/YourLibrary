package sample.yourLibrary.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LEND_HISTORY")
public class LendHistory implements IdEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	@Temporal(TemporalType.DATE)
	@Column(name="LEND_DATE")
	private Date lendDate;
	@Temporal(TemporalType.DATE)
	@Column(name="DUE_DATE")
	private Date dueDate;
	@Column(name="RETURN_DATE")
	private Date returnDate;
	@Column(name="REVIEW")
	private String review;
	@Column(name="STAR_RATTING")
	private double starRating;

	@ManyToOne
	@JoinColumn(name="MOVIE_ID",referencedColumnName="ID")
	private Movie movie;
	@ManyToOne
	@JoinColumn(name="LENDUSER_ID",referencedColumnName="ID")
	private User lendUser;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLendDate() {
		return lendDate;
	}
	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public double getStarRating() {
		return starRating;
	}
	public void setStarRating(double starRating) {
		this.starRating = starRating;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public User getLendUser() {
		return lendUser;
	}
	public void setLendUser(User lendUser) {
		this.lendUser = lendUser;
	}

}
