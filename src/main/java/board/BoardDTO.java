package board;

public class BoardDTO {
	private int b_id;
	private String user_id;
    private String title;
    private String content;
    private java.sql.Date postdate;
    private int visitcount;
    private int re_ref;
    private int re_level;
    private int re_sequence;
    
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public java.sql.Date getPostdate() {
		return postdate;
	}
	public void setPostdate(java.sql.Date postdate) {
		this.postdate = postdate;
	}
	public int getVisitcount() {
		return visitcount;
	}
	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_level() {
		return re_level;
	}
	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}
	public int getRe_sequence() {
		return re_sequence;
	}
	public void setRe_sequence(int re_sequence) {
		this.re_sequence = re_sequence;
	}
	@Override
	public String toString() {
		return "BoardDTO [b_id=" + b_id + ", user_id=" + user_id + ", title=" + title + ", content=" + content
				+ ", postdate=" + postdate + ", visitcount=" + visitcount + ", re_ref=" + re_ref + ", re_level="
				+ re_level + ", re_sequence=" + re_sequence + "]";
	}
}
