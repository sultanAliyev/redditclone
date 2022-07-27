package com.redditcopy.reddit.repository;

import com.redditcopy.reddit.model.Post;
import com.redditcopy.reddit.model.User;
import com.redditcopy.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
