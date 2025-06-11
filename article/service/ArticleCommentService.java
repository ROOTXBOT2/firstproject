package com.firstproject.article.service;

import com.firstproject.article.dto.ArticleCommentCreateRequest;
import com.firstproject.article.dto.ArticleCommentResponse;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.entity.Comment;
import com.firstproject.article.repository.ArticlesRepository;
import com.firstproject.article.repository.CommentsRepository;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final CommentsRepository commentsRepository;
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentResponse> getAllComment(Long articleId) {
        // 게시글 존재 여부 확인
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 해당 게시글의 모든 댓글 조회
        List<Comment> comments = commentsRepository.findAllByArticle(article);

        // Comment 엔티티를 ArticleCommentResponse DTO로 변환
        return comments.stream()
                .map(comment -> new ArticleCommentResponse(
                        comment.getCommentId(),
                        comment.getComment(),
                        comment.getAuthor()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createComment(Long articleId, ArticleCommentCreateRequest form, Long userId) {
        // 게시글 존재 여부 확인
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 사용자 존재 여부 확인
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new SecurityException("유효하지 않은 사용자입니다."));

//        // 댓글 생성 시 null 방지를 위한 검증 추가
//        if (form.getComment() == null || form.getComment().trim().isEmpty()) {
//            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
//        }

        // 댓글 생성
        Comment comment = Comment.builder()
                .article(article)
                .comment(form.getComment()) // 공백 제거
                .author(user.getNickname())
                .build();
        commentsRepository.save(comment);
    }
}
