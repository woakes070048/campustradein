package com.cti.controller;


import com.cti.App;
import io.jsonwebtoken.*;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * @author ifeify
 */
public class RequiresAuthorization implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequiresAuthorization.class);
    @Override
    public void handle(Request request, Response response) throws Exception {
        // check jwt in cookie and xcsrf-code in header
        String accessTokenCookie = request.cookie(Cookies.ACCESS_TOKEN);
        String authorizationHeader = request.headers("Authorization");
        if(accessTokenCookie == null && authorizationHeader == null) {
            Spark.halt(HttpStatus.SC_UNAUTHORIZED, "{\"result\" : \"error\"}");
            return;
        }
        String accessToken;
        if(accessTokenCookie != null) {
            accessToken = accessTokenCookie;
        } else { // jwt sent in authorization header
            accessToken = authorizationHeader.substring("Bearer".length()).trim();
        }

        try {
            Jws<Claims> claims = Jwts.parser()
                                    .requireIssuer(App.ISSUER)
                                    .setSigningKey(App.KEY)
                                    .parseClaimsJws(accessToken);
        } catch(SignatureException | MissingClaimException | IncorrectClaimException e) {
            logger.error("Failed to validate access token from {}", request.ip(), e);
            Spark.halt(HttpStatus.SC_UNAUTHORIZED, "{\"result\" : \"error\"}");
        }
    }
}
