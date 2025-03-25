package com.blogservice.service.impl;

import com.blogservice.dto.BlogDTO;
import com.blogservice.dto.BlogResponse;
import com.blogservice.exception.BlogNotFoundException;
import com.blogservice.model.Blog;
import com.blogservice.repository.BlogRepository;
import com.blogservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the BlogService interface.
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public BlogResponse createBlog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(blogDTO.getAuthor());
        
        Blog savedBlog = blogRepository.save(blog);
        return mapToResponse(savedBlog);
    }

    @Override
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + id));
        return mapToResponse(blog);
    }

    @Override
    public BlogResponse updateBlog(Long id, BlogDTO blogDTO) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + id));
        
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(blogDTO.getAuthor());
        
        Blog updatedBlog = blogRepository.save(blog);
        return mapToResponse(updatedBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
    }

    @Override
    public Page<BlogResponse> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<BlogResponse> searchBlogsByTitle(String title, Pageable pageable) {
        return blogRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<BlogResponse> searchBlogsByContent(String content, Pageable pageable) {
        return blogRepository.findByContentContainingIgnoreCase(content, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<BlogResponse> searchBlogs(String query, Pageable pageable) {
        return blogRepository.searchByTitleOrContent(query, pageable)
                .map(this::mapToResponse);
    }
    
    /**
     * Maps a Blog entity to a BlogResponse DTO.
     * 
     * @param blog the blog entity
     * @return the blog response DTO
     */
    private BlogResponse mapToResponse(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .author(blog.getAuthor())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }
}
