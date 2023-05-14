package com.roughsea.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roughsea.demo.dto.NoteDto;
import com.roughsea.demo.entity.Note;
import com.roughsea.demo.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteService noteService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void creationTest_happyPath() throws Exception {
        NoteDto noteDto = NoteDto.builder()
                .content(UUID.randomUUID().toString())
                .title(UUID.randomUUID().toString())
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isOk())
                .andReturn();
        Note responseNote = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Note.class);

        Long noteId = responseNote.getId();
        Note note = noteService.getById(noteId);

        assertEquals(noteDto.getTitle(), note.getTitle(), "Title dose not match");
        assertEquals(noteDto.getContent(), note.getContent(), "Content dose not match");
    }

}
