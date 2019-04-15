package cn.edu.imut.infrastructrue.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenUtil {

	private static String privateKey = "123456";
	private static String publicKey = null;

	private static final long EXPIRE_TIME = 15 * 60 * 1000;

	/**
	 * 生成token
	 * 
	 * @param userName
	 * @param userPwd
	 * @return
	 */
	public static String generateToken(String userName, Integer userId) {
		Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		Algorithm algorithm = Algorithm.HMAC256(privateKey);
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		return JWT.create().withHeader(header).withClaim("userName", userName).withClaim("userId", userId)
				.withExpiresAt(expiresAt).sign(algorithm);
	}

	/**
	 * 检验是否token正确
	 * 
	 * @param token
	 * @return
	 */
	public static boolean parseToken(String token) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			JWTVerifier jwtVerifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	// 获取token自定义属性
	public static Map<String, Claim> getClaims(String token) {
		Map<String, Claim> claims = null;
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			claims = decodedJWT.getClaims();
			return claims;
		} catch (Exception e) {
			return null;
		}

	}

	public static Integer getUserId(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			Integer userId = decodedJWT.getClaim("userId").asInt();
			return userId;
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	// 是否已过期
	public static boolean isExpiration(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		boolean before = decodedJWT.getExpiresAt().before(new Date());
		return before;
	}

}
