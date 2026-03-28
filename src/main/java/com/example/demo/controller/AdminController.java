package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BookingService bookingService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Get all posts
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Post> posts = postRepository.findByOrderByCreatedAtDesc();
        return ResponseEntity.ok(posts);
    }

    // Get dashboard statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getStats(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalPosts", postRepository.count());

        // Count posts from this week
        long newThisWeek = 0;
        try {
            java.time.LocalDateTime weekAgo = java.time.LocalDateTime.now().minusDays(7);
            newThisWeek = postRepository.findAll().stream()
                    .filter(post -> post.getCreatedAt() != null && post.getCreatedAt().isAfter(weekAgo))
                    .count();
        } catch (Exception e) {
            // If error, just return 0
        }
        stats.put("newThisWeek", newThisWeek);
        stats.put("pendingReports", 0); // Placeholder

        return ResponseEntity.ok(stats);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(404).body("User not found");
    }

    // Get recent activity (posts with user info)
    @GetMapping("/activity")
    public ResponseEntity<?> getRecentActivity(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<Post> recentPosts = postRepository.findTop10ByOrderByCreatedAtDesc();
        return ResponseEntity.ok(recentPosts);
    }

    // Get all bookings
    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Check admin authentication
    @GetMapping("/check-admin")
    public ResponseEntity<?> checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(Map.of("authenticated", true, "username", user.getUsername()));
    }
}
