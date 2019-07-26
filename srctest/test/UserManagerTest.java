package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import sample.yourLibrary.entity.User;
import sample.yourLibrary.logic.UserManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UserManagerTest {
	@Before
	public void setUp() throws Exception{
	}

	@After
	public void tearDown() throws Exception{
	}

	@Test
	public void testCRUD() {
		//検索
		List<User> list1st = UserManager.findAll();
		int list1stSize = list1st.size();

		//新規作成
		User user1 = UserManager.createUser("user1","user1name");
		assertEquals("user1",user1.getAccount());
		assertEquals("user1name",user1.getName());

		//ID検索
		User find1 = UserManager.findById(user1.getId());
		assertEquals("user1",find1.getAccount());
		assertEquals("user1name",find1.getName());

		//更新
		find1.setPassword("micky");
		find1.setEmail("micky@destiney.land.hell");
		find1.setAdminflg(true);
		UserManager.updateUser(find1);

		//アカウント検索
		find1 = UserManager.findByAccount("user1");
		assertEquals("user1",find1.getAccount());
		assertEquals("user1name",find1.getName());
		assertEquals("micky",find1.getPassword());
		assertEquals("micky@destiney.land.hell",find1.getEmail());
		assertTrue(find1.isAdminflg());

		//ログイン
		User login = UserManager.login("user1", "micky");
		assertNotNull(login);
		assertEquals("user1name",login.getName());

		//追加の件数確認
		List<User> list2nd = UserManager.findAll();
		assertEquals(list1stSize+1, list2nd.size());

		//削除
		UserManager.removeUser(login);
		find1 = UserManager.findByAccount("user1");
		assertNull(find1);

		//削除の件数確認
		List<User> list3rd = UserManager.findAll();
		assertEquals(list1stSize,list3rd.size());
	}

}
