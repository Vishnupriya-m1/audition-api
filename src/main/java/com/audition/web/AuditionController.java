package com.audition.web;

import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.model.AuditionPostRequest;
import com.audition.service.AuditionService;
import com.audition.validation.IntegerConstraint;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
public class AuditionController {

    private final Logger log = LoggerFactory.getLogger(AuditionController.class);

    private AuditionService auditionService;

    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(@Valid @RequestParam AuditionPostRequest request) {
        log.info("Request for posts [request = {}] ", request);
        // TODO Add logic that filters response data based on the query param
        return auditionService.getPosts(request);
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostById(@Valid @PathVariable("id") @IntegerConstraint final String postId) {
        log.info("Request for post by id [postId = {}] ", postId);
        // TODO Add input validation
        return auditionService.getPostById(postId);
    }

    @GetMapping(value = {"/post/{id}/comments", "/posts/{id}/comments"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostAndComments(
        @Valid @PathVariable("id") @IntegerConstraint final String postId) {
        log.info("Request for post and related comments [postId = {}] ", postId);
        // TODO Add input validation
        return auditionService.getPostAndComments(postId);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPostComment> getComments(
        @Valid @RequestParam @IntegerConstraint final String postId) {
        log.info("Request for comments [postId = {}] ", postId);
        // TODO Add input validation
        return auditionService.getComments(postId);
    }
}
