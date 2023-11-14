package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.model.AuditionPostRequest;
import com.google.common.base.Strings;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditionService {

    private AuditionIntegrationClient auditionIntegrationClient;


    public List<AuditionPost> getPosts(AuditionPostRequest request) {
        List<AuditionPost> posts = auditionIntegrationClient.getPosts(request.getUserId());
        return posts.stream().filter(
                p -> Strings.isNullOrEmpty(request.getTitleContains()) || p.getTitle().contains(request.getTitleContains()))
            .filter(p -> Strings.isNullOrEmpty(request.getBodyContains()) || p.getTitle()
                .contains(request.getBodyContains()))
            .collect(
                Collectors.toList());
    }

    public AuditionPost getPostById(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public AuditionPost getPostAndComments(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public List<AuditionPostComment> getComments(final String postId) {
        return auditionIntegrationClient.getCommentsByPostId(postId);
    }

}
