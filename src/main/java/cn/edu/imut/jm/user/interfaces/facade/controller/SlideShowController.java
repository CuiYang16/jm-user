package cn.edu.imut.jm.user.interfaces.facade.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.edu.imut.infrastructrue.util.ResponseVo;
import cn.edu.imut.jm.user.domain.slide.entity.SlideShow;
import cn.edu.imut.jm.user.domain.slide.service.SlideShowService;
import cn.edu.imut.jm.user.interfaces.facade.controller.api.SlideShowRemoteApi;

@RestController
public class SlideShowController implements SlideShowRemoteApi {

	@Autowired
	private SlideShowService slideShowService;
	private static final String SLIDE_IMG_FILE_PATH = "F:/MyWorkSpace/bishe-vue/journal-door/static/slide-img/";

	public ResponseVo insertSlideShow(@RequestParam("file") MultipartFile slideImage) {

		if (slideImage == null || slideImage.isEmpty()) {
			return new ResponseVo<>(0, "文件为空");
		}
		String fileName = System.currentTimeMillis() + "-slide"
				+ slideImage.getOriginalFilename().substring(slideImage.getOriginalFilename().lastIndexOf("."));
		String filePath = SLIDE_IMG_FILE_PATH + fileName;
		File file = new File(filePath);
		if (!file.getParentFile().exists()) { // 判断文件父目录是否存在
			file.getParentFile().mkdir();
		}
		if (slideImage.getOriginalFilename().endsWith(".jpg") || slideImage.getOriginalFilename().endsWith(".jpeg")
				|| slideImage.getOriginalFilename().endsWith(".png")) {
			try {
				slideImage.transferTo(file);
				SlideShow slideShow = new SlideShow(fileName, filePath);
				Integer insertSlideShow = slideShowService.insertSlideShow(slideShow);
				if (insertSlideShow == 1) {
					return new ResponseVo<>(insertSlideShow, fileName);
				} else {
					return new ResponseVo(0, "上传失败，请重试");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseVo(0, "上传失败，请重试");
			}
		} else {
			return new ResponseVo(0, "上传失败，只允许上传.jpg/.jpeg/.png图片");
		}
	}

	@Override
	public ResponseVo<SlideShow> selectSlideShows() {

		return new ResponseVo<>(slideShowService.selectSlideShows());
	}

	@Override
	public ResponseVo deleteSlideShow(@RequestBody String json) {
		String name = JSON.parseObject(json).getString("name");
		Integer deleteSlideShow = slideShowService.deleteSlideShow(name);
		if (deleteSlideShow == 1) {
			File file = new File(SLIDE_IMG_FILE_PATH + name);
			if (file.delete()) {
				return new ResponseVo<>(1);
			}

		}
		return new ResponseVo<>(0);
	}

//	前端请求
	@Override
	public List<SlideShow> getSlideShows() {

		return slideShowService.selectSlideShows();
	}
}
