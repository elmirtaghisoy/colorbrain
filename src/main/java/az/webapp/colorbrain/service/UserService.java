//package az.webapp.colorbrain.service;
//
//import az.webapp.colorbrain.model.entity.CUser;
//import az.webapp.colorbrain.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        CUser user = userRepository.findByEmail(email);
//        UserBuilder userBuilder;
//        if (user != null) {
//            userBuilder = User.withUsername(email);
//            userBuilder.disabled(!user.isActive());
//            userBuilder.password(user.getPassword());
//            userBuilder.authorities("ADMIN");
//        } else {
//            throw new UsernameNotFoundException("User not found !!!");
//        }
//        return userBuilder.build();
//    }
//
//}
