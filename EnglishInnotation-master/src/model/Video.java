package model;

import java.io.Serializable;

public class Video implements Serializable {
	private String name;
	private String urlVideo;
	private String urlSubtitle;

	private static final long serialVersionUID = 1L;

	public Video(String name, String urlVideo) {
		super();
		this.name = name;
		this.urlVideo = urlVideo;
	}

	public Video(String name, String urlVideo, String urlSubtitle) {
		super();
		this.name = name;
		this.urlVideo = urlVideo;
		this.urlSubtitle = urlSubtitle;
	}

	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public String getUrlSubtitle() {
		return urlSubtitle;
	}

	public void setUrlSubtitle(String urlSubtitle) {
		this.urlSubtitle = urlSubtitle;
	}

}
