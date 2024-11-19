package com.example.testing.examples.controller;

import com.example.testing.examples.TestingExamplesApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TestingExamplesApplication.class)
@AutoConfigureMockMvc
public class JokesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        mvc.perform(get("/jokes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                //.andExpect(content().string("[{\"id\":\"219\",\"type\":\"general\",\"setup\":\"What do you call an eagle who can play the piano?\",\"punchline\":\"Talonted!\"},{\"id\":\"421\",\"type\":\"general\",\"setup\":\"Why couldn't the bicycle stand up by itself?\",\"punchline\":\"It was two-tired.\"},{\"id\":\"326\",\"type\":\"general\",\"setup\":\"Why did the house go to the doctor?\",\"punchline\":\"It was having window panes.\"},{\"id\":\"22\",\"type\":\"general\",\"setup\":\"What does C.S. Lewis keep at the back of his wardrobe?\",\"punchline\":\"Narnia business!\"},{\"id\":\"64\",\"type\":\"general\",\"setup\":\"Well...\",\"punchline\":\"That’s a deep subject.\"},{\"id\":\"150\",\"type\":\"general\",\"setup\":\"Want to hear a chimney joke?\",\"punchline\":\"Got stacks of em! First one's on the house\"},{\"id\":\"369\",\"type\":\"programming\",\"setup\":\"3 SQL statements walk into a NoSQL bar. Soon, they walk out\",\"punchline\":\"They couldn't find a table.\"},{\"id\":\"366\",\"type\":\"programming\",\"setup\":\"What did the router say to the doctor?\",\"punchline\":\"It hurts when IP.\"},{\"id\":\"339\",\"type\":\"general\",\"setup\":\"Why didn’t the skeleton cross the road?\",\"punchline\":\"Because he had no guts.\"},{\"id\":\"43\",\"type\":\"general\",\"setup\":\"Why did the Clydesdale give the pony a glass of water?\",\"punchline\":\"Because he was a little horse\"}]\n"))
                .andExpect(jsonPath("$.length()").value(10));
    }

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
