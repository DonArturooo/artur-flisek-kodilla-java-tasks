package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());

        //When & Then
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloListDto = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoardDto = List.of(new TrelloBoardDto("1", "test", trelloListDto));

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDto);

        //When & Then
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Trello board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("test")))
                //Trello list fields
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("test_list")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "test_description", "top", "test_list_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "test_name", "https://test.com");

        when(trelloFacade.createCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When & Then
        Gson gson = new Gson();
        String json = gson.toJson(trelloCardDto);

        mockMvc.perform(
                post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.shortUrl", is("https://test.com")));

    }
}