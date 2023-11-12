package com.words.finder.api.client;

import com.words.finder.api.model.FinderResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class FinderApiClientImpl implements FinderApiClient {
    @Override
    public Mono<FinderResponse> findByMask(String mask) {
        WebClient client = WebClient.create();
            return client
                    .get()
                    .uri(buildUrl(mask))
                    .retrieve()
                    .bodyToMono(FinderResponse.class);
    }

    private URI buildUrl(String mask) {
        try {
            return new URIBuilder("https://anagram.poncy.ru/anagram-decoding.cgi")
                    .addParameter("name", "words_by_mask_index")
                    .addParameter("inword", mask)
                    .addParameter("answer_type", "4")
                    .addParameter("nouns", "on")
                    .addParameter("required_letters", "")
                    .addParameter("exclude_letters", "")
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
