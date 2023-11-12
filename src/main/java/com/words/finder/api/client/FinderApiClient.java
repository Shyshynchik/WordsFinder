package com.words.finder.api.client;

import com.words.finder.api.model.FinderResponse;
import reactor.core.publisher.Mono;

public interface FinderApiClient {
    Mono<FinderResponse> findByMask(String mask);
}
