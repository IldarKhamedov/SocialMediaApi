package ru.khamedov.ildar.socialMedia;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.post.ImageContent;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;
import ru.khamedov.ildar.socialMedia.model.post.Post;
import ru.khamedov.ildar.socialMedia.service.ModelMapperService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SocialMediaApplicationTests {

	@Resource
	private ModelMapper modelMapper;

	@Resource
	private ModelMapperService modelMapperService;

	@Test
	void contextLoads() {
	}

	@Test
	void testPostToDTO(){
		String text="text";
		Post post=new Post();
		post.setAuthor(new UserProfile());
		post.setText(text);
		post.setTitle(text);
		List<ImageFile> imageFileList=new ArrayList<>();
		for(int i=0; i<10; i++){
			imageFileList.add(new ImageFile(new ImageContent(text.getBytes()),10l,"xml",post));
		}
		post.setImageFileList(imageFileList);
		PostDTO postDTO=modelMapperService.converterToPostDTO(post);
		Assertions.assertEquals(post.getImageFileList().size(),postDTO.getImageFileDTOList().size());
	}

}
