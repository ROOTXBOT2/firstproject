package com.firstproject.article.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

/**
 * @author rua
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleRead articleRead;
    private final ArticleCreate articleCreate;
    private final ArticleUpdate articleUpdate;
    private final ArticleDelete articleDelete;

    @GetMapping("/articles") //게시판 메인 페이지
    public String articles(Model model, HttpSession session) {
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        // ❌ 로그인하지 않은 경우 → 로그인 페이지로 리다이렉트
        if (loggedIn == null || !loggedIn) {
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
            model.addAttribute("company", "본또보");
            model.addAttribute("error","로그인을 진행해 주세요.");
            return "redirect:/login";
        }
        model.addAttribute("loggedIn", true);
        model.addAttribute("title", "게시판");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");

        // 게시판 리스트 출력(ArticleRead)
        List<ArticlesReadForm> articleList = articleRead.getArticles();
        model.addAttribute("articles", articleList);

        return "articles/index";
    }

    @GetMapping("/articles/create") //글쓰기 페이지
    public String articlesCreate(Model model,HttpSession session) {
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        // ❌ 로그인하지 않은 경우 → 로그인 페이지로 리다이렉트
        if (loggedIn == null || !loggedIn) {
            model.addAttribute("title", "글작성");
            model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
            model.addAttribute("company", "본또보");
            model.addAttribute("error","로그인을 진행해 주세요.");
            return "redirect:/login";
        }
        model.addAttribute("loggedIn", true);
        model.addAttribute("title", "글쓰기");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        return "articles/create";
    }

    @PostMapping("/articles/creating") //글을 쓰기위한 데이터 전달 컨트롤러
    public String articlesCreating(@Valid @ModelAttribute ArticlesCreateForm articlescreateform, Model model, HttpSession session) {
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        // ❌ 로그인하지 않은 경우 → 로그인 페이지로 리다이렉트
        if (loggedIn == null || !loggedIn) {
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
            model.addAttribute("company", "본또보");
            model.addAttribute("error","로그인을 진행해 주세요.");
            return "redirect:/login";
        }

        //1. DTO 가져와서 Create 서비스로 전달
        model.addAttribute("title", "게시판");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        String author = (String) session.getAttribute("username");
        articleCreate.CreateArticle(articlescreateform,author);
        return "redirect:/articles";
    }

    @GetMapping("/articles/{articleId}")
    public String show(@PathVariable Long articleId, Model model,HttpSession session) {
        // 로그인 여부
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        // ❌ 로그인하지 않은 경우 → 로그인 페이지로 리다이렉트
        if (loggedIn == null || !loggedIn) {
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
            model.addAttribute("company", "본또보");
            model.addAttribute("error","로그인을 진행해 주세요.");
            return "redirect:/login";
        }
        model.addAttribute("loggedIn", true);
        model.addAttribute("title", "게시글");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        Optional<ArticlesReadOneForm> article = articleRead.getArticleById(articleId);

        if (article.isPresent()) {
            ArticlesReadOneForm articlesReadOneForm = article.get();
            model.addAttribute("article", articlesReadOneForm);
            // 작성자와 현재 세션 사용자 비교

            String username = (String) session.getAttribute("username");
            boolean isAuthor = articlesReadOneForm.getAuthor().equals(username);
            model.addAttribute("isAuthor", isAuthor);

            return "articles/show";
        }

        return "articles/error";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String edit(@PathVariable Long articleId, Model model, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) return "redirect:/login";

        model.addAttribute("loggedIn", true);
        model.addAttribute("title", "글 수정");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        Optional<ArticlesReadOneForm> articleOptional = articleRead.getArticleById(articleId);
        if (articleOptional.isPresent()) {
            ArticlesReadOneForm article = articleOptional.get();

            String username = (String) session.getAttribute("username");
            if (!article.getAuthor().equals(username)) return "redirect:/articles";

            model.addAttribute("article", article); // 기존 글 내용 전달
            return "articles/edit"; // 수정 폼 뷰
        }
        return "articles/error";
    }

    @PostMapping("/articles/{articleId}/edit")
    public String update(@PathVariable Long articleId,
                         @ModelAttribute ArticlesCreateForm form,
                         HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) return "redirect:/login";
        String username = (String) session.getAttribute("username");
        articleUpdate.EditArticle(articleId, form.getTitle(), form.getContent(), username);
        return "redirect:/articles/" + articleId;

    }

    @PostMapping("/articles/{articleId}/delete")
    public String delete(@PathVariable Long articleId, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) return "redirect:/login";

        Optional<ArticlesReadOneForm> articleOptional = articleRead.getArticleById(articleId);

        if (articleOptional.isPresent()) {
            ArticlesReadOneForm article = articleOptional.get();
            String username = (String) session.getAttribute("username");
            if (!article.getAuthor().equals(username)) return "redirect:/articles";

            articleDelete.DeleteArticle(article);
        }
        return "redirect:/articles";
    }
}
