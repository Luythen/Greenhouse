package com.github.Luythen.GreenHouseServer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Repository.UserRepository;
import com.github.Luythen.GreenHouseServer.Service.AuthService;

@SpringBootTest
class GreenHouseServerApplicationTests {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	public void cleanUp () {
		userRepository.deleteById(userRepository.findByEmail("test1@gmail.com").get().getId());
	}

	@Test
	public void registerEmailDuplicationTest () {
		assertDoesNotThrow(() -> {
			authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
		});

		assertThrows(Exception.class, () -> {
			authService.register(new RegisterDto("Test2", "Rest2", "test1@gmail.com", "test2"));
		});
	}

}
