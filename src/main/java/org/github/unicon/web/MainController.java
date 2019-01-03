package org.github.unicon.web;

import org.github.unicon.conv.measure.MeasureType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("types", MeasureType.values());
        return "main";
    }

    @RequestMapping("/convert/simple")
    public String convertSimple(Model model,
                                @RequestParam("type") MeasureType type) {
        MeasureType[] allTypes = MeasureType.values();
        ArrayList<MeasureType> types = new ArrayList<>();
        for (MeasureType allType : allTypes) {
            if (allType != type) {
                types.add(allType);
            }
        }

        model.addAttribute("types", types);
        model.addAttribute("units", type.getUnits());
        model.addAttribute("currentType", type);

        return "convert-simple";
    }

    @RequestMapping("/convert/date")
    public String convertDate(Model model) {
        model.addAttribute("types", MeasureType.values());
        model.addAttribute("units", MeasureType.DURATION.getUnits());
        return "convert-date";
    }
}
