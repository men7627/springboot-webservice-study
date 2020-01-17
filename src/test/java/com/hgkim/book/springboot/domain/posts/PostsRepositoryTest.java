package com.hgkim.book.springboot.domain.posts;

import com.hgkim.book.springboot.service.posts.NotFoundAuthorException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostsRepositoryTest.class);

    @Autowired
    PostsRepository postsRepository;
    
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .build();

        postsRepository.save(posts);

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts result = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2020,1,1,0,0,0);
        postsRepository.save(Posts.builder()
                                .title("title")
                                .content("content")
                                .author("author")
                                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        LOGGER.info("createdDate: {}", posts.getCreatedDate());
        LOGGER.info("modifiedDate: {}", posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}