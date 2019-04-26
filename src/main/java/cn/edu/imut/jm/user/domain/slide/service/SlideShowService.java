package cn.edu.imut.jm.user.domain.slide.service;

import java.util.List;

import cn.edu.imut.jm.user.domain.slide.entity.SlideShow;

public interface SlideShowService {

	List<SlideShow> selectSlideShows();

	Integer insertSlideShow(SlideShow slideShow);

	Integer deleteSlideShow(String name);
}
