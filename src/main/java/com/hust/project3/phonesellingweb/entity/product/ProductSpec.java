package com.hust.project3.phonesellingweb.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_spec")
public class ProductSpec {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int specId;
	
	private String displayTech, displayRes, size, freq, nit, os, cpu, cpuSpeed, gpu, ram,
		rom, sim, wifi, gps, bluetooth, pinType, pinCap, pinTech, pinWatt,
		ipXx, designLang, designMaterial , weight, releasedDate, otherTech, description;
	
	@Column(name="backcamera_res")
	private String backCameraRes;
	
	@Column(name="backcamera_video")
	private String backCameraVideo;
	
	public String getDisplayRes() {
		return displayRes;
	}

	public void setDisplayRes(String displayRes) {
		this.displayRes = displayRes;
	}

	@Column(name="backcamera_func")
	private String backCameraFunc;
	
	@Column(name="frontcamera_res")
	private String frontCameraRes;
	
	@Column(name="frontcamera_video")
	private String frontCameraVideo;
	
	@Column(name="frontcamera_func")
	private String frontCameraFunc;
	
	@Column(name="mobileinternet")
	private String mobileInternet;
	
	@Column(name="otherport")
	private String otherPort;
	
	@Column(name="chargeport")
	private String chargePort;
	
	@Column(name="jackport")
	private String jackPort;

	public int getSpecId() {
		return specId;
	}

	public void setSpecId(int specId) {
		this.specId = specId;
	}

	public String getDisplayTech() {
		return displayTech;
	}

	public void setDisplayTech(String displayTech) {
		this.displayTech = displayTech;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getCpuSpeed() {
		return cpuSpeed;
	}

	public void setCpuSpeed(String cpuSpeed) {
		this.cpuSpeed = cpuSpeed;
	}

	public String getGpu() {
		return gpu;
	}

	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getPinType() {
		return pinType;
	}

	public void setPinType(String pinType) {
		this.pinType = pinType;
	}

	public String getPinCap() {
		return pinCap;
	}

	public void setPinCap(String pinCap) {
		this.pinCap = pinCap;
	}

	public String getPinTech() {
		return pinTech;
	}

	public void setPinTech(String pinTech) {
		this.pinTech = pinTech;
	}

	public String getPinWatt() {
		return pinWatt;
	}

	public void setPinWatt(String pinWatt) {
		this.pinWatt = pinWatt;
	}

	public String getIpXx() {
		return ipXx;
	}

	public void setIpXx(String ipXx) {
		this.ipXx = ipXx;
	}

	public String getDesignLang() {
		return designLang;
	}

	public void setDesignLang(String designLang) {
		this.designLang = designLang;
	}

	public String getDesignMaterial() {
		return designMaterial;
	}

	public void setDesignMaterial(String designMaterial) {
		this.designMaterial = designMaterial;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getOtherTech() {
		return otherTech;
	}

	public void setOtherTech(String otherTech) {
		this.otherTech = otherTech;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBackCameraRes() {
		return backCameraRes;
	}

	public void setBackCameraRes(String backCameraRes) {
		this.backCameraRes = backCameraRes;
	}

	public String getBackCameraVideo() {
		return backCameraVideo;
	}

	public void setBackCameraVideo(String backCameraVideo) {
		this.backCameraVideo = backCameraVideo;
	}

	public String getBackCameraFunc() {
		return backCameraFunc;
	}

	public void setBackCameraFunc(String backCameraFunc) {
		this.backCameraFunc = backCameraFunc;
	}

	public String getFrontCameraRes() {
		return frontCameraRes;
	}

	public void setFrontCameraRes(String frontCameraRes) {
		this.frontCameraRes = frontCameraRes;
	}

	public String getFrontCameraVideo() {
		return frontCameraVideo;
	}

	public void setFrontCameraVideo(String frontCameraVideo) {
		this.frontCameraVideo = frontCameraVideo;
	}

	public String getFrontCameraFunc() {
		return frontCameraFunc;
	}

	public void setFrontCameraFunc(String frontCameraFunc) {
		this.frontCameraFunc = frontCameraFunc;
	}

	public String getMobileInternet() {
		return mobileInternet;
	}

	public void setMobileInternet(String mobileInternet) {
		this.mobileInternet = mobileInternet;
	}

	public String getOtherPort() {
		return otherPort;
	}

	public void setOtherPort(String otherport) {
		this.otherPort = otherport;
	}

	public String getChargePort() {
		return chargePort;
	}

	public void setChargePort(String chargePort) {
		this.chargePort = chargePort;
	}

	public String getJackPort() {
		return jackPort;
	}

	public void setJackPort(String jackPort) {
		this.jackPort = jackPort;
	}

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	@Override
	public String toString() {
		return "ProductSpec [specId=" + specId + ", displayTech=" + displayTech + ", displayRes=" + displayRes
				+ ", size=" + size + ", freq=" + freq + ", nit=" + nit + ", os=" + os + ", cpu=" + cpu + ", cpuSpeed="
				+ cpuSpeed + ", gpu=" + gpu + ", ram=" + ram + ", rom=" + rom + ", sim=" + sim + ", wifi=" + wifi
				+ ", gps=" + gps + ", bluetooth=" + bluetooth + ", pinType=" + pinType + ", pinCap=" + pinCap
				+ ", pinTech=" + pinTech + ", pinWatt=" + pinWatt + ", ipXx=" + ipXx + ", designLang=" + designLang
				+ ", designMaterial=" + designMaterial + ", weight=" + weight + ", releasedDate=" + releasedDate
				+ ", otherTech=" + otherTech + ", description=" + description + ", backCameraRes=" + backCameraRes
				+ ", backCameraVideo=" + backCameraVideo + ", backCameraFunc=" + backCameraFunc + ", frontCameraRes="
				+ frontCameraRes + ", frontCameraVideo=" + frontCameraVideo + ", frontCameraFunc=" + frontCameraFunc
				+ ", mobileInternet=" + mobileInternet + ", otherport=" + otherPort + ", chargePort=" + chargePort
				+ ", jackPort=" + jackPort + "]";
	}

	

}
