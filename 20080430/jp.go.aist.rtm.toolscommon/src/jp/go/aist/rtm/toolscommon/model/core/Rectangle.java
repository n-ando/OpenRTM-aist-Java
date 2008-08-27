package jp.go.aist.rtm.toolscommon.model.core;

import java.io.Serializable;


/**
 * 制限（矩形）を表すクラス
 */
public class Rectangle implements Serializable {
	private int x;

	private int y;

	private int width;

	private int height;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
