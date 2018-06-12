package com.eelessam.raw.feed.app.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Food {

    private String name;

    private Integer bonePercentage;

    private Integer offalPercentage;

    private Integer meatPercentage;
}
