package movie;

public class MovieDTO {
	private int movie_id;
	private String title;
	private String cate;
	private String open_date;
	private String actor;
	private int m_time;
	private String grade;
	private String director;
	private String image;
	private String trailer;
	private String user_id;
	private String summary;
	
	public MovieDTO() {
		super();
	}

	public MovieDTO(int movie_id, String title, String cate, String open_date, String actor, int m_time, String grade,
			String director, String image) {
		super();
		this.movie_id = movie_id;
		this.title = title;
		this.cate = cate;
		this.open_date = open_date;
		this.actor = actor;
		this.m_time = m_time;
		this.grade = grade;
		this.director = director;
		this.image = image;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getOpen_date() {
		return open_date;
	}

	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public int getM_time() {
		return m_time;
	}

	public void setM_time(int m_time) {
		this.m_time = m_time;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MovieDTO [movie_id=" + movie_id + ", title=" + title + ", cate=" + cate + ", open_date=" + open_date
				+ ", actor=" + actor + ", m_time=" + m_time + ", grade=" + grade + ", director=" + director + ", image="
				+ image + "]";
	}
	
}
