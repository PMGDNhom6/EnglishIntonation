package model;

import java.io.Serializable;

public class Favorite implements Serializable {
	private Video vidReference;
	private Subtitle subReference;

	public Favorite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Favorite(Video vidReference, Subtitle subReference) {
		super();
		this.vidReference = vidReference;
		this.subReference = subReference;
	}

	public Video getVidReference() {
		return vidReference;
	}

	public void setVidReference(Video vidReference) {
		this.vidReference = vidReference;
	}

	public Subtitle getSubReference() {
		return subReference;
	}

	public void setSubReference(Subtitle subReference) {
		this.subReference = subReference;
	}

}
