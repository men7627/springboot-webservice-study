package com.hgkim.book.springboot.service.posts;

import com.hgkim.book.springboot.domain.posts.PostsRepository;
import com.hgkim.book.springboot.web.dto.PostsSaveRequestDto;
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
}
