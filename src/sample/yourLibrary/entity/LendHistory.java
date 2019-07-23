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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="LEND_HISTORY")
@Setter
@Getter
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

}
