package com.hust.project3.phonesellingweb.model;

public class TempImageUploadItem {

	private String originName;
	private String tempPath;
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public TempImageUploadItem(String originName, String tempPath) {
		super();
		this.originName = originName;
		this.tempPath = tempPath;
	}
	@Override
	public String toString() {
		return "ResponseTempImageUpload [originName=" + originName + ", tempPath=" + tempPath + "]";
	}
	public TempImageUploadItem() {
		super();
	}
	
	
}
