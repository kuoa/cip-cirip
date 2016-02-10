package services.test.user;

import services.tools.ServicesUser;

public class LoginTest {
	
	
	public static void main(String[] args) {
		System.out.println (ServicesUser.create("testuser", "Testp@ss1", "test@mail.com"));
		System.out.println (ServicesUser.create("testuser", "testpass", "testmail.com"));
	}
}
