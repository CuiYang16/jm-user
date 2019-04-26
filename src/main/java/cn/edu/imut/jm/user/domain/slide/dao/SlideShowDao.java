package cn.edu.imut.jm.user.domain.slide.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.edu.imut.jm.user.domain.slide.entity.SlideShow;

@Mapper
public interface SlideShowDao {
	List<SlideShow> selectSlideShows();

	Integer insertSlideShow(SlideShow slideShow);

	Integer deleteSlideShow(String name);
}
