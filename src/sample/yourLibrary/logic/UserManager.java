package sample.yourLibrary.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import lombok.Getter;
import lombok.Setter;
import sample.yourLibrary.entity.LendHistory;
import sample.yourLibrary.entity.User;
import sample.yourLibrary.view.ViewUtil;

@Getter @Setter
public class UserManager {
	private static EntityManager getEm() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourLibrary");
		EntityManager em = emf.createEntityManager();
		return em;
	}
	public static User createUser( String account, String name)
	{
		User user = new User();
		user.setAccount(account);
		user.setName(name);
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);//newしたオブジェクトの永続化
		tx.commit();
		em.close();
		return user;
	}
	public static User findById(long id) {
		EntityManager em = getEm();
		User user = em.find(User.class, id);
		em.clear();
		em.close();
		return user;
	}
	public static List<User> findAll(){
		EntityManager em = getEm();
		TypedQuery<User> q = em.createQuery("select u from User u order by u.account asc",User.class);
		List<User> result = q.getResultList();
		em.close();
		return result;
	}
	public static User findByAccount(String account) {
		EntityManager em = getEm();
		TypedQuery<User> q = em.createQuery("select u from User u where u.account=:account",User.class);
		q.setParameter("account", account);
		List<User> result = q.getResultList();
		em.close();
		if(result.size() > 0)
			return result.get(0);
		return null;
	}
	public static User login(String account, String password) {
		EntityManager em = getEm();
		TypedQuery<User> q = em.createQuery("select u from User u where u.account =:account and u.password =:password",User.class);
		q.setParameter("account", account);
		q.setParameter("password",password);
		List<User> result = q.getResultList();
		em.close();
		if(result.size()>0)
			return result.get(0);

		if("admin".equals(account) && "admin".equals(password)) {
			User admin = findByAccount(account);
			if(admin==null)
			{//adminがいないときに作成するバックドア
				admin = createUser(account,password);
				admin.setPassword("admin");
				admin.setAdminflg(true);
				updateUser(admin);
				return admin;
			}
		}
			return null;
	}
	public static User updateUser(User user) {
		EntityManager em =getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if(!em.contains(user)) {
			try {
				user = em.merge(user);
				tx.commit();
			}catch(Exception e){
				ViewUtil.AddErrorMessage("ユーザの編集", "ユーザ"+ user.getName() + "を更新時にエラーが発生しました");
			}
		}
		em.close();
		return user;
	}
	public static boolean removeUser(User user) {
		EntityManager em = getEm();
		User find = em.find(User.class, user.getId());
		if(find == null)
			return false;
		EntityTransaction tx =em.getTransaction();
		tx.begin();
		List<LendHistory>histories = find.getLendHistories();
		for(LendHistory history : histories) {
			history.setLendUser(null);
			em.merge(history);
		}
		em.remove(find);
		tx.commit();
		em.close();
		return true;
	}
	public static int removeUser(List<User> users) {
		EntityManager em =getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int removeCount=0;
		for(User user:users) {
			User find = em.find(User.class,user.getId());
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

}