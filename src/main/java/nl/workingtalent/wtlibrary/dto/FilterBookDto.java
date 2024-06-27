package nl.workingtalent.wtlibrary.dto;

import java.util.List;

public class FilterBookDto {

    private String filterWord;

    private List<String> categories;

	private List<String> subject;

	private Integer minReviewScore;

	private String sortField;

	private String sortOrder;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getFilterWord() {
		return filterWord;
	}

	public void setFilterWord(String filterWord) {
		this.filterWord = filterWord;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public Integer getMinReviewScore() {
		return minReviewScore;
	}

	public void setMinReviewScore(Integer minReviewScore) {
		this.minReviewScore = minReviewScore;
	}

}
