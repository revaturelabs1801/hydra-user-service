package com.revature.user.service.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.demo.beans.BamUser;
import com.revature.demo.beans.Role;
import com.revature.demo.repository.BamUserRepository;
import com.revature.demo.service.BamUserService;



public class UserTests {
	
	static BamUserRepository mockUserRepo = mock(BamUserRepository.class);
	static BamUser mockUser = mock(BamUser.class);
	static BamUserService bamService = new BamUserService(mockUserRepo);
	
	@BeforeClass
	 public static void onceExecutedBeforeAll() {
		BamUser bam = new BamUser("Cal","c","Cay","a@mail.com","1", Role.TRAINER,1,"2234568794",null,null,null);
		BamUser bam2 = new BamUser("Mal","m","May","b@mail.com","1", Role.NONE,2,"1134568794",null,null,null);
		BamUser bam3 = new BamUser("Sal","s","Say","c@mail.com","1", Role.ASSOCIATE,2,"4434568794",null,null,null);
		
		bamService.addOrUpdateUser(bam);
		bamService.addOrUpdateUser(bam2);
		bamService.addOrUpdateUser(bam3);

	}

	@Test
	public void testAddOrUpdateUserReturnsCorrectRole() {
		
		//Setup
		BamUser bam = new BamUser("Sally","b","May","abc@mail.com","15", Role.ASSOCIATE,1,"1234568794",null,null,null);
		when(mockUserRepo.exists(1)).thenReturn(true);
		when(bamService.addOrUpdateUser(bam)).thenReturn(bam);
		
		//Execute		
		mockUser = bamService.addOrUpdateUser(bam);
		
		
		//Verify
		assertEquals(mockUser.getRole(),Role.ASSOCIATE);
	}
	
	
	@Test
	public void testFindUsersInBatchNotNull() {
		
		//Setup
		BamUser bam = new BamUser("S","","G","c@mail.com","1", Role.NONE,1,"3334568794",null,null,null);
		when(bamService.addOrUpdateUser(bam)).thenReturn(bam);
		
		//Execute		
		mockUser = bamService.addOrUpdateUser(bam);
		
		
		//Verify
		assertNotNull(bamService.findUsersInBatch(2));
	}
	
	@Test
	public void testFindUsersByRoleNotNull() {
		
		//Setup
		BamUser bam = new BamUser("S","","G","c@mail.com","1", Role.TRAINER,1,"3334568794",null,null,null);
		when(bamService.addOrUpdateUser(bam)).thenReturn(bam);
		
		//Execute		
		mockUser = bamService.addOrUpdateUser(bam);
		
		//Verify
		assertNotNull(bamService.findByRole(Role.TRAINER));//2 is Trainer
	}
	
	@Test
	public void testInternalControllerFindAllUsers() {
		
		//Setup
		List<BamUser> users = bamService.findAllUsers();
		
		//Verify
		assertNotNull(users);
	}
	
	@Test
	public void testInternalControllerFindTrainers() {
		
		//Setup
		List<BamUser> trainers = bamService.findByRole(Role.TRAINER);
		
		//Verify
		assertNotNull(trainers);
	}


}
