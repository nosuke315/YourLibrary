package sample.yourLibary.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import sample.yourLibrary.entity.User;

public class UserListDataModel extends ListDataModel<User> implements SelectableDataModel<User> {
	@SuppressWarnings("unchecked")
	@Override
	public User getRowData(String key) {
		List<User> users = (List<User>)getWrappedData();
		long id = Long.parseLong(key);
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(User user) {
		return user.getId();
	}
}
