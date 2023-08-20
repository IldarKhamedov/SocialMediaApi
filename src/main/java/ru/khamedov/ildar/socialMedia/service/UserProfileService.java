package ru.khamedov.ildar.socialMedia.service;


import jakarta.annotation.Resource;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khamedov.ildar.socialMedia.dto.UserProfileDTO;
import ru.khamedov.ildar.socialMedia.dto.UserTokenDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;



public class UserProfileService {

    @Resource
    private UserProfileRepository userProfileRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ModelMapper modelMapper;

    public UserProfile createUser(UserProfileDTO userProfileDTO){
        UserProfile userProfile=null;
        if(resolveName(userProfileDTO.getName()) && isValidEmail(userProfileDTO.getEmail())){
            userProfile=converterUser(userProfileDTO);
            userProfileRepository.save(userProfile);
        }
        return userProfile;
    }

    private boolean resolveName(String name){
        return userProfileRepository.findById(name).isEmpty();
    }
    private boolean isValidEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }
    private UserProfile converterUser(UserProfileDTO userProfileDTO){
        Converter<String,String> converter= context -> passwordEncoder.encode(context.getSource());
        modelMapper.typeMap(UserProfileDTO.class,UserProfile.class).addMappings(mapper -> mapper.using(converter).map(UserProfileDTO::getPassword, UserProfile::setPassword));
        return modelMapper.map(userProfileDTO,UserProfile.class);
    }
    public UserProfile getUserForToken(UserTokenDTO userTokenDTO){
        UserProfile userProfile=userProfileRepository.findById(userTokenDTO.getName()).get();
        if(userProfile != null && passwordEncoder.matches(userTokenDTO.getPassword(),userProfile.getPassword())){
            return userProfile;
        }
        return null;
    }
}
