package com.words.finder.api.service;

import com.words.finder.api.model.FinderResponse;
import reactor.core.publisher.Mono;

public interface FinderApi {
    Mono<FinderResponse> findByMask(String mask);
}
