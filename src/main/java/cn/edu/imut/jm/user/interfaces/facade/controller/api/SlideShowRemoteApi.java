package cn.edu.imut.jm.user.interfaces.facade.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.imut.infrastructrue.util.ResponseVo;
import cn.edu.imut.jm.user.domain.slide.entity.SlideShow;

@RequestMapping("/slide")
public interface SlideShowRemoteApi {

	@RequestMapping(value = "/get-slides", method = { RequestMethod.GET })
	ResponseVo<SlideShow> selectSlideShows();

	@RequestMapping(value = "/insert/slide", method = { RequestMethod.POST, RequestMethod.GET })
	ResponseVo insertSlideShow(@RequestParam("file") MultipartFile slideImage);

	@RequestMapping(value = "/del-slide", method = { RequestMethod.DELETE })
	ResponseVo deleteSlideShow(@RequestBody String json);

//	前端请求
	@RequestMapping(value = "/get/slides", method = { RequestMethod.GET })
	List<SlideShow> getSlideShows();
}
