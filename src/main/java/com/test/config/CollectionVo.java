package com.test.config;

public class CollectionVo {

	private int col_no;			//컬렉션 번호
	private String seed;		//seed url
	private String col_url;		//수집 url
	private String col_dt;		//수집 일시
	private String title;		//제목
	private String contents;	//내용
	private int task_no;		//작업 번호
	
	
	public CollectionVo() {
	}


	public int getCol_no() {
		return col_no;
	}


	public void setCol_no(int col_no) {
		this.col_no = col_no;
	}


	public String getSeed() {
		return seed;
	}


	public void setSeed(String seed) {
		this.seed = seed;
	}


	public String getCol_url() {
		return col_url;
	}


	public void setCol_url(String col_url) {
		this.col_url = col_url;
	}


	public String getCol_dt() {
		return col_dt;
	}


	public void setCol_dt(String col_dt) {
		this.col_dt = col_dt;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public int getTask_no() {
		return task_no;
	}


	public void setTask_no(int task_no) {
		this.task_no = task_no;
	}


	@Override
	public String toString() {
		return "CollectionVo [col_no=" + col_no + ", seed=" + seed + ", col_url=" + col_url + ", col_dt=" + col_dt
				+ ", title=" + title + ", contents=" + contents + ", task_no=" + task_no + "]";
	}


}
