package com.firstproject.article.controller;

import com.firstproject.article.dto.ArticleCommentCreateForm;
import com.firstproject.article.service.ArticleCommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

/**
 * @author rua
 */

@Controller
@RequiredArgsConstructor
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;
    /** 댓글 작성 */
    @PostMapping("/articles/{articleId}/comment")
    public String addComment(@PathVariable Long articleId,
                             @Valid @ModelAttribute ArticleCommentCreateForm form,
                             HttpSession session, Model model) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) return "redirect:/login";

        String username = (String) session.getAttribute("username");
        //boolean success = articleCommentService.addComment(articleId, form, username);

        //if (!success) {
        //    model.addAttribute("error", "댓글 작성에 실패했습니다.");
        //}

        return "redirect:/articles/" + articleId;
    }

    /** 댓글 삭제 */
    @PostMapping("/articles/{articleId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long articleId,
                                @PathVariable Long commentId,
                                HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) return "redirect:/login";

        String username = (String) session.getAttribute("username");

        try {
            articleCommentService.deleteComment(commentId, username);
        } catch (Exception e) {
            // 실패 무시하고 리디렉트
        }

        return "redirect:/articles/" + articleId;
    }
}
