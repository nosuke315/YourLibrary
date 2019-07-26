package sample.yourLibrary.cmn;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

import sample.yourLibrary.entity.IdEntity;

public class IdEntityListDataModel<T extends IdEntity> extends ListDataModel<T> implements SelectableDataModel<T>{
	public IdEntityListDataModel(List<T> data) {
		super((List<T>)data);
	}

	@Override
	public T getRowData(String key) {
		List<T> entities = (List<T>) getWrappedData();
		long id = Long.parseLong(key);
		for(T entity:entities) {
			if(entity.getId() == id) {
				return(T) entity;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(T entity) {
		return entity.getId();
	}
}
