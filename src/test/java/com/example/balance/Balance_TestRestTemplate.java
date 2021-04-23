package com.example.balance;

import com.example.balance.entities.User;
import com.example.balance.message.request.LoginForm;
import com.example.balance.repositories.BalanceRepository;
import com.example.balance.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

@ActiveProfiles("h2")        // Выбор профиля, с какой базой работать  postgres или h2
public class Balance_TestRestTemplate {
    @Autowired
//  тестирование с запросами по настоящему
    private TestRestTemplate restTemplate;
    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    public void resetDb() {
        balanceRepository.deleteAll();
    }

    @Test
    public void givenUser_whenGetUser_thenStatus200() {

        long id = createTestUser("Mike", "123456").getId();
        ResponseEntity<?> response = restTemplate.exchange("/user/{id}", HttpMethod.GET, null, User.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        User user = (User) response.getBody();
        assertThat(user.getId(), notNullValue());
        assertThat(user.getUsername(), is("Mike"));

//        response = restTemplate.exchange("/user/{id}", HttpMethod.DELETE, null, User.class,
//                id);
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));


        //       assertThat(user.getUsername(), is("Mike"));
    }


    @Test
    public void givenUser() {
        long id = createTestUser("Mike", "123456").getId();
//        User user0 =  userService.findUserByUsername("Mike");

        User user = restTemplate.getForObject("/user/{id}", User.class, id);
        assertThat(user.getUsername(), is("Mike"));
    }




    private User createTestUser(String userName, String password) {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(userName);
        loginForm.setPassword(password);
        return userService.createUser(loginForm);
    }


}
