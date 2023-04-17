package Routes;

import Exceptions.NotAccessException;
import Exceptions.NotActivateAccountException;
import Exceptions.NotCompleteAccountException;
import Exceptions.UnAuthException;
import Security.JwtTokenFilter;
import Service.UserService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class Router {

    @Autowired
    private UserService userService;

    private final static JwtTokenFilter JWT_TOKEN_FILTER = new JwtTokenFilter();

    protected Document getUser(HttpServletRequest request)
            throws NotActivateAccountException, NotCompleteAccountException, UnAuthException {

        boolean auth = new JwtTokenFilter().isAuth(request);

        if(auth) {
            Document u = userService.whoAmI(request);
            if (u != null) {
                if(!u.getString("status").equals("active")) {
                    JwtTokenFilter.removeTokenFromCache(request.getHeader("Authorization").replace("Bearer ", ""));
                    throw new NotActivateAccountException("Account not activated");
                }

                return u;
            }
        }

        throw new UnAuthException("Token is not valid");
    }

    protected void getUserWithOutCheckCompletenessVoid(HttpServletRequest request)
            throws NotActivateAccountException, UnAuthException {

        boolean auth = new JwtTokenFilter().isAuth(request);

        Document u;
        if(auth) {
            u = userService.whoAmI(request);
            if (u != null) {

                if(!u.getString("status").equals("active")) {
                    JwtTokenFilter.removeTokenFromCache(request.getHeader("Authorization").replace("Bearer ", ""));
                    throw new NotActivateAccountException("Account not activated");
                }

                return;
            }
        }

        throw new UnAuthException("Token is not valid");
    }

    protected Document getUserIfLogin(HttpServletRequest request) {

        boolean auth = new JwtTokenFilter().isAuth(request);

        Document u;
        if(auth) {
            u = userService.whoAmI(request);
            if (u != null) {

                if(!u.getString("status").equals("active"))
                    return null;

                return u;
            }
        }

        return null;
    }

}
