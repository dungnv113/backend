//package dung.spring.webbanhang.service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//			FilterChain filterChain) throws ServletException, IOException {
//
//		String token = resolveToken(httpServletRequest);
//
//		try {
//			if (token != null && jwtTokenProvider.validateToken(token)) {
//				Authentication auth = jwtTokenProvider.getAuthentication(token);
////auth: doi tuong lay tu db
//				SecurityContextHolder.getContext().setAuthentication(auth);  //phan quyen cho doi tuong dua vao security spring
//			}
//		} catch (Exception ex) {
//			// this is very important, since it guarantees the user is not authenticated at
//			// all
//			SecurityContextHolder.clearContext();
//			httpServletResponse.sendError(401, ex.getMessage());
//			return;
//		}
//
//		filterChain.doFilter(httpServletRequest, httpServletResponse);
//	}
//
//	public String resolveToken(HttpServletRequest req) {
//		String bearerToken = req.getHeader("Authorization");
//		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7);
//		}
//		return null;
//	}
//}
