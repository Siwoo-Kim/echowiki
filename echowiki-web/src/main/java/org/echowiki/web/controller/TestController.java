package org.echowiki.web.controller;

import org.echowiki.core.manage.CategoryManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

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

}
