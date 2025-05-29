package com.firstproject.article.api;

import com.firstproject.article.dto.ArticlesCreateForm;
import com.firstproject.article.dto.ArticlesReadForm;
import com.firstproject.article.dto.ArticlesReadOneForm;
import com.firstproject.article.service.ArticleCreate;
import com.firstproject.article.service.ArticleDelete;
import com.firstproject.article.service.ArticleRead;
import com.firstproject.article.service.ArticleUpdate;
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
public class ArticleApiController {
    private final ArticleRead articleRead;
    private final ArticleCreate articleCreate;
    private final ArticleUpdate articleUpdate;
    private final ArticleDelete articleDelete;

    @GetMapping("/api/articles")
    public List<ArticlesReadForm> articledApi(){
        return articleRead.getArticles();
    }

    @PostMapping("/api/articles/creating") //글쓰기 페이지
    public ResponseEntity<String> articlesCreatingApi(@Valid @RequestBody ArticlesCreateForm articlescreateform, HttpSession session){
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        String author = (String) session.getAttribute("username");

        if (loggedIn == null || !loggedIn || author == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 사용 가능한 기능입니다.");
        }
        try {
            articleCreate.CreateArticle(articlescreateform, author);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("게시글이 성공적으로 작성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류로 인해 게시글 작성에 실패했습니다.");
        }
    }

    @GetMapping("/api/articles/{articleId}")
    public ResponseEntity<?> showArticleApi(@PathVariable Long articleId, HttpSession session) {
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        String author = (String) session.getAttribute("username");

        if (loggedIn == null || !loggedIn || author == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 사용 가능한 기능입니다.");
        }
        Optional<ArticlesReadOneForm> article = articleRead.getArticleById(articleId);

        if (article.isPresent()) {
            ArticlesReadOneForm articlesReadOneForm = article.get();
            boolean isAuthor = articlesReadOneForm.getAuthor().equals(author);
            articlesReadOneForm.setAuthor(isAuthor);
            return ResponseEntity.status(HttpStatus.OK).body(articlesReadOneForm);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 글을 찾을 수 없습니다.");
    }

    @PatchMapping("/api/articles/{articleId}/edit")
    public ResponseEntity<String> articleUpdateapi(@PathVariable Long articleId,@RequestBody ArticlesCreateForm articlesUpdateForm, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        String author = (String) session.getAttribute("username");
        if (loggedIn == null || !loggedIn || author == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 사용 가능한 기능입니다.");
        }
        try{
            articleUpdate.EditArticle(articleId, articlesUpdateForm.getTitle(), articlesUpdateForm.getContent(), author);
            return ResponseEntity.status(HttpStatus.OK).body("updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("api/articles/{articleId}/delete")
    public ResponseEntity<String> articleDelete(@PathVariable Long articleId, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        String author = (String) session.getAttribute("username");
        if (loggedIn == null || !loggedIn || author == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 사용 가능한 기능입니다.");
        }
        try{
            Optional<ArticlesReadOneForm> article = articleRead.getArticleById(articleId);
            boolean isAuthor = false;
            if (article.isPresent()) {
                ArticlesReadOneForm articlesReadOneForm = article.get();
                isAuthor = articlesReadOneForm.getAuthor().equals(author);
                if (isAuthor){
                    articleDelete.DeleteArticle(articlesReadOneForm);
                    return ResponseEntity.status(HttpStatus.OK).body("Deleted");
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("DOUX 안전한 접근이 아닙니다.");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("DX 안전한 접근이 아닙니다.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
