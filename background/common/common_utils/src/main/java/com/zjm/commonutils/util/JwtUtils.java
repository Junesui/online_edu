package com.zjm.commonutils.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author ZengJinming
 * @since 2020/04/09
 */
public class JwtUtils {

    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    private static Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(APP_SECRET);
        return new SecretKeySpec(bytes,signatureAlgorithm.getJcaName());
    }

    public static String getJwtToken(JwtInfo jwtInfo, int expire){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("MindSchool-user")//主题
                .setIssuedAt(new Date())//颁发时间
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())//过期时间
                .claim("id", jwtInfo.getId())//用户id
                .claim("nickname", jwtInfo.getNickname())//用户昵称
                .claim("avatar", jwtInfo.getAvatar())//用户头像
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkJwtTToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkJwtTToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static JwtInfo getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        JwtInfo jwtInfo = new JwtInfo(claims.get("id").toString(), claims.get("nickname").toString(), claims.get("avatar").toString());
//        JwtInfo jwtInfo = new JwtInfo(claims.get("id").toString(), claims.get("nickname").toString(), claims.get("avatar").toString(), claims.get("age").toString(), claims.get("sex").toString(), claims.get("mobile").toString());
        return jwtInfo;
    }
}

//public class JwtUtils {
//
//    //常量
//    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
//    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥加密操作
//
//    //生成token字符串的方法
//    public static String getJwtToken(String id, String nickname){
//
//        String JwtToken = Jwts.builder()
//                /*jwt头信息*/
//                .setHeaderParam("typ", "JWT")
//                .setHeaderParam("alg", "HS256")
//
//                /*设置过期时间*/
//                .setSubject("MindSchool-user")  //主题
//                .setIssuedAt(new Date())  //颁发时间
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
//
//                //设置token主体部分 ，存储用户信息
//                .claim("id", id)
//                .claim("nickname", nickname)
//
//                /*签名哈希*/
//                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
//                .compact();
//
//        return JwtToken;
//    }
//
//    /**
//     * 判断token是否存在与有效
//     * @param jwtToken
//     * @return
//     */
//    public static boolean checkToken(String jwtToken) {
//        if(StringUtils.isEmpty(jwtToken)) return false;
//        try {
//            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 判断token是否存在与有效
//     * @param request
//     * @return
//     */
//    public static boolean checkToken(HttpServletRequest request) {
//        try {
//            String jwtToken = request.getHeader("token");
//            if(StringUtils.isEmpty(jwtToken)) return false;
//            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 根据token字符串获取会员id
//     * @param request
//     * @return
//     */
//    public static String getMemberIdByJwtToken(HttpServletRequest request) {
//        String jwtToken = request.getHeader("token");
//        if(StringUtils.isEmpty(jwtToken)) return "";
//        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        Claims claims = claimsJws.getBody();
//        return (String)claims.get("id");
//    }
//}
