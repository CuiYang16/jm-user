package cn.edu.imut.jm.user.domain.user.valobj;

import java.util.List;

import cn.edu.imut.infrastructrue.util.AbstractResponse;

public class ResponseVo<E> extends AbstractResponse{

	private List<E> list;
	private Integer val;
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
	public ResponseVo(List<E> list, Integer val) {
		super();
		this.list = list;
		this.val = val;
	}
	public ResponseVo(List<E> list) {
		super();
		this.list = list;
	}
	public ResponseVo(Integer val) {
		super();
		this.val = val;
	}
	public ResponseVo() {
		super();
	}
	
}
