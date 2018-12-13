package com.bruno.springboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bruno.springboot.security.JWTAuthenticationFilter;
import com.bruno.springboot.security.JWTAuthorizationFilter;
import com.bruno.springboot.security.JWTUtil;

//https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)//para permitir que apenas alguns perfis de users acedam a alguns endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private Environment env; // para permitir acesso a base de dados h2

	// vector com caminhos sem restrição de acesso, publicos
	// localhost:8080/h2-console/ - permitido
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**",

	};

	// vector com caminhos sem restrição de acesso, onde apenas se pode ver os
	// dados(não se pode alterar)
	// http://localhost:8080/produtos?categorias=1 -acesso permitido
	// localhost:8080/clientes/1-acesso negado
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**","/estados/**"  };

	private static final String[] PUBLIC_MATCHERS_POST = { "/clientes","/auth/forgot/**"  };
	
	
	
	//permitir o acesso aos caminhos do Swagger
	//http://localhost:8080/swagger-ui.html
	@Override
	public void configure(WebSecurity web) throws Exception {
	 web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
	"/swagger-ui.html", "/webjars/**");
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); // se o perfil de teste, que contem a base de dados h2,estiver
														// activo permitir acesso
		}

		http.cors().and().csrf().disable(); // http.cors()-Se houver um metodo CorsConfigurationSource definido as suas
											// configurações são aplicadas. csrf().disable()porque é uma protecção
											// contra ataques a sistemas que armazenam a autenticação na sessão e este
											// sistema não armazena a sessão
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // Apenas permite POST nos caminhos do vector PUBLIC_MATCHERS_POST
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()// Apenas permite o metodo get no vector PUBLIC_MATCHERS_GET
				.antMatchers(PUBLIC_MATCHERS).permitAll() // todos os caminhos que estão no vector PUBLIC_MATCHERS não  necessitam autenticação
				.anyRequest().authenticated(); // todo o resto tem de ter autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));//fazer a autenticação
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));//obter a autorização para aceder a endpoints
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// this disables session
																						// creation on Spring Security
	}

				//http://localhost:8080/login e escrever no body o email e a senha
	@Override//metodo para autenticacao informand o user e a pass
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean // permisão aos endpoints por multiplas fontes com as configurações basicas
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));//metodos em que se permite o cors
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean // encriptar a senha
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
