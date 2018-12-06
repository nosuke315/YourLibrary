package test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sample.yourDiary.entity.LendHistory;
import sample.yourDiary.entity.Movie;
import sample.yourDiary.entity.User;
import sample.yourDiary.logic.MovieManager;
import sample.yourDiary.logic.UserManager;

public class MovieManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCRUDforMovie() {
		//検索
		List<Movie> list1st = MovieManager.findAll();
		int list1stSize = list1st.size();

		//新規作成
		Movie movie = MovieManager.createMovie("movie1title");
		assertEquals("movie1title", movie.getTitle());
		assertFalse(movie.getisLent());
		//ID検索
		Movie find1 = MovieManager.findById(movie.getId());
		assertEquals("movie1title", find1.getTitle());

		//更新
		find1.setCategory("movie1category");
		find1.setOutline("movie1outline");
		find1.setLent(true);
		MovieManager.updateMovie(find1);

		//タイトル検索
		find1 = MovieManager.findByTitle("movie1title");
		assertEquals("movie1title", find1.getTitle());
		assertEquals("movie1category", find1.getCategory());
		assertEquals("movie1outline", find1.getOutline());
		assertTrue( find1.getisLent() );

		//追加の件数確認
		List<Movie> list2nd = MovieManager.findAll();
		assertEquals(list1stSize+1, list2nd.size() );

		//削除
		MovieManager.removeMovie(find1);
		find1 = MovieManager.findByTitle("movie1title");
		assertNull( find1 );

		//削除の件数確認
		List<Movie> list3rd = MovieManager.findAll();
		assertEquals(list1stSize, list3rd.size() );
	}

	@Test
	public void testCRUDforLendHistory() {

		Movie movie = null;
		User user = null;
		try
		{
			//movieの新規作成
			movie = MovieManager.createMovie("movie2title");
			//userの新規作成
			user = UserManager.createUser("user2", "user2");

			//検索
			List<LendHistory> list1st = MovieManager.findAllLendHistory();
			int list1stSize = list1st.size();

			//作成（貸出）
			LendHistory history1 = MovieManager.lendMovie(movie, user);
			assertEquals(movie.getId(), history1.getMovie().getId());
			assertEquals(user.getId(), history1.getLendUser().getId());
			assertNotNull(history1.getLendDate());

			//追加の件数確認
			List<LendHistory> list2nd = MovieManager.findAllLendHistory();
			int list2ndSize = list2nd.size();
			assertEquals(list1stSize+1, list2ndSize);

			//更新
			history1.setReview("nice");
			history1.setStarRating(5);
			history1.setDueDate(new Date());
			MovieManager.updateLendHistory(history1);
			LendHistory history2 = MovieManager.findLendHistoryById(history1.getId());
			assertEquals("nice", history2.getReview());
			assertEquals(new Double(5), new Double(history2.getStarRating()) );
			assertNotNull(history2.getDueDate());

			//Movie側からの取得
			Movie movie1 = MovieManager.findById(movie.getId());
			assertEquals(history1.getId(), movie1.getLendHistories().get(0).getId());
			//User側からの取得
			User user1 = UserManager.findById(user.getId());
			assertEquals(history1.getId(), user1.getLendHistories().get(0).getId());

			//返却
			history2 = MovieManager.returnMovie(history2);
			assertNotNull(history2.getReturnDate());

			//2件目の作成
			LendHistory history3 = MovieManager.lendMovie(movie, user);
			assertEquals(movie.getId(), history3.getMovie().getId());
			assertEquals(user.getId(), history3.getLendUser().getId());
			assertNotNull(history3.getLendDate());

			//１件削除
			MovieManager.removeLendHistory(history1);
			List<LendHistory> list3rd = MovieManager.findAllLendHistory();
			int list3rdSize = list3rd.size();
			assertEquals(list1stSize+1, list3rdSize);

			//Userを削除した際に削除されていないことの確認
			UserManager.removeUser(user);
			user = null;
			List<LendHistory> list4th = MovieManager.findAllLendHistory();
			int list4thSize = list4th.size();
			assertEquals(list1stSize+1, list4thSize);

			//Movieを削除した際に削除されることの確認
			MovieManager.removeMovie(movie);
			movie = null;
			List<LendHistory> list5th = MovieManager.findAllLendHistory();
			int list5thSize = list5th.size();
			assertEquals(list1stSize, list5thSize);
		}
		finally
		{
			//movieの削除
			if( movie != null )
				MovieManager.removeMovie(movie);
			//userの削除
			if( user != null)
				UserManager.removeUser(user);
		}
	}
}