package com.words.finder.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserInputFindDto {
    @Schema(example = "**ав*")
    private String mask;
    private String excludedLetters = "";
    private String includedLetters = "";
    private Set<String> excludedMasks = new HashSet<>();
}
