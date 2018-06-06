package com.eelessam.raw.feed.app.domain;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Food {

    @NotNull
    private String name;
    @NotNull
    private long bonePercentage;
    @NotNull
    private long offalPercentage;
    @NotNull
    private long meatPercentage;
}
