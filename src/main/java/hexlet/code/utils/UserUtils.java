package hexlet.code.utils;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userUtils")
@AllArgsConstructor
public class UserUtils {

    @Autowired
    private UserRepository userRepository;
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean isCurrentUser(long userId) {
        var userEmail = userRepository.findById(userId).get().getUsername();
        var authenticationEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEmail.equals(authenticationEmail);
    }
}
