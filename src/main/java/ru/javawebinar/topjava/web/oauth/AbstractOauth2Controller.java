package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

public abstract class AbstractOauth2Controller {

    @Autowired
    protected RestTemplate template;

    @Autowired
    protected UserDetailsService service;

    protected String redirectUri;

    protected String getAccessToken(String code, AuthorizationCodeResourceDetails resourceDetails) {
        UriComponentsBuilder builder = fromHttpUrl(resourceDetails.getAccessTokenUri())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("client_id", resourceDetails.getClientId())
                .queryParam("client_secret", resourceDetails.getClientSecret());
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    protected ModelAndView authorizeAndRedirect(String login, String email, RedirectAttributes attr) {
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
