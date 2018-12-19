package sample.yourLibary.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import sample.yourLibrary.entity.Movie;
import sample.yourLibrary.logic.MovieManager;

public class SearchMovieView {
	private List<Movie> movies;
	private IdEntityListDataModel<Movie> movieModel;
	private String title;
	private long id;
	private String Category;
	private boolean isLent;
	private Movie selectedMovies;
	public boolean isSelected;
	public boolean getIsSelected() {
		return isSelected;
	}
	private List<String> enteredTitles;
	private List<String> enteredCategory;
	private List<String> enteredOutline;

	public static final String SEARCH_MOVIE_VIEW_MOVIES_IN_CART = "searchMovieView.moviesInCart";
	private List<Movie> moviesInCart;
	private Movie selectedMovie;

	public List<Movie> getMoviesInCart() {
		return moviesInCart;
	}
	public void setMoviesInCart(List<Movie> moviesInCart) {
		this.moviesInCart = moviesInCart;
	}
	public Movie getSelectedMovie() {
		return selectedMovie;
	}
	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	public IdEntityListDataModel<Movie> getMovieModel() {
		return movieModel;
	}
	public void setMovieModel(IdEntityListDataModel<Movie> movieModel) {
		this.movieModel = movieModel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public boolean isLent() {
		return isLent;
	}
	public void setLent(boolean isLent) {
		this.isLent = isLent;
	}
	public Movie getSelectedMovies() {
		return selectedMovies;
	}
	public void setSelectedMovies(Movie selectedMovies) {
		this.selectedMovies = selectedMovies;
	}
	@PostConstruct
	public void init() {
		movies = new ArrayList<Movie>();
		movieModel = new IdEntityListDataModel<Movie>(movies);
		enteredTitles = MovieManager.getEnteredTitles();
	}
	public List<String> completeTitle(String input)
	{
		return enteredTitles
				.stream()
				.filter(e -> e.contains(input))
				.collect(Collectors.toList());
	}
	public List<String> completeCategory(String input){
		return enteredCategory
				.stream()
				.filter(e -> e.contains(input))
				.collect(Collectors.toList());
	}
	public List<String> completeOutline(String input){
		return enteredOutline
				.stream()
				.filter(e -> e.contains(input))
				.collect(Collectors.toList());
	}
	public String addCart()
	{
		if( moviesInCart.size() >= 10)
		{
			ViewUtil.AddErrorMessage("制限", "カートに入れられるのは10件までです。");
			return "success";
		}
		if( ! moviesInCart.contains(selectedMovie) )
		{
			moviesInCart.add(selectedMovie);
			movies.remove(selectedMovie);
			movieModel.setWrappedData(movies);
			selectedMovie = null;
			isSelected = false;
		}
		return "success";
	}

	//viewCart.xhtmlへの遷移。Flashにカートの中の映画を設定する。
	public String viewCart()
	{
		if( moviesInCart.size() == 0)
		{
			ViewUtil.AddErrorMessage("エラー", "カートが空です。");
			return "success";
		}
		ViewUtil.putToFlash(SEARCH_MOVIE_VIEW_MOVIES_IN_CART, moviesInCart);
		return "/viewCart.xhtml";
	}

	public String set() {
		return "searchMovie.xhtml";
	}
}
