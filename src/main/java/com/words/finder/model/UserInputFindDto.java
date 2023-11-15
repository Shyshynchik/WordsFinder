package com.words.finder.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserInputFindDto {
    @Schema(example = "*****", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mask;
    @Schema(nullable = true, description = "Можно указывать в любом регистре")
    private String excludedLetters = "";
    @Schema(nullable = true, description = "Можно указывать в любом регистре")
    private String includedLetters = "";
    @Schema(nullable = true, description = "Маска для исключения слов")
    private Set<String> excludedMasks = new HashSet<>();
}
