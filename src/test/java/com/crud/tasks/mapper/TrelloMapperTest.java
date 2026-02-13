package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    static List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
    static List<TrelloListDto> trelloListDto = new ArrayList<>();
    static List<TrelloListDto> trelloListDto2 = new ArrayList<>();
    static List<TrelloListDto> trelloListsDto3 = new ArrayList<>();

    static List<TrelloBoard> trelloBoards = new ArrayList<>();
    static List<TrelloList> trelloLists = new ArrayList<>();
    static List<TrelloList> trelloLists2 = new ArrayList<>();
    static List<TrelloList> trelloLists3 = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        trelloListDto.add(new TrelloListDto("test_id_1", "test_name_1", false));
        trelloListDto.add(new TrelloListDto("test_id_2", "test_name_2", false));
        trelloListDto.add(new TrelloListDto("test_id_3", "test_name_1", false));

        trelloListDto2.add(new TrelloListDto("test_id_1", "test_name_1", false));
        trelloListDto2.add(new TrelloListDto("test_id_2", "test_name_2", false));

        trelloListsDto3.add(new TrelloListDto("test_id_1", "test_name_1", false));
        trelloListsDto3.add(new TrelloListDto("test_id_2", "test_name_2", false));

        trelloBoardDto.add(new TrelloBoardDto("test_id", "test_name", trelloListDto));
        trelloBoardDto.add(new TrelloBoardDto("test_id_2", "test_name_2", trelloListDto2));
        trelloBoardDto.add(new TrelloBoardDto("test_id_3", "test_name_3", trelloListsDto3));

        trelloLists.add(new TrelloList("test_id_1", "test_name_1", false));
        trelloLists.add(new TrelloList("test_id_2", "test_name_2", false));
        trelloLists.add(new TrelloList("test_id_3", "test_name_1", false));

        trelloLists2.add(new TrelloList("test_id_1", "test_name_1", false));
        trelloLists2.add(new TrelloList("test_id_2", "test_name_2", false));

        trelloLists3.add(new TrelloList("test_id_1", "test_name_1", false));
        trelloLists3.add(new TrelloList("test_id_2", "test_name_2", false));

        trelloBoards.add(new TrelloBoard("test_id", "test_name", trelloLists));
        trelloBoards.add(new TrelloBoard("test_id_2", "test_name_2", trelloLists2));
        trelloBoards.add(new TrelloBoard("test_id_3", "test_name_3", trelloLists3));
    }

    @Test
    void mapToBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDto);
        assertAll(() -> {
            assertEquals(3, trelloBoards.size());
            assertEquals("test_id", trelloBoards.getFirst().getId());
            assertEquals("test_name", trelloBoards.getFirst().getName());
            assertEquals(3, trelloBoards.getFirst().getLists().size());
            assertEquals("test_id_1", trelloBoards.getFirst().getLists().getFirst().getId());
            assertEquals("test_name_1", trelloBoards.getFirst().getLists().getFirst().getName());
            assertFalse(trelloBoards.getFirst().getLists().getFirst().isClosed());
        });
    }

    @Test
    void mapToBoardsDto() {
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        assertAll(() -> {
            assertEquals(3, trelloBoardDtos.size());
            assertEquals("test_id", trelloBoardDtos.getFirst().getId());
            assertEquals("test_name", trelloBoardDtos.getFirst().getName());
            assertEquals(3, trelloBoardDtos.getFirst().getLists().size());
            assertEquals("test_id_1", trelloBoardDtos.getFirst().getLists().getFirst().getId());
            assertEquals("test_name_1", trelloBoardDtos.getFirst().getLists().getFirst().getName());
            assertFalse(trelloBoardDtos.getFirst().getLists().getFirst().isClosed());
        });
    }

    @Test
    void mapToList() {
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);
        assertAll(() -> {
            assertEquals(3, trelloLists.size());
            assertEquals("test_id_1", trelloLists.getFirst().getId());
            assertEquals("test_name_1", trelloLists.getFirst().getName());
            assertFalse(trelloLists.getFirst().isClosed());
        });
    }

    @Test
    void mapToListDto() {
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        assertAll(() -> {
            assertEquals(3, trelloListDtos.size());
            assertEquals("test_id_1", trelloListDtos.getFirst().getId());
            assertEquals("test_name_1", trelloListDtos.getFirst().getName());
            assertFalse(trelloListDtos.getFirst().isClosed());
        });
    }

    @Test
    void mapToCardDto() {
        TrelloCard trelloCard = new TrelloCard("test_name", "test_description", "top", "test_list_id");
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        assertAll(() -> {
            assertEquals("test_name", trelloCardDto.getName());
            assertEquals("test_description", trelloCardDto.getDescription());
            assertEquals("top", trelloCardDto.getPos());
            assertEquals("test_list_id", trelloCardDto.getListId());
        });
    }

    @Test
    void mapToCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "test_description", "top", "test_list_id");
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        assertAll(() -> {
            assertEquals("test_name", trelloCard.getName());
            assertEquals("test_description", trelloCard.getDescription());
            assertEquals("top", trelloCard.getPos());
            assertEquals("test_list_id", trelloCard.getListId());
        });
    }
}