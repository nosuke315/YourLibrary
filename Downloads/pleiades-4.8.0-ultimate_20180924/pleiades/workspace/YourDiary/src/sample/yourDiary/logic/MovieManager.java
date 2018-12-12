package sample.yourDiary.logic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import sample.yourDiary.entity.LendHistory;
import sample.yourDiary.entity.Movie;
import sample.yourDiary.entity.User;

public class MovieManager {
	private static EntityManager getEm() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourDiary");
		EntityManager em = emf.createEntityManager();
		return em;
	}
	public  static Movie createMovie(String title) {
		Movie movie = new Movie();
		movie.setTitle(title);
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(movie);
		tx.commit();
		em.close();
		return movie;
	}
	public static Movie findById( long id )
	{
		EntityManager em = getEm();
		Movie movie = em.find(Movie.class, id);
		em.clear();
		em.close();
		return movie;
	}

	public static List<Movie> findAll()
	{
		EntityManager em = getEm();
		TypedQuery<Movie> q = em.createQuery("select m from Movie m order by m.title asc", Movie.class);
		List<Movie> result = q.getResultList();
		em.close();
		return result;
	}

	public static Movie findByTitle( String title )
	{
		EntityManager em = getEm();
		TypedQuery<Movie> q = em.createQuery("select m from Movie m where m.title=:title", Movie.class);
		q.setParameter("title", title);
		List<Movie> result = q.getResultList();
		em.close();
		if( result.size() > 0 )
			return result.get(0);
		return null;
	}

	public static Movie updateMovie( Movie movie )
	{
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if(!em.contains(movie) )
			movie = em.merge(movie);
		tx.commit();
		em.close();
		return movie;
	}

	public static boolean removeMovie( Movie movie )
	{
		EntityManager em = getEm();
		Movie find = em.find(Movie.class, movie.getId());
		if( find == null )
			return false;
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(find);
		tx.commit();
		em.close();
		return true;
	}

	public static LendHistory lendMovie( Movie movie, User user )
	{
		LendHistory history = new LendHistory();
		history.setLendDate(Date.from( LocalDateTime.now().toInstant(ZoneOffset.UTC)));
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		history.setMovie(movie);
		history.setLendUser(user);
		em.persist(history);
		if(!em.contains(movie))
			movie = em.merge(movie);
		movie.setLent(true);
		movie.addLendHistory(history);
		if(!em.contains(user))
			user = em.merge(user);
		user.addLendHistory(history);
		tx.commit();
		em.close();
		return history;
	}

	public static LendHistory updateLendHistory( LendHistory history )
	{
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if( !em.contains(history) )
			history = em.merge(history);
		tx.commit();
		em.close();
		return history;
	}

	public static LendHistory returnMovie( LendHistory history )
	{
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if( !em.contains(history) )
			history = em.merge(history);
		history.setReturnDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
		Movie movie = history.getMovie();
		if( !em.contains(movie) )
			movie = em.merge(movie);
		movie.setLent(false);
		tx.commit();
		em.close();
		return history;
	}

	public static boolean removeLendHistory( LendHistory history )
	{
		EntityManager em = getEm();
		LendHistory find = em.find(LendHistory.class, history.getId());
		if( find == null )
			return false;
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(find);
		tx.commit();
		em.close();
		return true;
	}

	public static LendHistory findLendHistoryById( long id )
	{
		EntityManager em = getEm();
		LendHistory history = em.find(LendHistory.class, id);
		em.clear();
		em.close();
		return history;
	}

	public static List<LendHistory> findAllLendHistory()
	{
		EntityManager em = getEm();
		TypedQuery<LendHistory> q = em.createQuery("select h from LendHistory h order by h.movie.title asc", LendHistory.class);
		List<LendHistory> result = q.getResultList();
		em.close();
		return result;
	}

}
