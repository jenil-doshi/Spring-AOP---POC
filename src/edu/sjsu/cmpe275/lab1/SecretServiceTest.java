package edu.sjsu.cmpe275.lab1;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.cmpe275.lab1.exception.UnauthorizedException;
import edu.sjsu.cmpe275.lab1.model.Secret;
import edu.sjsu.cmpe275.lab1.model.SecretImpl;

/**
 * THis is the test class which defines various test cases.
 * @author Jenil
 *
 */
public class SecretServiceTest {

	@Autowired
	SecretImpl secretImpl;
	
	  @Before
	  public void setUp() throws Exception {
		  ApplicationContext context = new ClassPathXmlApplicationContext("SpringConfig.xml");
		  secretImpl = (SecretImpl) context.getBean("secretImplBean");
	  }
		
	@Test(expected = UnauthorizedException.class)
	public void testA() {
	  System.out.println("testA");
	  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
	  secretImpl.readSecret("Bob", aliceSecret);
    }
	
	 @Test
	 public void testB(){
		  System.out.println("testB");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.readSecret("Bob",aliceSecret);
	 }
	 
	 @Test
	 public void testC(){
		  System.out.println("testC");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", aliceSecret, "Carl");
		  secretImpl.readSecret("Carl",aliceSecret);
	 }
	 
	 @Test(expected = UnauthorizedException.class)
	 public void testD(){
		  System.out.println("testD");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  UUID carlSecret = secretImpl.storeSecret("Carl", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", carlSecret, "Alice");
	 }
	 
	 @Test(expected = UnauthorizedException.class)
	 public void testE(){
		  System.out.println("testE");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", aliceSecret, "Carl");
		  secretImpl.unshareSecret("Alice", aliceSecret, "Carl");
		  secretImpl.readSecret("Carl", aliceSecret);
	 }
	 
	 @Test(expected = UnauthorizedException.class)
	 public void testF(){
		  System.out.println("testF");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Alice", aliceSecret, "Carl");
		  secretImpl.shareSecret("Carl", aliceSecret, "Bob");
		  secretImpl.unshareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.readSecret("Bob", aliceSecret);
	 }
	 
	 @Test
	 public void testG(){
		  System.out.println("testG");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", aliceSecret, "Carl");
		  secretImpl.unshareSecret("Bob", aliceSecret, "Carl");
		  secretImpl.readSecret("Carl", aliceSecret);
	 }
	 
	 @Test(expected = UnauthorizedException.class)
	 public void testH(){
		  System.out.println("testH");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.unshareSecret("Carl", aliceSecret, "Bob");
	 }
	 
	 @Test(expected = UnauthorizedException.class)
	 public void testI(){
		  System.out.println("testI");
		  UUID aliceSecret = secretImpl.storeSecret("Alice", new Secret());
		  secretImpl.shareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", aliceSecret, "Carl");
		  secretImpl.unshareSecret("Alice", aliceSecret, "Bob");
		  secretImpl.shareSecret("Bob", aliceSecret, "Carl");
	 }
	 
	 @SuppressWarnings("unused")
	 @Test
	 public void testJ(){
		  System.out.println("testJ");
		  Secret aliceSecret = new Secret();
		  UUID ss1 = secretImpl.storeSecret("Alice", aliceSecret);
		  UUID ss2 = secretImpl.storeSecret("Alice", aliceSecret);
	 }

}
