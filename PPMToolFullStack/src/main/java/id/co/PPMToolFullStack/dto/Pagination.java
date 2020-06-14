package id.co.PPMToolFullStack.dto;

import java.util.List;

public class Pagination {

	private List<?> content;
	private int totalPages;
	private int size;
	private Long totalElements;

	public Pagination() {
		super();
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

}
