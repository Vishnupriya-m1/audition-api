package com.audition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.model.AuditionPostRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AuditionServiceTest {

    @Autowired
    private AuditionService service;

    @MockBean
    private AuditionIntegrationClient client;

    @Test
    void testGetPostsNoRequestFields() {
        List<AuditionPost> posts = List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build());
        when(client.getPosts(null)).thenReturn(posts);
        assertThat(service.getPosts(AuditionPostRequest.builder().build())).usingRecursiveComparison().isEqualTo(posts);
    }

    @Test
    void testGetPostsWithRequestFields() {
        List<AuditionPost> posts = List.of(
            AuditionPost.builder()
                .userId(1)
                .id(1)
                .title("Software Architecture")
                .body("Software Architecture in distributed systems").build(),
            AuditionPost.builder()
                .userId(1)
                .id(2)
                .title("System Design")
                .body("latest technology changes in system design").build(),
            AuditionPost.builder()
                .userId(2)
                .id(3)
                .title("JAVA for Beginners")
                .body("Hello World for JAVA Beginners").build(),
            AuditionPost.builder()
                .userId(3)
                .id(4)
                .title("Microservice Patterns")
                .body("Microservice Patterns following twelve factors").build(),
            AuditionPost.builder()
                .userId(4)
                .id(5)
                .title("Software in Distributed Systems")
                .body("Distributed systems of famous tech giants").build());
        when(client.getPosts(null)).thenReturn(posts);

        assertThat(
            service.getPosts(
                AuditionPostRequest.builder().titleContains("Software").bodyContains("systems")
                    .build())).usingRecursiveComparison().isEqualTo(
            List.of(
                AuditionPost.builder()
                    .userId(1)
                    .id(1)
                    .title("Software Architecture")
                    .body("Software Architecture in distributed systems").build(),
                AuditionPost.builder()
                    .userId(4)
                    .id(5)
                    .title("Software in Distributed Systems")
                    .body("Distributed systems of famous tech giants").build())
        );
    }

    @Test
    void testGetPostById() {
        AuditionPost post = AuditionPost.builder()
            .userId(1)
            .id(1)
            .title("Software Architecture")
            .body("Software Architecture in distributed systems").build();
        when(client.getPostById("1")).thenReturn(post);
        assertThat(service.getPostById("1")).isEqualTo(post);
    }

    @Test
    void testGetPostAndComments() {
        AuditionPost post = AuditionPost.builder()
            .userId(1)
            .id(1)
            .title("Software Architecture")
            .body("Software Architecture in distributed systems")
            .comments(List.of(AuditionPostComment.builder().id(1).postId(1).email("test.1@test.com").name("amazing")
                .body("amazing post").build())).build();
        when(client.getPostAndComments("1")).thenReturn(post);
        assertThat(service.getPostAndComments("1")).isEqualTo(post);
    }

    @Test
    void testGetComments() {
        List<AuditionPostComment> comments = List.of(
            AuditionPostComment.builder().id(1).postId(1).email("test.1@test.com").name("amazing")
                .body("amazing post").build());
        when(client.getCommentsByPostId("1")).thenReturn(comments);
        assertThat(service.getComments("1")).usingRecursiveComparison().isEqualTo(comments);
    }
}
