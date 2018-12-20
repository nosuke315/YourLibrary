package sample.yourLibrary.logic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sample.yourLibrary.entity.LendHistory;
import sample.yourLibrary.entity.Movie;
import sample.yourLibrary.entity.User;

public class MovieManager {
	private static EntityManager getEm() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourLibrary");
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

	public static int removeMovie(List<Movie> movies) {
		EntityManager em =getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int removeCount=0;
		for(Movie movie:movies) {
			Movie find = em.find(Movie.class,movie.getId());
			if(find==null)
				continue;
			List<LendHistory> histories = find.getLendHistories();
			for(LendHistory history:histories) {
				history.setLendUser(null);//履歴からはユーザーをnullにしてから削除
				em.merge(history);//履歴の更新
			}
			em.remove(find);
			removeCount++;
		}
		tx.commit();
		em.close();
		return removeCount;
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

	public static List<String> getEnteredTitles(){
		EntityManager em = getEm();
		TypedQuery<String> q = em.createQuery("select distinct m.title from Movie m order by m.title asc",String.class);
		List<String> result = q.getResultList();
		em.close();
		return result;
	}
	public static List<Movie> searchMovie(String title,String category,String outline){
		EntityManager em = getEm();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
		Root<Movie> root = query.from(Movie.class);
		query.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(title != null && !title.isEmpty()) {
			predicates.add(cb.like(root.get("title"), "%" + title + "%"));
		}
		if( category != null && !category.isEmpty() )
		{
			predicates.add( cb.equal(root.get("category"), category) );
		}
		if( outline != null && !outline.isEmpty() )
		{
			predicates.add( cb.like(root.get("outline"), "%" + outline + "%") );
		}
		if( predicates.size() > 0 )
		{
			query.where(cb.and(predicates.toArray(new Predicate[]{})));
		}
		query.orderBy(cb.asc(root.get("title")));
		return em.createQuery(query).getResultList();
	}
}
