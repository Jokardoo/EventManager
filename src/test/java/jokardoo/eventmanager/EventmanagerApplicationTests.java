package jokardoo.eventmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.dto.mapper.user.UserMapper;
import jokardoo.eventmanager.repository.UserRepository;
import jokardoo.eventmanager.service.utils.DefaultAccountCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class EventmanagerApplicationTests {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DefaultAccountCreator defaultAccountCreator;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "admin", authorities = "ADMIN")
	void shouldBeTwoDefaultUsers() throws Exception {

		Assertions.assertTrue(userRepository.findByLogin("admin").isPresent());

		Assertions.assertTrue(userRepository.findByLogin("user").isPresent());
	}

	@Test
	@WithMockUser(username = "admin", authorities = "ADMIN")
	void shouldContainsOnlyHashedPasswordInDatabase() throws Exception {


		UserEntity foundUser = userRepository.findByLogin("user").get();

		System.out.println(foundUser.getLogin());

		Assertions.assertTrue(passwordEncoder.matches("user", foundUser.getPasswordHash()));


	}

}
