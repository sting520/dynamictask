package com.power.taskcenter.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtTokenUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名称
	 */
	private static final String USERNAME = Claims.SUBJECT;
	/**
	 * 创建时间
	 */
	private static final String CREATED = "createdInterfaceTime";
	/**
	 * 权限列表
	 */
	private static final String AUTHORITIES = "authorities";
	/**
     * 密钥
     */
    private static final String SECRET = "79a1376c7ce0e5c59c65dfa2d1333ed2";
    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
	 * 生成令牌
	 *
	 * @return 令牌
	 */
	public static String generateToken(String user) {
	    Map<String, Object> claims = new HashMap<>(3);

	    claims.put(USERNAME,  user);
	    claims.put(CREATED, new Date());
	    claims.put(AUTHORITIES, "admin");
	    return generateToken(claims);
	}

	/**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    /**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
	    String username;
	    try {
	        Claims claims = getClaimsFromToken(token);
	        username = claims.getSubject();
	    } catch (Exception e) {
	        username = null;
	    }
	    return username;
	}
	
	/**
	 * 根据请求令牌获取登录认证信息
	 * @return 用户名
	 */
	public static String  getAuthenticationeFromToken(HttpServletRequest request) {
		String user = null;
		// 获取请求携带的令牌
		String token = JwtTokenUtils.getToken(request);
		if(token != null) {
			// 请求令牌不能为空
//			if(SecurityUtils.getAuthentication() == null) {
				// 上下文中Authentication为空
				Claims claims = getClaimsFromToken(token);
				if(claims == null) {
					return null;
				}
				String username = claims.getSubject();
				if(username == null) {
					return null;
				}
				if(!isTokenExpired(token)) {
					return null;
				}
				user =  username;
//				Object authors = claims.get(AUTHORITIES);
//				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//				if (authors != null && authors instanceof List) {
//					for (Object object : (List) authors) {
//						authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
//					}
//				}
//				authentication = new JwtAuthenticatioToken(username, null, authorities, token);
			}
//		else {
//				if(validateToken(token, SecurityUtils.getUsername())) {
//					// 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
//					authentication = SecurityUtils.getAuthentication();
//				}
//			}
//		}
		return user;
	}

	/**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
	 * 验证令牌
	 * @param token
	 * @param username
	 * @return
	 */
	public static Boolean validateToken(String token, String username) {
	    String userName = getUsernameFromToken(token);
	    return (userName.equals(username) && isTokenExpired(token));
	}

	/**
	 * 刷新令牌
	 * @param token
	 * @return
	 */
	public static String refreshToken(String token) {
	    String refreshedToken;
	    try {
	        Claims claims = getClaimsFromToken(token);
	        claims.put(CREATED, new Date());
	        refreshedToken = generateToken(claims);
	    } catch (Exception e) {
	        refreshedToken = null;
	    }
	    return refreshedToken;
	}

	/**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
			boolean l =expiration.after(new Date());
            return  l;
        } catch (Exception e) {
//        	e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取请求token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if(token == null) {
        	token = request.getHeader("token");
			if(token == null) {
        		token = request.getHeader("accessToken");
			}
        } else if(token.contains(tokenHead)){
        	token = token.substring(tokenHead.length());
        } 
        if("".equals(token)) {
        	token = null;
        }
        return token;
    }

	public static void main(String[] args) throws Exception {
//		TaUser user = new TaUser();
//		user.setGbcode("3299999999");
//		user.setGbname("盐城");
//		user.setUsername("jjjjjj");
//		System.out.print(generateToken(user));
	}

}