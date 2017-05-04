package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth/google")
public class GoogleOauth2Controller extends AbstractOauth2Controller {

    @Autowired
    public GoogleOauth2Controller(@Qualifier("googleResourceDetails") AuthorizationCodeResourceDetails resourceDetails) {
        super(resourceDetails);
    }

    @RequestMapping("/authorize")
    public String authorize() {
        String uriString = getAuthUriBuilder()
                .queryParam("scope", resourceDetails.getScope().get(0))
                .toUriString();
        return "redirect:" + uriString;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (resourceDetails.getTokenName().equals(state)) {
            UriComponentsBuilder builder = fromHttpUrl("https://www.googleapis.com/userinfo/v2/me")
                    .queryParam("access_token", getAccessToken(code));
            ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
            String login = entityUser.getBody().get("name").asText();
            String email = entityUser.getBody().get("email").asText();
            return authorizeAndRedirect(login, email, attr);
        }
        return null;
    }
}