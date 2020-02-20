package corp.asbp.platform.is.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import corp.asbp.platform.is.exception.OAuthResourceNotFoundException;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.repository.UserRepository;
import corp.asbp.platform.is.security.CurrentUser;
import corp.asbp.platform.is.security.UserPrincipal;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
   // @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new OAuthResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    
    @GetMapping("/user/hello")
    public User hello() {
        return userRepository.findById(1L)
                .orElseThrow(() -> new OAuthResourceNotFoundException("User", "id",1L));
    }
}
