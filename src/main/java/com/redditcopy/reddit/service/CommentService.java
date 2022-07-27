package com.redditcopy.reddit.service;

import com.redditcopy.reddit.dto.CommentsDto;
import com.redditcopy.reddit.exceptions.PostNotFoundException;
import com.redditcopy.reddit.mapper.CommentMapper;
import com.redditcopy.reddit.model.Comment;
import com.redditcopy.reddit.model.NotificationEmail;
import com.redditcopy.reddit.model.Post;
import com.redditcopy.reddit.model.User;
import com.redditcopy.reddit.repository.CommentRepository;
import com.redditcopy.reddit.repository.PostRepository;
import com.redditcopy.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){
       Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
       Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        comment.setUser(authService.getCurrentUser());
        comment.setPost(post);
       commentRepository.save(comment);


        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
       Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
       return commentRepository.findByPost(post).stream()
               .map(commentMapper::mapToDto)
               .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
