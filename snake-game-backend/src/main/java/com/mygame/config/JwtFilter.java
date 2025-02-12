package com.mygame.config;

import com.mygame.utils.JwtUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain; // Updated import from javax.servlet to jakarta.servlet
import jakarta.servlet.ServletException; // Updated import from javax.servlet to jakarta.servlet
import jakarta.servlet.http.HttpServletRequest; // Updated import from javax.servlet to jakarta.servlet
import jakarta.servlet.http.HttpServletResponse; // Updated import from javax.servlet to jakarta.servlet
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Missing or invalid token");
			return;
		}

		try {
			String token = header.replace("Bearer ", "");
			String username = JwtUtil.validateToken(token);
			request.setAttribute("username", username);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Invalid token");
		}
	}
}
