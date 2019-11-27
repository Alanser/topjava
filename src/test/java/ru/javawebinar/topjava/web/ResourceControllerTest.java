package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void style() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resources/css/style.css"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/css"));
    }
}