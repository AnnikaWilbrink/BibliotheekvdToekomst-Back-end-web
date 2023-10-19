package nl.workingtalent.wtlibrary.dto;

import java.util.List;

public class FilterBookDto {

    private String filterWord;

    private List<String> categories;

    private Integer minReviewScore;

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

	public Integer getMinReviewScore() {
		return minReviewScore;
	}

	public void setMinReviewScore(Integer minReviewScore) {
		this.minReviewScore = minReviewScore;
	}

}
