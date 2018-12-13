package com.bruno.springboot.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	
	//para testar no post fazer Post http://localhost:8080/login ->obter o token  e depois GET http://localhost:8080/categorias/1 no Headers na Key por Authorization e no value o token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");// obter o cabecalho "Authorization" com o token -> Bearer
															// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJicnVub2NhYmFuYXNAZ21haWwuY29tIiwiZXhwIjoxNTQ0MTg4MTk3fQ.iwsN9U_hk3SvyziaX2wwwLqdonkKs67VElCncFB9-i0yx1tHN7Iaqeh_RH7lyumGRrUIVD2eXQnjpvfbD2-uHA
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));// guardar só o token
			if (auth != null) { // se auth for nulo quer dizer que o token é invalido
				SecurityContextHolder.getContext().setAuthentication(auth);// pode-se aceder
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
