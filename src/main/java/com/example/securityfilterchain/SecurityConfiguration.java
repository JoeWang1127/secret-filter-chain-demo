package com.example.securityfilterchain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public DelegatingOAuth2TokenValidator<Jwt> iapJwtDelegatingValidator() {

    List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
    validators.add(new JwtTimestampValidator());
    validators.add(new JwtIssuerValidator("https://cloud.google.com/iap"));

    return new DelegatingOAuth2TokenValidator<>(validators);
  }

  @Bean
  public JwtDecoder iapJwtDecoder(DelegatingOAuth2TokenValidator<Jwt> validator) {
    NimbusJwtDecoder jwtDecoder =
        NimbusJwtDecoder.withJwkSetUri("https://www.gstatic.com/iap/verify/public_key-jwk")
            .jwsAlgorithm(SignatureAlgorithm.from("ES256"))
            .build();
    jwtDecoder.setJwtValidator(validator);

    return jwtDecoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz.requestMatchers("/topsecret").authenticated())
        .oauth2ResourceServer()
        .jwt()
        .and()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    return http.build();
  }

}
