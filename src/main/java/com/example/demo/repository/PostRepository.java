package com.example.demo.repository;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Post p WHERE p.category = :category")
    List<Post> findByCategory(String category);

    @EntityGraph(attributePaths = {"user"})
    List<Post> findTop10ByOrderByCreatedAtDesc();
    
    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Post p WHERE p.user = :user ORDER BY p.createdAt DESC")
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}
