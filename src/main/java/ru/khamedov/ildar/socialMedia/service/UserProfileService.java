package ru.khamedov.ildar.socialMedia.service;


import jakarta.annotation.Resource;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import ru.khamedov.ildar.socialMedia.dto.UserProfileDTO;
import ru.khamedov.ildar.socialMedia.dto.UserTokenDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;
import ru.khamedov.ildar.socialMedia.util.Constant;


public class UserProfileService {

    @Resource
    private UserProfileRepository userProfileRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ModelMapperService modelMapperService;

    @Resource
    private AuthService authService;




    public UserProfile createUser(UserProfileDTO userProfileDTO){
        if(!resolveName(userProfileDTO.getName())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,userProfileDTO.getName()+Constant.ERROR_MESSAGE_VALID_NAME);
        }
        if(!isValidEmail(userProfileDTO.getEmail())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,userProfileDTO.getEmail()+Constant.ERROR_MESSAGE_VALID_EMAIL);
        }
        UserProfile userProfile=modelMapperService.converterToUser(userProfileDTO);
        userProfileRepository.save(userProfile);
        return userProfile;
    }

    private boolean resolveName(String name){
        return userProfileRepository.findById(name).isEmpty();
    }

    private boolean isValidEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    public UserProfile getUserForToken(UserTokenDTO userTokenDTO){
        UserProfile userProfile=authService.getUserByName(userTokenDTO.getName());
        if(!passwordEncoder.matches(userTokenDTO.getPassword(),userProfile.getPassword())){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,Constant.ERROR_MESSAGE_Login+userTokenDTO.getName());
        }
        return userProfile;
    }

}
