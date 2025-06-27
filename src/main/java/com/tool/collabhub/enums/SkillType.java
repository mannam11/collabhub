package com.tool.collabhub.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SkillType {
    PROGRAMMING_LANGUAGE("Programming Language"),
    FRAMEWORK("Framework"),
    DATABASE("Database"),
    TOOL("Tool"),
    CLOUD("Cloud"),
    DEVOPS("DevOps"),
    TESTING("Testing");

    private String value;

    SkillType(String value){
        this.value=value;
    }

    public static SkillType fromValue(String value) {
        return Arrays.stream(SkillType.values())
                .filter(type -> type.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid skill type: " + value));
    }
}
