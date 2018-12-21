package sample.yourLibary.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.Movie;
import sample.yourLibrary.logic.MovieManager;

@ManagedBean(name="editMovieView")
@ViewScoped
public class EditMovieView {
	@Getter @Setter
	private String title;
	@Getter @Setter
	private String category;
	@Getter @Setter
	private String outline;
	@Getter @Setter
	private List<Movie> movies;
	@Getter @Setter
	private IdEntityListDataModel<Movie> movieModel;
	@Getter @Setter
	private List<Movie>  selectedMovies;
	@Getter @Setter
	private String newPassword;

	@PostConstruct
	public void init() {
		movies = MovieManager.findAll();
		movieModel = new IdEntityListDataModel<Movie>(MovieManager.findAll());
	}
	public String addMovie() {
		if(title == null || category == null)
			return null;
		Movie movie = MovieManager.createMovie(title);
		movie.setCategory(category);
		movie.setOutline(outline);
		movie = MovieManager.updateMovie(movie);
		movies = MovieManager.findAll();
		return "succes";
	}
	public String removeMovie() {
		if(selectedMovies == null || selectedMovies.isEmpty())
			return "success";
		MovieManager.removeMovie(selectedMovies);
		movies.removeAll(selectedMovies);
		movieModel.setWrappedData(movies);
		ViewUtil.AddMessage("映画の削除",selectedMovies.size()+"件の映画を削除");
		isSelected = false;
		return "succes";
	}
	public boolean isSelected;
	public boolean getIsSelected() {
		return isSelected;
	}
	public void handleSelect(SelectEvent event) {
		isSelected = (getSelectedMovies().size()>0);
	}
	public void handleUnSelect(SelectEvent event) {
		isSelected = (getSelectedMovies().size()>0);
	}
	public void handleToggleSelect(ToggleSelectEvent event) {
		isSelected = (getSelectedMovies().size()>0);
	}
	public void onRowEdit(RowEditEvent event) {
		Movie movie=(Movie)event.getObject();
		MovieManager.updateMovie(movie);
		ViewUtil.AddMessage("映画の編集", "映画"+ movie.getTitle() + "を更新");
	}
	public void onRowEditCancel(RowEditEvent event) {
		ViewUtil.AddMessage("映画の編集", "映画の編集をキャンセル");
	}
}
