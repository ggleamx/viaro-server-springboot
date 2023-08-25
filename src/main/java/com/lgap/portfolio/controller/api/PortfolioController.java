package com.lgap.portfolio.controller.api;

import com.lgap.portfolio.model.Prospect;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioController {
    @PostMapping("/processForm")
    public String processForm(
            @Valid @ModelAttribute("prospect") Prospect theProspectForm,
            BindingResult theBindingResult
    ) {

        if (theBindingResult.hasErrors()) {
            return "contact";
        } else {
            return "contact-confirmation";
        }
    }


}
