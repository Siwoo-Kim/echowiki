package org.echowiki.web.controller;

import org.echowiki.core.expression.ExpressionEngine;
import org.echowiki.core.expression.ParagraphContext;
import org.echowiki.core.manage.CategoryManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.servlet.jsp.PageContext;

@Controller
@RequestMapping("/test")
public class TestController {

    @Inject
    private CategoryManager categoryManager;

    @GetMapping
    public String test(Model model) {
        model.addAttribute("categories", categoryManager.getRoots());
        return "test";
    }

    private ExpressionEngine expressionEngine = new ExpressionEngine();

    @PostMapping
    public String parsing(@RequestParam(name = "text") String text, Model model) {
        ParagraphContext paragraphContext = expressionEngine.encoding(text);
        model.addAttribute("paragraph", paragraphContext);
        return "test";
    }
}
