package com.redditcopy.reddit.repository;


import com.redditcopy.reddit.model.Post;
import com.redditcopy.reddit.model.Subreddit;
import com.redditcopy.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
