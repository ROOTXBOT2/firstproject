package com.firstproject.article.restcontroller;

import com.firstproject.article.dto.ArticleCommentCreateRequest;
import com.firstproject.article.dto.ArticleCommentResponse;
import com.firstproject.article.service.ArticleCommentService;
import com.firstproject.auth.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rua
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class ArticleCommentApiController {
    private final ArticleCommentService articleCommentService;

    @GetMapping("/{articleId}")
    public ResponseEntity<List<ArticleCommentResponse>> getCommentsApi(@PathVariable Long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body( articleCommentService.getAllComment(articleId));
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<ApiResponse<Void>> createCommentApi(@PathVariable Long articleId,@RequestBody ArticleCommentCreateRequest form, HttpSession session) {
        long userId = (long)session.getAttribute("loginUserId");
        try {
            articleCommentService.createComment(articleId,form,userId);
            return ResponseEntity.status(HttpStatus.CREATED) // OK → CREATED
                    .body(new ApiResponse<>(true, "댓글이 추가되었습니다"));        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "댓글 추가에 실패했습니다."+ e.getMessage()));
        }
    }
}
