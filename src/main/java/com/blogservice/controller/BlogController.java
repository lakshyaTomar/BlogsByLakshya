package com.blogservice.controller;

import com.blogservice.dto.BlogDTO;
import com.blogservice.dto.BlogResponse;
import com.blogservice.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for blog operations.
 */
@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Tag(name = "Blog API", description = "API for blog management")
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "Create a new blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Blog created successfully",
                    content = @Content(schema = @Schema(implementation = BlogResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogDTO blogDTO) {
        BlogResponse createdBlog = blogService.createBlog(blogDTO);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a blog post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog found",
                    content = @Content(schema = @Schema(implementation = BlogResponse.class))),
            @ApiResponse(responseCode = "404", description = "Blog not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        BlogResponse blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    @Operation(summary = "Update a blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog updated successfully",
                    content = @Content(schema = @Schema(implementation = BlogResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Blog not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @Valid @RequestBody BlogDTO blogDTO
    ) {
        BlogResponse updatedBlog = blogService.updateBlog(id, blogDTO);
        return ResponseEntity.ok(updatedBlog);
    }

    @Operation(summary = "Delete a blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Blog deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Blog not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all blog posts with pagination")
    @ApiResponse(responseCode = "200", description = "List of blogs retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? 
                    Sort.by(sortBy).ascending() : 
                    Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BlogResponse> blogs = blogService.getAllBlogs(pageable);
        return ResponseEntity.ok(blogs);
    }

    @Operation(summary = "Search blogs by title")
    @ApiResponse(responseCode = "200", description = "List of matching blogs retrieved successfully")
    @GetMapping("/search/title")
    public ResponseEntity<Page<BlogResponse>> searchBlogsByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogResponse> blogs = blogService.searchBlogsByTitle(title, pageable);
        return ResponseEntity.ok(blogs);
    }

    @Operation(summary = "Search blogs by content")
    @ApiResponse(responseCode = "200", description = "List of matching blogs retrieved successfully")
    @GetMapping("/search/content")
    public ResponseEntity<Page<BlogResponse>> searchBlogsByContent(
            @RequestParam String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogResponse> blogs = blogService.searchBlogsByContent(content, pageable);
        return ResponseEntity.ok(blogs);
    }

    @Operation(summary = "Search blogs by title or content")
    @ApiResponse(responseCode = "200", description = "List of matching blogs retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<Page<BlogResponse>> searchBlogs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogResponse> blogs = blogService.searchBlogs(query, pageable);
        return ResponseEntity.ok(blogs);
    }
}
