package com.springboot.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
    	/**
    	 * Apply some default the configurations related to the authorization server.
    	 */
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                		/**
                		 * With the help of this `defaultAuthenticationEntryPoint()`,
                		 * we're going to redirect the end user to the login page
                		 * whenever exception occurs related to authentication
                		 */
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                /**
                 * Convert these authorizations (OAuth2AuthorizationServerConfiguration) server as an OAuth2 resource server
                 * to accept Accept access tokens for User Info and/or Client Registration
                 */
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * Another bean with the same type is being created,
     * but with the order as two.
     * That means the above bean is going
     * to be created first followed by this bean.
     * 
     * This bean handles web page related security details `.anyRequest().authenticated()`
     * 
     * Why two different beans?
     * - Just to keep authorization server configuration into different area and web mvc path related configuration in different area
     * - In the very first bean, spring is trying to define all the configurations which are specific to the auth server.
     * 	 Whereas coming to the second bean, you can see they're trying to configure these .authenticated() and formLogin().
     *   At the end of the day, the authorization server also, it is also going to expose some secured APIs and secure pages.
     *   So all these pages, since they have to be authenticated properly, and in the case whenever we want to access these pages,
     *   we should be able to access them by entering our credentials with the help of formLogin() approach.
     *   So that's why they tried to create two different beans.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Since we don't have admin console just like we had in KeyCloak, so whenever we want to register a client,
     * this is how we need to register. We need to register a client with the help of this registered client class.
     * So inside this class we have so many methods like clientId, clientSecret, what is the type of authentication method,
     * what are the authorization grant type that your client is going to support, redirectUri, scopes.
     * So once the registered client object is created, they're trying to pass the object of this
     * to the InMemoryRegisteredClientRepository.
     * This means all the clients that we are going to configure,
     * they're going to be saved inside the memory of the application.
     * 
     * If you wanna store the client credentials in a database you can use RegisteredClientRepository
     * implementation JdbcRegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
    	// Generating a random Unique ID
		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("oidc-client")
				.clientSecret("{noop}secret")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
				.postLogoutRedirectUri("http://127.0.0.1:8080/")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();
		
		
		RegisteredClient thirdpartyCC = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("thirdpartycc")
				.clientSecret("{noop}arandomgeneratestring") // Can be stored in Bcrypt format as well
				
				/**
				 * How will the client sent its credentials ? in headers or body or in basic http standards?
				 * @CLIENT_SECRET_BASIC -> Basic HTTP Standard
				 */
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // Not require now
				
				/**
				 * Below details will require when you have UI page.
				 */
//				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
//				.postLogoutRedirectUri("http://127.0.0.1:8080/")
				
				// Defining scopes
//				.scope(OidcScopes.OPENID) 
//				.scope(OidcScopes.EMAIL)
				
				.scopes(i->i.addAll(List.of(OidcScopes.OPENID,"ADMIN","USER")))
				
				/**
				 * Defining JWT token or self contained token format along with expiration of token.
				 * So using this it will validate the tokens locally
				 */
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build())
                
                /**
                 * Taking consent from the user when redirected.
                 * So true indicates we need to take consent from the user
                 * whenever authentication is perform
                 * 
                 * Not applicable for Client Credentials grant type
                 */
//				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

		RegisteredClient thirdpartyAC = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("thirdpartyac")
				.clientSecret("{noop}arandomgeneratestringac") // Can be stored in Bcrypt format as well
				/**
				 * The auth server will expect these clientId and clientSecret as part of that RequestBody because
				 * we have specified CLIENT_SECRET_POST
				 */
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				/**
				 * Grant type
				 */
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				
				/**
				 * whenever we are configuring this authorization code, 
				 * we also want to support the Refresh Grant Type flow as well.
				 */
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				
				/**
				 * We need to invoke a method which is redirectUri(). Usually, in real applications
				 * we want the end user to be redirected to a different page once the authentication is successful.
				 * For example, like a dashboard or a profile page, it can be anything. But since right now,
				 * we are trying to mimic the client application
				 * with the help of postman, what we have to do is we have to mention the URL provided by the postman.
				 */
				.redirectUri("https://oauth.pstmn.io/v1/callback")
				
				/**
				 * ADMIN, USER scopes are not recommended scopes for client , because client requires
				 * access of products like email , or any other profile etc. ADMIN USER scopes are applicable
				 * for user
				 */
                .scope(OidcScopes.OPENID).scope(OidcScopes.EMAIL)
                
                /**
                 * Below lines defines expiration of access token
                 */
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))

                /**
                 * Below lines defines expiration of refresh token, so if any access tokens expires
                 * using refresh token we generate a new access token, so how much time that refresh token should be valid?
                 * so we can define that , also we if the refresh token gets expired so the auth server
                 * must provide a new refresh token or not that can be defined using reuseRefreshTokens() method
                 * when false, with this configuration what is going to happen is, whenever the client application,
                 * it is trying to leverage the Refresh Token Grant Type flow,
                 * it is going to get a new refresh token
                 * every time it provides a previous refresh token.
                 * Otherwise, if you configure this value as true, then always the client application
                 * is going to get the same refresh token value.
                 */
                .refreshTokenTimeToLive(Duration.ofHours(8)).reuseRefreshTokens(false)

                // JWT Token format
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build())
                
                /**
                 * 
                 */
				.build();
		
		RegisteredClient thirdpartyPKCE = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("thirdpartypkce")
				.clientSecret("{noop}arandomgeneratestringpkce") // Can be stored in Bcrypt format as well
				// PKEC flow does not have client secret
				.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

				/**
				 * Grant type (Authorization code but without client secret which becomes PKCE)
				 */
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope(OidcScopes.OPENID).scope(OidcScopes.EMAIL)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                .refreshTokenTimeToLive(Duration.ofHours(8)).reuseRefreshTokens(false)
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build())
                /**
                 * if the client is required to provide a proof key challenge 
                 * and verifier when performing the Authorization Code Grant flow.
                 * and default SHA256 algorithm is used
                 */
                .clientSettings(ClientSettings.builder().requireProofKey(true).build())
				.build();		
		
		/**
		 * So the constructor of InMemoryRegisteredClientRepository class, 
		 * it is capable of accepting any number of registered client as an input.
		 */
		return new InMemoryRegisteredClientRepository(oidcClient,thirdpartyCC,thirdpartyAC,thirdpartyPKCE);
    }

    /**
     * Any auth server that is built based upon the OAuth2 standards, behind the scenes, 
     * it's going to generate private and public certificates or keys. So using the private key 
     * or certificate, the auth server, it is going to digitally sign the access tokens, 
     * ID tokens, or any other tokens.
     * 
     * On the resource server side, they should be able to validate these tokens locally by 
     * using the public certificate or public key. So this method or this bean,
     * it is going to take care of generating a public key and a private key during the startup.
     * So it is also going to use the helper method, which is generateRsaKey().
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * So this is the helper method which will generate a key pair,
     * which is going to have both the private and the public keys.
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * Inside this method we're trying to configure the bean of JWKSource that got generated here.
     * So with this, what we're trying to tell to the auth server is, so whenever you are trying to generate an access token,
     * please digitally sign it with the help of these JwkSource.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     *  Responsible to configure all the settings inside the authorization server
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * To customize the tokens awe need to use interface OAuth2TokenCustomizer with a generic type
     * Since right now we are generating the tokens by using the jot format, we 
     * need to mention the class name as `JWTEncodingContext`.
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
    	
    	/**
    	 * So all the token values which will be generated will go under this Context object.
    	 * So the framework is going to provide an object of JWTEncodingContext as an input to this lambda expression.
    	 */
        return (context) -> {
        	
        	/**
        	 * Check the token type whether it is ACCESS_TOKEN or REFRESH_TOKEN
        	 */
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                context.getClaims().claims((claims) -> {
                	
                	/**
                	 * Checks the Grant type , in case of CLIENT_CREDENTIALS we wanted to duplicate the scope fields
                	 * and add duplicated values inside the roles field.
                	 */
                    if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
                    	
                    	/**
                    	 * So context.getClaims() is going to give me all the claims available inside the Context object or inside an access token.
                    	 * Claims are nothing but all these fields.
                    	 * 
                    	 * {
							  "sub": "thirdpartycc",
							  "aud": "thirdpartycc",
							  "nbf": 1726063137,
							  "scope": [
							    "openid",
							    "ADMIN",
							    "USER"
							  ],
							  "iss": "http://localhost:9000",
							  "exp": 1726063737,
							  "iat": 1726063137,
							  "jti": "cca9fddc-6ab4-483d-8b5d-488d1b6c9ff2"
							}
                    	 */
                        Set<String> roles = context.getClaims().build().getClaim("scope");
                        claims.put("roles", roles);
                    } else if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.AUTHORIZATION_CODE)) {
                    	System.out.println(context.getPrincipal().getAuthorities());
                        Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                                .stream()
                                .map(c -> c.replaceFirst("^ROLE_", ""))
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        System.out.println("ROLES---------------"+roles);
                        claims.put("roles", roles);
                    }
                });
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}