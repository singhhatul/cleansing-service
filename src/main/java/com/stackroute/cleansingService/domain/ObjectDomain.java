package com.stackroute.cleansingService.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ObjectDomain {
    private String objectType;
    private String content;
}
