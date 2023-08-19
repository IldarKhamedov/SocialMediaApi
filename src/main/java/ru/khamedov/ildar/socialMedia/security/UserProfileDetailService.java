package ru.khamedov.ildar.socialMedia.security;

import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;
import ru.khamedov.ildar.socialMedia.util.Constant;

import java.util.ArrayList;
import java.util.Optional;


public class UserProfileDetailService implements UserDetailsService {

    private static final String ERROR_MESSAGE="User not found!";
    @Resource
    private UserProfileRepository userProfileRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
       Optional<UserProfile> userProfileOptional =userProfileRepository.findById(username);
        if (userProfileOptional.get() == null) {
            throw new UsernameNotFoundException(ERROR_MESSAGE);
        }
            authorities.add(new SimpleGrantedAuthority(Constant.AUTHORITY_ROLE));
        UserProfile userProfile=userProfileOptional.get();
        return new User(userProfile.getName(), userProfile.getPassword(),true,true, true, true, authorities);
    }
}
