package com.cutter.project.controller;

import com.cutter.project.error.ResourceNotFoundException;
import com.cutter.project.model.Link;
import com.cutter.project.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MainController {

    private final LinkService linkService;
    private static Logger logger =
        LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String mainPage() {
        return "index";
    }

    @GetMapping("/{link}")
    public ModelAndView redirectFromSite(@PathVariable("link") String link) {

        Link byCuttedLink = linkService.findByCuttedLink("localhost:8080/" + link);

        if (byCuttedLink == null) {
            throw new ResourceNotFoundException();
        }

        String linkToRedirect = byCuttedLink.getOriginalLink();
        return new ModelAndView("redirect:http://" + linkToRedirect);
    }


    @PostMapping("/cut")
    public String cutLink(@RequestParam String link,
                          Map<String, Object> model) {

        if(link.isEmpty()) {
            model.put("incorrect", "Write some link");
            return "redirect:/";
        }

        String result = linkService.cutLinkAndReturnCutted(link);

        if (result.isEmpty()) {
            model.put("incorrect", "Can't cut your link");
            return "redirect:/";
        }

        model.put("newLink", result);
        return "index";

    }

}
