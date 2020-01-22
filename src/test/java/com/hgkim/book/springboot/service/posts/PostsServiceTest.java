package com.hgkim.book.springboot.service.posts;

import com.hgkim.book.springboot.domain.posts.Posts;
import com.hgkim.book.springboot.domain.posts.PostsRepository;
import com.hgkim.book.springboot.web.dto.PostsResponseDto;
import com.hgkim.book.springboot.web.dto.PostsSaveRequestDto;
import com.hgkim.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsServiceTest {
    @InjectMocks
    private PostsService postsService;

    @Mock
    private PostsRepository postsRepository;

    @Test
    public void save() {
        //Arrange
        PostsSaveRequestDto requestDto = mock(PostsSaveRequestDto.class);
        Posts posts = mock(Posts.class);

        given(requestDto.toEntity()).willReturn(posts);
        given(postsRepository.save(posts)).willReturn(posts);
        given(posts.getId()).willReturn(1L);

        //Act
        Long saveId = postsService.save(requestDto);

        //Assert
        verify(postsRepository).save(posts);
        assertThat(saveId).isEqualTo(1L);
    }

    @Test
    public void update() {
        //Arrange
        PostsUpdateRequestDto requestDto = mock(PostsUpdateRequestDto.class);
        Posts posts = mock(Posts.class);

        given(postsRepository.findById(1L)).willReturn(Optional.of(posts));
        given(requestDto.getTitle()).willReturn("수정한 제목");
        given(requestDto.getContent()).willReturn("수정한 내용");

        //Act
        Long updatedId = postsService.update(1L, requestDto);

        //Assert
        verify(postsRepository).findById(1L);
        verify(posts).update(requestDto.getTitle(), requestDto.getContent());
        assertThat(updatedId).isEqualTo(1L);
    }

    @Test(expected = NotFoundPostsException.class)
    public void update_존재하지_않는_게시글() {
        //Arrange
        PostsUpdateRequestDto requestDto = mock(PostsUpdateRequestDto.class);
        Posts posts = mock(Posts.class);

        given(postsRepository.findById(1L)).willReturn(Optional.of(posts));
        given(requestDto.getTitle()).willReturn("수정한 제목");
        given(requestDto.getContent()).willReturn("수정한 내용");

        //Act & Assert
        postsService.update(2L, requestDto);
    }

    @Test
    public void findById() {
        //Arrange
        Posts posts = mock(Posts.class);

        given(postsRepository.findById(1L)).willReturn(Optional.of(posts));
        given(posts.getTitle()).willReturn("제목");

        //Act
        PostsResponseDto responseDto = postsService.findById(1L);

        //Assert
        verify(postsRepository).findById(1L);
        assertThat(responseDto.getTitle()).isEqualTo("제목");
    }

    @Test(expected = NotFoundPostsException.class)
    public void findById_존재하지_않는_게시글() {
        //Arrange
        Posts posts = mock(Posts.class);

        given(postsRepository.findById(1L)).willReturn(Optional.of(posts));
        given(posts.getTitle()).willReturn("제목");

        //Act & Assert
        postsService.findById(2L);
    }
}