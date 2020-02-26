package org.echowiki.web.controller;

import org.echowiki.core.expression.Paragraph;
import org.echowiki.core.expression.SimpleWikiExpressionEngine;
import org.echowiki.core.expression.WikiExpressionEngine;
import org.echowiki.core.manage.CategoryManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Inject
    private CategoryManager categoryManager;
    private final WikiExpressionEngine expressionEngine = new SimpleWikiExpressionEngine();

    @GetMapping
    public String test(Model model) {
        model.addAttribute("categories", categoryManager.getRoots());
        return "test";
    }

    @PostMapping
    public String parsing(@RequestParam(name = "text") String text, Model model) {
        List<Paragraph> paragraphContext = expressionEngine.encoding(text);
        model.addAttribute("paragraph", paragraphContext);
        return "test";
    }
}
