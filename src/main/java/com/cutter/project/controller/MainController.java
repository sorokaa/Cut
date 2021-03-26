package com.cutter.project.controller;

import com.cutter.project.dao.LinkRepository;
import com.cutter.project.error.ResourceNotFoundException;
import com.cutter.project.model.Link;
import com.cutter.project.pojo.cuttingLink.GenerateNewLink;
import com.cutter.project.pojo.matchingLink.LinkCheck;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private LinkCheck linkCheck;

    @Autowired
    private GenerateNewLink generateNewLink;

    @Autowired
    private LinkRepository linkRepository;


    private Logger logger = LoggerFactory.getLogger(MainController.class.getName());

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String mainPage() {
        return "main";
    }

    //FIXME - !!!DRY!!!
    @GetMapping("/localhost/{link}")
    public ModelAndView redirectFromSite(@PathVariable("link") String link) {
        Link byCuttedLink = linkRepository.findByCuttedLink(link);

        if(byCuttedLink == null) {
            throw new ResourceNotFoundException();
        }

        String linkToRedirect = byCuttedLink.getOriginalLink();

        return new ModelAndView("redirect:" + linkToRedirect);
    }

    @GetMapping("/{link}")
    public ModelAndView redirectByLink(@PathVariable("link") String link) {

        Link byCuttedLink = linkRepository.findByCuttedLink("localhost/" + link);

        if(byCuttedLink == null) {
            throw new ResourceNotFoundException();
        }

        String linkToRedirect = byCuttedLink.getOriginalLink();

        return new ModelAndView("redirect:" + linkToRedirect);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error404Handling() {
        return "error404";
    }

    @RequestMapping(value = "/cut", method = RequestMethod.POST)
    public String cutLink(@RequestParam String link, Map<String, Object> model) {

        if(link.isEmpty()) {

            model.put("incorrect", "Write some link");
            return "/main";

        }

        if(!linkCheck.isLink(link)) {

            model.put("incorrect", "Incorrect link");
            return "/main";

        }

        if(linkRepository.findByOriginalLink(link) != null) {

            model.put("incorrect", "This link exist. Try new");
            return "/main";
        }

        try {

            String newLink = generateNewLink.cut();
            logger.info("Success in create new link");

            linkRepository.save(new Link(link, newLink));
            logger.info("Saved to DAO");

            model.put("newLink", newLink);
            return "/main";

        } catch (RuntimeException exp) {

            model.put("incorrect", "Can't create new link. Try again later");
            return "/main";

        }

    }

}
