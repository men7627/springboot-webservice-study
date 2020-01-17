package com.hgkim.book.springboot.service.posts;

public class NotFoundAuthorException extends RuntimeException {
    public NotFoundAuthorException(Long id) {
        super("해당 사용자가 없습니다. id=" + id);
    }
}
