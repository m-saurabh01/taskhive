package com.sync.workflow.taskhiveusermanagement.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sync.workflow.taskhiveusermanagement.serviceImpl.CustomUserDetailsService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter implements Filter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		String jwtToken = null;
		String email = null;

		String requestURI = request.getRequestURI();

		if (requestURI.contains("/auth/login") || requestURI.contains("/auth/register")) {

			filterChain.doFilter(request, response);

			return;
		}

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwtToken = authHeader.substring(7);
			email = jwtUtil.extractUsername(jwtToken);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			String cachedToken = jwtUtil.getCachedToken(email);

			if (cachedToken != null && cachedToken.equals(jwtToken)) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

				if (jwtUtil.isTokenExpired(jwtToken)) {
					jwtUtil.invalidateToken(jwtToken);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired or invalid");
		            return;					
				} else if (jwtUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		}

		filterChain.doFilter(request, response);

	}

}
