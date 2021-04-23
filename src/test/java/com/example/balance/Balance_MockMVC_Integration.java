package com.example.balance;

import com.example.balance.entities.User;
import com.example.balance.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

@ActiveProfiles("postgres")  // Выбоо профиля, с какой базой работать  postgres или h2
//@ActiveProfiles("h2")  // Выбоо профиля, с какой базой работать  postgres или h2

public class Balance_MockMVC_Integration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
//    @MockBean
    @Autowired
    private UserRepository userRepository;

    @Test
    public void give_User_10() throws Exception {

        User user = userRepository.findUserById(10);

        mockMvc.perform(
                get("/user/" + user.getId()))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("10"))
//                .andExpect(jsonPath("$.username").value("andrey"));
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }




}
