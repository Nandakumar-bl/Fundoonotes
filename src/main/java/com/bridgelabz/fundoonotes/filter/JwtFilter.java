package com.bridgelabz.fundoonotes.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.serviceimplementation.UserImplementation;
import com.bridgelabz.fundoonotes.utility.Utility;

import lombok.SneakyThrows;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	Utility utility;

	@Autowired
	UserImplementation userimp;

	@Override
	@SneakyThrows
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("jwt");
		String username = null;
		String jwt = null;
		
		if (header != null) {
			jwt = header;
			username = utility.getUsernameFromToken(jwt);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails = userimp.loadUserByUsername(username);
			if (utility.validateToken(jwt)) {
				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userdetails, null,
						userdetails.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
				if (utility.validateToken(jwt))
					utility.getUser(header);
				else
					utility.cache(jwt);
			} 

		}
		
		filterChain.doFilter(request, response);
		
	}
}
