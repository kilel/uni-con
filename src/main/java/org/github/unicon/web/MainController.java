package org.github.unicon.web;

import org.github.unicon.conv.measure.MeasureType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("types", MeasureType.values());
        return "main";
    }
}
