package com.redditcopy.reddit.repository;


import com.redditcopy.reddit.model.Comment;
import com.redditcopy.reddit.model.Post;
import com.redditcopy.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
