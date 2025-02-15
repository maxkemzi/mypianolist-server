package com.maxkemzi.mypianolist.auth.service.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.service.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	private final SecretKey accessTokenKey;
	private final SecretKey refreshTokenKey;

	public JwtService() {
		this.accessTokenKey = generateKey();
		this.refreshTokenKey = generateKey();
	}

	private SecretKey generateKey() {
		return Jwts.SIG.HS256.key().build();
	}

	public JwtTokens generateAccessAndRefreshTokens(UserPrincipal userPrincipal) {
		String accessToken = generateAccessToken(userPrincipal);
		String refreshToken = generateRefreshToken(userPrincipal);

		return new JwtTokens(accessToken, refreshToken);
	}

	private String generateAccessToken(UserPrincipal userPrincipal) {
		return generateToken(userPrincipal, accessTokenKey, TimeUnit.MINUTES.toMillis(30)); // 30 minutes
	}

	private String generateRefreshToken(UserPrincipal userPrincipal) {
		return generateToken(userPrincipal, refreshTokenKey, TimeUnit.DAYS.toMillis(30)); // 30 days
	}

	private String generateToken(UserPrincipal user, SecretKey key, long expireInMs) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.getUsername());
		claims.put("avatar", user.getAvatar());

		long now = System.currentTimeMillis();

		return Jwts
				.builder()
				.claims()
				.add(claims)
				.issuedAt(new Date(now))
				.expiration(new Date(now + expireInMs))
				.and()
				.signWith(key)
				.compact();
	}

	public JwtUser verifyAccessToken(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(accessTokenKey).build().parseSignedClaims(token).getPayload();

			return new JwtUser(claims.get("username", String.class), claims.get("avatar", String.class));
		} catch (JwtException e) {
			return null;
		}
	}
}
