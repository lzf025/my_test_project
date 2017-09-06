package com.ggxueche.utils.photo;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;

public class ImageItem implements Serializable {
	private static final long serialVersionUID = -8257351176702205741L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	private String imageUrl;
	private boolean isUploadSuccess;
	public boolean isSelected = false;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isUploadSuccess() {
		return isUploadSuccess;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		isUploadSuccess = uploadSuccess;
	}
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
			try {
				bitmap = BitmapUtil.revitionImageSize(imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	@Override
	public boolean equals(Object arg0) {
		if (arg0 != null && arg0 instanceof ImageItem) {
			return this.getImageId().equals(((ImageItem) arg0).getImageId());
		}
		return false;
	}
}
