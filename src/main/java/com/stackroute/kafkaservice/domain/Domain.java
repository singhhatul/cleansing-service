package com.stackroute.kafkaservice.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Domain {
    private String actor;
    private ObjectDomain object;
    private String verb;
}
