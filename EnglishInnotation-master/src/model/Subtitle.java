package model;

import java.io.Serializable;

public class Subtitle implements Serializable {

	private String time;
	private String content;
	private int second;

	public Subtitle(String time, String content, int second) {
		super();
		this.second = second;
		this.time = time;
		this.content = content;
	}

	public Subtitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

}
