package Service;

import Security.JwtTokenProvider;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static Main.Ticket.userRepository;

@Service
public class UserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Document whoAmI(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

}
