package save_review;

public class SaveReviewDTO {
	private String user_id;
	private String review_id;
	
	public SaveReviewDTO() {
		super();
	}

	public SaveReviewDTO(String user_id, String review_id) {
		super();
		this.user_id = user_id;
		this.review_id = review_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	
}
