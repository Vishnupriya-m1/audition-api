package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private static final String BASE_URL_COMMENTS_FOR_AUDITION_POSTS = "https://jsonplaceholder.typicode.com/comments";

    public List<AuditionPost> getPosts(final @Nullable String userId) {
        ResponseEntity<AuditionPost[]> response;
        if (Strings.isBlank(userId)) {
            response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS,
                AuditionPost[].class);
        } else {
            response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS,
                AuditionPost[].class,
                Map.of("userId", userId));
        }
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        AuditionPost[] posts = response.getBody();
        return ObjectUtils.isEmpty(posts) ? Collections.emptyList() : Arrays.asList(posts);
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            return new AuditionPost();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Unknown Error message");
            }
        }
    }

    public AuditionPost mygetPostById(final String id) {
        ResponseEntity<AuditionPost> response = restTemplate.getForEntity(BASE_URL_AUDITION_POSTS + "/" + id,
            AuditionPost.class);
        return response.getBody();
    }

    public AuditionPost getPostAndComments(final String id) {
        AuditionPost post = mygetPostById(id);
        post.setComments(getCommentsByPostId(id));
        return post;
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.

    public List<AuditionPostComment> getCommentsByPostId(final String postId) {
        ResponseEntity<AuditionPostComment[]> response = restTemplate.getForEntity(BASE_URL_COMMENTS_FOR_AUDITION_POSTS,
            AuditionPostComment[].class,
            Map.of("postId", postId));
        AuditionPostComment[] comments = response.getBody();
        return ObjectUtils.isEmpty(comments) ? Collections.emptyList() : Arrays.asList(comments);

    }

}
