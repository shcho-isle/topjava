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
@RequestMapping("/oauth/github")
public class GithubOauth2Controller extends AbstractOauth2Controller {

    private final AuthorizationCodeResourceDetails resourceDetails;

    @Autowired
    public GithubOauth2Controller(@Qualifier("githubResourceDetails") AuthorizationCodeResourceDetails resourceDetails) {
        redirectUri = "http://localhost:8080/topjava/oauth/github/callback";
        this.resourceDetails = resourceDetails;
    }

    @RequestMapping("/authorize")
    public String authorize() {
        String s = fromHttpUrl(resourceDetails.getUserAuthorizationUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", resourceDetails.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", resourceDetails.getTokenName())
                .toUriString();
        return "redirect:" + s;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (resourceDetails.getTokenName().equals(state)) {
            UriComponentsBuilder builder = fromHttpUrl("https://api.github.com/user")
                    .queryParam("access_token", getAccessToken(code, resourceDetails));
            ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
            String login = entityUser.getBody().get("login").asText();
            String email = entityUser.getBody().get("email").asText();
            return authorizeAndRedirect(login, email, attr);
        }
        return null;
    }
}