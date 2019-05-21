package cn.edu.imut.jm.user.domain.slide.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.imut.jm.user.domain.slide.dao.SlideShowDao;
import cn.edu.imut.jm.user.domain.slide.entity.SlideShow;
import cn.edu.imut.jm.user.domain.slide.service.SlideShowService;

@Service
public class SlideShowServiceImpl implements SlideShowService {

	@Autowired
	private SlideShowDao slideShowDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer insertSlideShow(SlideShow slideShow) {
		if (slideShow != null && slideShow.getName() != null) {
			return slideShowDao.insertSlideShow(slideShow);
		}
		return null;
	}

	@Override
	public List<SlideShow> selectSlideShows() {

		return slideShowDao.selectSlideShows();
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer deleteSlideShow(String name) {
		if (name != null && name.length() > 0) {
			return slideShowDao.deleteSlideShow(name);
		}
		return null;
	};
}
