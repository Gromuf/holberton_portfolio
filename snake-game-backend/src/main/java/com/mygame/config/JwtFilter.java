package com.mygame.config;

import com.mygame.utils.JwtUtil;
import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final PlayerRepository playerRepository;
	private final JwtUtil jwtUtil; // Inject JwtUtil instead of using static methods

	public JwtFilter(PlayerRepository playerRepository, JwtUtil jwtUtil) {
		this.playerRepository = playerRepository;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		String path = request.getRequestURI();
		if (path.equals("/auth/logout") || header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.replace("Bearer ", "");

		try {
			String username = jwtUtil.validateToken(token); // Now using instance method

			Optional<Player> playerOptional = playerRepository.findByUsername(username);

			if (playerOptional.isPresent()) {
				UserDetails userDetails = User.withUsername(username)
						.password(playerOptional.get().getPassword())
						.roles("USER")
						.build();

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Invalid token");
			return;
		}

		filterChain.doFilter(request, response);
	}
}
