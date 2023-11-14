package com.audition.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuditionPostComment {

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
