package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {
    private final TrelloClient trelloClient;

    @GetMapping(value = "boards")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getBoardsList();

        trelloBoards.stream()
                .filter(trelloBoardDto ->
                        trelloBoardDto.getName() != null
                                && trelloBoardDto.getId() != null
                                && trelloBoardDto.getName().contains("Kodilla"))
                .forEach(trelloBoardDto ->
                        System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName()));
    }
}
