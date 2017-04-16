package ru.javawebinar.topjava.web.oauth.github;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.oauth.OauthSource;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth/github")
public class Oauth2Controller {

    @Autowired
    private RestTemplate template;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private OauthSource source;

    @RequestMapping("/authorize")
    public String authorize() {
        return String.format("redirect:%s?client_id=%s&client_secret=%s&redirect_uri=%s&state=%s"
                , source.getAuthorizeUrl()
                , source.getClientId()
                , source.getClientSecret()
                , source.getRedirectUri()
                , source.getCode());
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (source.getCode().equals(state)) {
            UriComponentsBuilder builder = fromHttpUrl(source.getLoginUrl()).queryParam("access_token", getAccessToken(code));
            ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
            String login = entityUser.getBody().get("login").asText();
            String email = entityUser.getBody().get("email").asText();
            return authorizeAndRedirect(login, email, attr);
        }
        return null;
    }

    private String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(source.getAccessTokenUrl())
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code)
                .queryParam("redirect_uri", source.getRedirectUri());
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    private ModelAndView authorizeAndRedirect(String login, String email, RedirectAttributes attr) {
        try {
            UserDetails userDetails = service.loadUserByUsername(email);
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            return new ModelAndView("redirect:/meals");
        } catch (UsernameNotFoundException ex) {
            attr.addFlashAttribute("userTo", new UserTo(login, email));
            return new ModelAndView("redirect:/register");
        }
    }
}
