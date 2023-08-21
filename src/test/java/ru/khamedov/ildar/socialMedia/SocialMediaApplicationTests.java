package ru.khamedov.ildar.socialMedia;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.*;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.post.ImageContent;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;
import ru.khamedov.ildar.socialMedia.model.post.Post;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;
import ru.khamedov.ildar.socialMedia.service.ModelMapperService;
import ru.khamedov.ildar.socialMedia.util.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class SocialMediaApplicationTests {

	@Resource
	private ModelMapper modelMapper;

	@Resource
	private ModelMapperService modelMapperService;

	@Resource
	private JwtDecoder jwtDecoder;

	@Resource
	private JwtEncoder jwtEncoder;

	@Value("${spring.security.jwt.secret.algorithm}")
	private String algorithm;

	private static final String NAME="test";

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

	@Test
	void testToken(){
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.claim(Constant.SCOPE,Constant.SCOPE_VALUE)
				.claim(Constant.USER_NAME,NAME)
				.build();
		JwsHeader jwsHeader = JwsHeader.with(() -> algorithm).build();
		Jwt jwtEncode=jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims));
		String token=jwtEncode.getTokenValue();
		Jwt jwtDecode=jwtDecoder.decode(token);
		Assertions.assertEquals(jwtEncode,jwtDecode);
	}

}
