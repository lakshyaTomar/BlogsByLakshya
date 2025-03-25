package com.blogservice.repository;

import com.blogservice.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for blog operations.
 * Extends JpaRepository to provide CRUD operations for Blog entities.
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    /**
     * Find blogs by title containing the search term (case-insensitive).
     * 
     * @param title the search term
     * @param pageable pagination information
     * @return a page of blogs matching the search criteria
     */
    Page<Blog> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    /**
     * Find blogs by content containing the search term (case-insensitive).
     * 
     * @param content the search term
     * @param pageable pagination information
     * @return a page of blogs matching the search criteria
     */
    Page<Blog> findByContentContainingIgnoreCase(String content, Pageable pageable);
    
    /**
     * Search blogs by either title or content containing the search term (case-insensitive).
     * 
     * @param query the search term
     * @param pageable pagination information
     * @return a page of blogs matching the search criteria
     */
    @Query("SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Blog> searchByTitleOrContent(@Param("query") String query, Pageable pageable);
}
