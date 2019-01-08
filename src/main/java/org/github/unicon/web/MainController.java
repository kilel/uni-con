package org.github.unicon.web;

import org.github.unicon.conv.measure.MeasureType;
import org.github.unicon.conv.text.EncodingType;
import org.github.unicon.conv.text.EscapeType;
import org.github.unicon.conv.text.HashType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("measureTypes", MeasureType.values());
        return "main";
    }

    @RequestMapping("/convert/measure")
    public String convertMeasure(Model model,
                                 @RequestParam("type") MeasureType type) {
        model.addAttribute("measureTypes", MeasureType.values());
        model.addAttribute("units", type.getUnits());
        model.addAttribute("currentType", type);

        return "convert/measure";
    }

    @RequestMapping("/convert/date")
    public String convertDate(Model model) {
        model.addAttribute("measureTypes", MeasureType.values());
        model.addAttribute("units", MeasureType.DURATION.getUnits());
        return "convert/date";
    }

    @RequestMapping("/convert/escape")
    public String convertEscape(Model model) {
        model.addAttribute("measureTypes", MeasureType.values());
        model.addAttribute("esTypes", EscapeType.values());
        return "convert/escape";
    }

    @RequestMapping("/convert/encode")
    public String convertEncode(Model model) {
        model.addAttribute("measureTypes", MeasureType.values());
        model.addAttribute("encodeTypes", EncodingType.values());
        return "convert/encode";
    }

    @RequestMapping("/convert/hash")
    public String convertHash(Model model) {
        model.addAttribute("measureTypes", MeasureType.values());
        model.addAttribute("encodeTypes", EncodingType.values());
        model.addAttribute("hashTypes", HashType.values());
        return "convert/hash";
    }
}
