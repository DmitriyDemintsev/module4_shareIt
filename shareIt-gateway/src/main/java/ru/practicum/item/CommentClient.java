package ru.practicum.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.item.dto.CommentDto;

import java.util.Map;

@Service
public class CommentClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public CommentClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    //PostMapping
    public ResponseEntity<Object> addComment(long userId, long itemId, CommentDto requestDto) {
        Map<String, Object> parameters = Map.of("id", itemId);
        return post("/{itemId}/comment", userId, parameters, requestDto);
    }
}
