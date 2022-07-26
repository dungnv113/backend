//package dung.spring.webbanhang.service;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Service
//public class JwtTokenProvider {
//	@Value("${jwt.secret}")
//	private String secretKey;
//	private long validityInMillionseconds = 360000;
//	public String createToken(String username) {
//		Claims claims = Jwts.claims().setSubject(username);
//		Date now = new Date();
//		Date validity = new Date(now.getTime() + validityInMillionseconds);
//		String accessToken = Jwts.builder()//
//				.setClaims(claims)//
//				.setIssuedAt(now)//
//				.setExpiration(validity)//
//				.signWith(SignatureAlgorithm.HS256, secretKey)//
//				.compact();
//		return accessToken;
//
//}
//	public boolean validateToken(String token) {
//		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//		return true;
//	}
//	
//	public Authentication getAuthentication(String token) {
//		UserDetails userDetails = 
//				userDetailsService.loadUserByUsername(getUsername(token));
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//	}
//
//	public String getUsername(String token) {
//		return Jwts.parser().setSigningKey(secretKey)
//				.parseClaimsJws(token).getBody().getSubject();
//	}
//}
