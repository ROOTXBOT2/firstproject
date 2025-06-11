package com.firstproject.article.restcontroller;

import com.firstproject.article.dto.ArticlesCreateRequest;
import com.firstproject.article.dto.ArticlesReadAllResponse;
import com.firstproject.article.dto.ArticlesReadOneResponse;
import com.firstproject.article.service.ArticleService;
import com.firstproject.auth.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author rua
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleApiController {
    private final ArticleService articleService;


    @GetMapping  // 전체 조회
    public ResponseEntity<List<ArticlesReadAllResponse>> articledApi() {
        return ResponseEntity.status(HttpStatus.OK)
                .body( articleService.getAllArticles());
    }

    @PostMapping // 글 작성
    public ResponseEntity<ApiResponse<Void>> articlesCreatingApi(@Valid @RequestBody ArticlesCreateRequest form, HttpSession session) {
        long userId = (long)session.getAttribute("loginUserId");
        try {
            articleService.createArticle(form, userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "게시글이 성공적으로 작성되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false,"서버 오류로 인해 게시글 작성에 실패했습니다."));
        }
    }
    /**
     * 글 하나 조회 API
    */
    @GetMapping("/{articleId}") // 단건 조회
    public ResponseEntity<ApiResponse<ArticlesReadOneResponse>> showArticleApi(@PathVariable Long articleId) {
        Optional<ArticlesReadOneResponse> articleDtoOptional = articleService.GetOneAsDto(articleId);
        if (articleDtoOptional.isPresent()) {
            // 값이 있을 때 get()으로 꺼내기
            ArticlesReadOneResponse dto = articleDtoOptional.get();

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"해당 글을 찾았습니다.",dto));
        } else {
            // 값이 없을 때 (404 Not Found 등)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,"해당 글을 찾을 수 없습니다."));
        }
    }

    @PatchMapping("/{articleId}") // 글 수정
    public ResponseEntity<ApiResponse<Void>> articleUpdateApi(@PathVariable Long articleId, @RequestBody ArticlesCreateRequest form, HttpSession session) {
        long userId = (long)session.getAttribute("loginUserId");
        try {
            articleService.updateArticle(articleId, form.getTitle(), form.getContent(), userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"수정되었습니다"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "수정에 실패했습니다."));
        }
    }

    @DeleteMapping("/{articleId}") // 삭제
    public ResponseEntity<ApiResponse<Void>> articleDeleteApi(@PathVariable Long articleId, HttpSession session) {
        long userId = (long) session.getAttribute("loginUserId");
        try {
            if(articleService.deleteArticle(articleId,userId)){
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"Deleted"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false,"해당 글을 찾을 수 없습니다."));
            }
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "삭제에 실패했습니다."));
        }
    }
}