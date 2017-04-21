package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
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

import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

public abstract class AbstractOauth2Controller {

    @Autowired
    protected RestTemplate template;

    @Autowired
    private UserDetailsService service;

    protected final AuthorizationCodeResourceDetails resourceDetails;

    private final String redirectUri;

    public AbstractOauth2Controller(AuthorizationCodeResourceDetails resourceDetails) {
        this.resourceDetails = resourceDetails;

        Resource resource = new ClassPathResource("oauth2.properties");
        try {
            PropertySource propertySource = new ResourcePropertySource(resource);
            this.redirectUri = String.format("%s/oauth/%s/callback", propertySource.getProperty("web_root"), resourceDetails.getId());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected UriComponentsBuilder getAuthUriBuilder() {
        return fromHttpUrl(resourceDetails.getUserAuthorizationUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", resourceDetails.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", resourceDetails.getTokenName());
    }

    protected String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(resourceDetails.getAccessTokenUri())
                .queryParam("grant_type", resourceDetails.getGrantType())
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
