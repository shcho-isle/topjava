package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth/linkedin")
public class LinkedinOauth2Controller extends AbstractOauth2Controller {

    @Autowired
    @Qualifier("linkedinSource")
    private OauthSource source;

    @RequestMapping("/authorize")
    public String authorize() {
        return getAuthorizedUrl(source);
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (source.getState().equals(state)) {
            UriComponentsBuilder builder = fromHttpUrl(source.getProfileUrl())
                    .queryParam("oauth2_access_token", getAccessToken(code, source))
                    .queryParam("format", "json");
            ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
            String firstName = entityUser.getBody().get("firstName").asText();
            String lastName = entityUser.getBody().get("lastName").asText();
            String email = entityUser.getBody().get("emailAddress").asText();
            return authorizeAndRedirect(firstName + " " + lastName, email, attr);
        }
        return null;
    }
}