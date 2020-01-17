package com.hgkim.book.springboot.service.posts;

import com.hgkim.book.springboot.domain.posts.Posts;
import com.hgkim.book.springboot.domain.posts.PostsRepository;
import com.hgkim.book.springboot.web.dto.PostsResponseDto;
import com.hgkim.book.springboot.web.dto.PostsSaveRequestDto;
import com.hgkim.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsService(final PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new NotFoundAuthorException(id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new NotFoundAuthorException(id));

        return new PostsResponseDto(entity);
    }
}
