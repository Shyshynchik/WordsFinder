package com.words.finder.api.service;

import com.words.finder.api.client.FinderApiClient;
import com.words.finder.api.model.FinderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FinderApiImpl implements FinderApi {
    private final FinderApiClient apiClient;
    @Override
    public Mono<FinderResponse> findByMask(String mask) {
        return apiClient.findByMask(mask);
    }
}
