package cn.edu.imut.jm.user.domain.slide.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SlideShow {
	/**
	 * 
	 */
	private Integer slideId;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String url;

	/**
	 * 
	 */
	private Boolean showFlag;

	public Integer getSlideId() {
		return slideId;
	}

	public void setSlideId(Integer slideId) {
		this.slideId = slideId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Boolean getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Boolean showFlag) {
		this.showFlag = showFlag;
	}

	public SlideShow(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public SlideShow() {
		super();
	}

	public SlideShow(Integer slideId, String name, String url, Boolean showFlag) {
		super();
		this.slideId = slideId;
		this.name = name;
		this.url = url;
		this.showFlag = showFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}