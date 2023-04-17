package Security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        final HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin",
                (request.getHeader("Origin") != null) ?
                    request.getHeader("Origin") : request.getHeader("Referer"));

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Cache-Control, Content-Type, X-CSRF-TOKEN");
        response.setHeader("Access-Control-Max-Age", "3600");

//        //SEND OK or validate
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {

            System.out.println("origin " + ((HttpServletRequest) req).getHeader("Origin"));

//            if(((HttpServletRequest) req).getHeader("Origin") == null ||
//                !((HttpServletRequest) req).getHeader("Origin").contains("https://e.irysc.com"))
//            {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("referer " + ((HttpServletRequest) req).getHeader("Referer"));
//            if(((HttpServletRequest) req).getHeader("Referer") == null ||
//                !((HttpServletRequest) req).getHeader("Referer").contains("https://e.irysc.com"))
//            {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        //
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
