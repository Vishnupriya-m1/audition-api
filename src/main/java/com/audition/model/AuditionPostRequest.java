package com.audition.model;

import com.audition.validation.IntegerConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditionPostRequest {

    @IntegerConstraint(mandatory = false)
    private String userId;
    private String titleContains;
    private String bodyContains;
}
