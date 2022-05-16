package com.example.myproject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.Resource;
import java.util.Date;

public class TokenUtil {

    /**
     * 有效时长
     */
    public static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    /**
     * 密钥
     */
    private static final String TOKEN_SECRET = "ben";

    /**
     * 签名生成
     *
     * @param id id
     * @return token
     */
    public static String sign(String id) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", id)
                    .withExpiresAt(expiresAt)
                    //使用HMAC256算法加密
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256((TOKEN_SECRET)))
                    .withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过");
            System.out.println("id=" + jwt.getClaim("id").asString());
            System.out.println("过期时间：" + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getId(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256((TOKEN_SECRET)))
                    .withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            return Integer.parseInt(jwt.getClaim("id").asString());
        } catch (Exception e) {
            return -1;
        }
    }


    public static int getIdFromRedis(RedisUtils redisUtils, String token) {
        return (int) redisUtils.get(token);
    }

}
