package com.words.finder.service;

import com.words.finder.model.FindRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WordFilterService {
    Mono<List<String>> findWords(FindRequest findRequest);
}
