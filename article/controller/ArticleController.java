package com.firstproject.article.controller;

import com.firstproject.article.dto.ArticlesCreateForm;
import com.firstproject.article.dto.ArticlesReadForm;
import com.firstproject.article.dto.ArticlesReadOneForm;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticlesRepository;
import com.firstproject.article.service.ArticleCreate;
import com.firstproject.article.service.ArticleRead;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleRead articleRead;
    private final ArticleCreate articleCreate;
    private final ArticlesRepository articlesRepository;

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
            model.addAttribute("title", "LOGIN");
            model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
            model.addAttribute("company", "본또보");
            model.addAttribute("error","로그인을 진행해 주세요.");
            return "redirect:/login";
        }

        model.addAttribute("title", "글쓰기");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        return "articles/articlesCreate";
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

        model.addAttribute("title", "게시글");
        model.addAttribute("brand", "RUA"); //가져온 세션 정보에서 추출 없으면 메인 페이지로 롤벡
        model.addAttribute("company", "본또보");
        Optional<ArticlesReadOneForm> article = articleRead.getArticleById(articleId);
        if (article.isPresent()) {
            ArticlesReadOneForm articlesReadOneForm = article.get();
            model.addAttribute("article", articlesReadOneForm);
            return "articles/show";
        }
        return "articles/error";
    }
}
