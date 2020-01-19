package com.hgkim.book.springboot.service.posts;

public class NotFoundPostsException extends RuntimeException {
    public NotFoundPostsException(Long id) {
        super("해당 게시물이 없습니다. id=" + id);
    }
}
