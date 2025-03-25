package com.blogservice.service;

import com.blogservice.dto.BlogDTO;
import com.blogservice.dto.BlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for blog operations.
 */
public interface BlogService {

    /**
     * Create a new blog post.
     * 
     * @param blogDTO the blog data
     * @return the created blog
     */
    BlogResponse createBlog(BlogDTO blogDTO);
    
    /**
     * Get a blog post by ID.
     * 
     * @param id the blog ID
     * @return the blog post
     */
    BlogResponse getBlogById(Long id);
    
    /**
     * Update an existing blog post.
     * 
     * @param id the blog ID
     * @param blogDTO the updated blog data
     * @return the updated blog
     */
    BlogResponse updateBlog(Long id, BlogDTO blogDTO);
    
    /**
     * Delete a blog post by ID.
     * 
     * @param id the blog ID
     */
    void deleteBlog(Long id);
    
    /**
     * Get all blog posts with pagination.
     * 
     * @param pageable pagination information
     * @return a page of blog posts
     */
    Page<BlogResponse> getAllBlogs(Pageable pageable);
    
    /**
     * Search blogs by title.
     * 
     * @param title the search term
     * @param pageable pagination information
     * @return a page of matching blog posts
     */
    Page<BlogResponse> searchBlogsByTitle(String title, Pageable pageable);
    
    /**
     * Search blogs by content.
     * 
     * @param content the search term
     * @param pageable pagination information
     * @return a page of matching blog posts
     */
    Page<BlogResponse> searchBlogsByContent(String content, Pageable pageable);
    
    /**
     * Search blogs by title or content.
     * 
     * @param query the search term
     * @param pageable pagination information
     * @return a page of matching blog posts
     */
    Page<BlogResponse> searchBlogs(String query, Pageable pageable);
}
