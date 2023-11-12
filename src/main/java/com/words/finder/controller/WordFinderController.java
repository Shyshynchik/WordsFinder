package com.words.finder.controller;

import com.words.finder.model.FindRequest;
import com.words.finder.model.NewFindRequest;
import com.words.finder.service.WordFilterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WordFinderController {
    private final WordFilterService wordFilterService;

    @Operation(description = "Find word by included and excluded letters")
    @PostMapping("/find")
    public ResponseEntity<Mono<List<String>>> findWord(
            @RequestBody NewFindRequest findRequest
    ) {
        return ResponseEntity.ok(wordFilterService.findWords(convert(findRequest)));
    }

    private FindRequest convert(NewFindRequest newFindRequest) {
        return FindRequest.builder()
                .mask(newFindRequest.getMask())
                .excludedMasks(newFindRequest.getExcludedMasks())
                .excludedLetters(stringToSet(newFindRequest.getExcludedLetters()))
                .includedLetters(stringToSet(newFindRequest.getIncludedLetters()))
                .build();
    }

    private Set<Character> stringToSet(String letters) {
        return letters.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }

}
