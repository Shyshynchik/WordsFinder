package com.words.finder.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FindRequest {
    @Schema(example = "**ав*")
    private String mask;
    private Set<Character> excludedLetters;
    private Set<Character> includedLetters;
    private Set<String> excludedMasks;
}
