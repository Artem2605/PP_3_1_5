package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.kata.spring.boot_security.demo.controller.MainRestController;
import ru.kata.spring.boot_security.demo.model.User;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MainRestController restController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainRestControllerIsNull() throws Exception {
        assertThat(restController).isNotNull();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void test1() throws Exception {
        this.mockMvc
                .perform(get("/api/users/",
                        new User()))
                .andExpect(status().isOk());
    }

    //@WithUserDetails("admin@mail.ru")
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void test() throws Exception {
        this.mockMvc
                .perform(get("/api/users/{id}",2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("user"));
    }
}