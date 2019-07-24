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
@Setter
@Getter
public class EditMovieView {
	private String title;
	private long ID;
	private String Category;
	private String outline;
	private boolean isAdmin;
	private List<Movie> movies;
	private IdEntityListDataModel<Movie> movieModel;
	private List<Movie>  selectedMovies;
	public boolean islended;
	public boolean isSelected;

	@PostConstruct
	public void init() {
		movies = MovieManager.findAll();
		movieModel = new IdEntityListDataModel<Movie>(MovieManager.findAll());
	}
	/* ユーザーの追加 */
	public String addMovie() {
		if(title == null)
			return null;
		Movie movie = MovieManager.createMovie(title);
		movie.setId(ID);
		movie.setCategory(Category);
		movie.setOutline(outline);
		movie = MovieManager.updateMovie(movie);
		movies= MovieManager.findAll();
		//reset処理
		this.setID(0);
		this.setTitle("");
		this.setCategory("");
		this.setOutline("");
		return "success";
	}
	/* ユーザーの削除 */
	public String removeMovie() {
		if(selectedMovies == null || selectedMovies.isEmpty())
			return "success";
		MovieManager.removeMovie(selectedMovies);
		movies.removeAll(selectedMovies);
		movieModel.setWrappedData(movies);
		ViewUtil.AddMessage("映画情報の削除",selectedMovies.size()+"件の作品を削除");
		isSelected = false;
		return "success";
	}
	//isSelectedのSetter
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
