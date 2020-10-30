package com.dropbox.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String sector;
}