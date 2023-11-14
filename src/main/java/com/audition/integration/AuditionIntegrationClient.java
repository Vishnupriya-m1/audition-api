package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class AuditionIntegrationClient {

    private RestTemplate restTemplate;

    private static final String BASE_URL_AUDITION_POSTS = "https://jsonplaceholder.typicode.com/posts";

    public List<AuditionPost> getPosts(final @Nullable String userId) {
        ResponseEntity<AuditionPost[]> response;
        if (Strings.isBlank(userId)) {
            response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS,
                AuditionPost[].class);
        } else {
            response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS + "?userId={userId}",
                AuditionPost[].class, userId);
        }
        AuditionPost[] posts = response.getBody();
        return ObjectUtils.isEmpty(posts) ? Collections.emptyList() : Arrays.asList(posts);
    }

    public AuditionPost getPostById(final String id) {
        try {
            ResponseEntity<AuditionPost> response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS + "/" + id,
                AuditionPost.class);
            return response.getBody();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                throw new SystemException("Unknown Error message", e.getStatusCode().value());
            }
        }
    }

    public AuditionPost getPostAndComments(final String id) {
        AuditionPost post = getPostById(id);
        post.setComments(getCommentsByPostId(id));
        return post;
    }

    public List<AuditionPostComment> getCommentsByPostId(final String postId) {
        ResponseEntity<AuditionPostComment[]> response = restTemplate.getForEntity(
            BASE_URL_AUDITION_POSTS + "/" + postId + "/comments",
            AuditionPostComment[].class);
        AuditionPostComment[] comments = response.getBody();
        return ObjectUtils.isEmpty(comments) ? Collections.emptyList() : Arrays.asList(comments);

    }

}
