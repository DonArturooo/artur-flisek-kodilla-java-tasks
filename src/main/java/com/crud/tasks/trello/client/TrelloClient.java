package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards() {
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUrl(), TrelloBoardDto[].class);

            return Optional.ofNullable(boardsResponse).map(Arrays::asList).
                    orElse(Collections.emptyList())
                    .stream()
                    .filter(trelloBoardDto -> Objects.nonNull(trelloBoardDto.getId()) && Objects.nonNull(trelloBoardDto.getName()))
//                    .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                    .toList();
        } catch (RestClientException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return Collections.emptyList();
        }
    }

        public CreatedTrelloCardDto createNewCard (TrelloCardDto trelloCardDto){
            URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                    .queryParam("key", trelloConfig.getTrelloAppKey())
                    .queryParam("token", trelloConfig.getTrelloToken())
                    .queryParam("name", trelloCardDto.getName())
                    .queryParam("desc", trelloCardDto.getDescription())
                    .queryParam("pos", trelloCardDto.getPos())
                    .queryParam("idList", trelloCardDto.getListId())
                    .build()
                    .encode()
                    .toUri();

            return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
        }

        private URI buildUrl () {
            return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                    .queryParam("key", trelloConfig.getTrelloAppKey())
                    .queryParam("token", trelloConfig.getTrelloToken())
                    .queryParam("fields", "name,id")
                    .queryParam("lists", "all")
                    .build()
                    .encode()
                    .toUri();
        }
    }
