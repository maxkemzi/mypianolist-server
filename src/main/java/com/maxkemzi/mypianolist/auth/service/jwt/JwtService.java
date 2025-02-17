package com.maxkemzi.mypianolist.auth.service.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.auth.service.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.access-token.key}")
	private String accessTokenKey;

	@Value("${jwt.refresh-token.key}")
	private String refreshTokenKey;

	public JwtTokens generateAccessAndRefreshTokens(UserPrincipal userPrincipal) {
		String accessToken = generateAccessToken(userPrincipal);
		String refreshToken = generateRefreshToken(userPrincipal);

		return new JwtTokens(accessToken, refreshToken);
	}

	private String generateAccessToken(UserPrincipal userPrincipal) {
		return generateToken(userPrincipal, getAccessTokenKey(), TimeUnit.MINUTES.toMillis(30)); // 30 minutes
	}

	private String generateRefreshToken(UserPrincipal userPrincipal) {
		return generateToken(userPrincipal, getRefreshTokenKey(), TimeUnit.DAYS.toMillis(30)); // 30 days
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
			Claims claims = Jwts.parser().verifyWith(getAccessTokenKey()).build().parseSignedClaims(token)
					.getPayload();

			return new JwtUser(claims.get("username", String.class), claims.get("avatar", String.class));
		} catch (JwtException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private SecretKey getAccessTokenKey() {
		return getSigningKey(accessTokenKey);
	}

	private SecretKey getRefreshTokenKey() {
		return getSigningKey(refreshTokenKey);
	}

	private SecretKey getSigningKey(String key) {
		byte[] decodedKey = Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(decodedKey);
	}
}
