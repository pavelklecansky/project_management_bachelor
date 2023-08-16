package cz.klecansky.projectmanagement.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class that will send every request to Svelte app except that too api.
 */
@Controller
public class WebAppController {
    @RequestMapping(value = {"/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/*/{y:[\\w\\-]+}", "/error"})
    public String getIndex(HttpServletRequest request) {
        return "/index.html";
    }
}
