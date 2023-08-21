package ru.khamedov.ildar.socialMedia.service;

import jakarta.annotation.Resource;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khamedov.ildar.socialMedia.dto.*;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.communication.Message;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;
import ru.khamedov.ildar.socialMedia.model.post.Post;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    public UserProfile converterToUser(UserProfileDTO userProfileDTO){
        Converter<String,String> converter= context -> passwordEncoder.encode(context.getSource());
        modelMapper.typeMap(UserProfileDTO.class,UserProfile.class).addMappings(mapper -> mapper.using(converter).map(UserProfileDTO::getPassword, UserProfile::setPassword));
        return modelMapper.map(userProfileDTO,UserProfile.class);
    }

    public Post converterToPost(PostDTO postDTO){
        modelMapper.typeMap(PostDTO.class, Post.class).addMappings(mapper -> mapper.skip(Post::setAuthor));
        return modelMapper.map(postDTO,Post.class);
    }

    public ImageFile converterToImageFile(ImageFileDTO imageFileDTO){
        return modelMapper.map(imageFileDTO, ImageFile.class);
    }

    public PostDTO converterToPostDTO(Post post){
        Converter<Instant, Date> converter= context -> Date.from(context.getSource());
        modelMapper.typeMap(Post.class,PostDTO.class).addMappings(mapper -> mapper.using(converter).map(Post::getCreated, PostDTO::setCreated));
        PostDTO postDTO=modelMapper.map(post,PostDTO.class);
        fillImageFileDTO(post,postDTO);
        return postDTO;
    }

    private void fillImageFileDTO(Post post, PostDTO postDTO){
        List<ImageFileDTO> imageFileDTOList=new ArrayList<>();
        for(ImageFile imageFile:post.getImageFileList()){
            ImageContentDTO imageContentDTO=modelMapper.map(imageFile.getImageContent(),ImageContentDTO.class);
            ImageFileDTO imageFileDTO=modelMapper.map(imageFile,ImageFileDTO.class);
            imageFileDTO.setImageContentDTO(imageContentDTO);
            imageFileDTOList.add(imageFileDTO);
        }
        postDTO.setImageFileDTOList(imageFileDTOList);
    }

    public List<MessagesDTO> converterToMessagesDTO(List<Message> messageList){
        Converter<Instant, Date> converter= context -> Date.from(context.getSource());
        TypeMap<Message, MessagesDTO> propertyMapper = modelMapper.createTypeMap(Message.class, MessagesDTO.class);
        propertyMapper.addMappings(mapper -> mapper.using(converter).map(Message::getCreated, MessagesDTO::setCreated));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getSender().getName(), MessagesDTO::setSender)
        );
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getReceiver().getName(), MessagesDTO::setReceiver)
        );
        return messageList.stream().map(m->modelMapper.map(m,MessagesDTO.class)).collect(Collectors.toList());
    }
}
