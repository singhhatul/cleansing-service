package com.stackroute.kafkaservice.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Domain {
    private int id;
    private String name;
    private String category;
}
