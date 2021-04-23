package com.example.balance;

import com.example.balance.entities.User;
import com.example.balance.message.request.LoginForm;
import com.example.balance.repositories.BalanceRepository;
import com.example.balance.repositories.UserRepository;
import com.example.balance.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

//@ActiveProfiles("postgres")  // Выбоо профиля, с какой базой работать  postgres или h2
@ActiveProfiles("h2")  // Выбоо профиля, с какой базой работать  postgres или h2

public class Balance_MockMVC_Unit {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void givenId_whenGetExistingPerson_thenStatus200andPersonReturned() throws Exception {

        User user = new User();
        user.setId(1006l);
        user.setUsername("Michail");
        Mockito.when(userRepository.findUserById(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(
                get("/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1006"))
                .andExpect(jsonPath("$.username").value("Michail"));
    }




}
