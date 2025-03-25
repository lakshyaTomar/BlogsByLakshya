package com.blogservice.exception;

/**
 * Exception thrown when a blog post is not found.
 */
public class BlogNotFoundException extends RuntimeException {
    
    public BlogNotFoundException(String message) {
        super(message);
    }
    
    public BlogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
