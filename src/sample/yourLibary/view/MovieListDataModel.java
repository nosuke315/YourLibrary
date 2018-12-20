package sample.yourLibary.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import sample.yourLibrary.entity.Movie;

public class MovieListDataModel extends ListDataModel<Movie> implements SelectableDataModel<Movie>{
	@SuppressWarnings("unchecked")
	@Override
	public Movie getRowData(String key) {
		List<Movie> movies = (List<Movie>) getWrappedData();
		long id = Long.parseLong(key);
		for (Movie movie : movies) {
			if (movie.getId() == id ) {
				return movie;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Movie movie) {
		return movie.getId();
	}
}
