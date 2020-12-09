////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	
	private User user;
	
	@Before
	public void setUp() {
		user = new User("Giorgio", "Mastrota", 56);
	}

	@Test
	public void getNameTest() {
		assertEquals("Giorgio", user.getName());
	}
	
	@Test
	public void getSurnameTest() {
		assertEquals("Mastrota", user.getSurname());
	}
	
	@Test
	public void getAgeTest() {
		assertEquals(56, user.getAge());
	}

}
