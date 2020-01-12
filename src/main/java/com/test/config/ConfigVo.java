package com.test.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConfigVo {

	
	private String webName;											//분류 명칭
	private List<String> rejectList = new ArrayList<>();			//불용 키워드 리스트
	private String seed;											//String 바꿀지 어떻게 담을지 고민해보자.
	private String arrow;											//허용 키워드
	private StatusType status;										//상태표시
	private ThreadStatus threadStatus;								//스레드 상태
	private int resultSize;											//수집 db 사이즈
	private int threadEndCnt;										//스레드 종료 체크
	
	private String xTitle;											//xpath .title
	private String xTitle2;											//xpath .title2
	private String xContent;										//xpath .content
	private String xError1;											//xpath .error1
	private String xError2;											//xpath .error2
	
	private List<String> overlapUrlList = new ArrayList<>();		//중복url리스트
	private List<String> deadUrlList = new ArrayList<>();			//데드url 리스트
	private BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();			//작업Queue
	private BlockingQueue<List<String>> readyUrlQueue = new LinkedBlockingQueue<>();//결과Queue
	
	public ConfigVo(){
		threadStatus = ThreadStatus.WAIT;
		resultSize = 0;
		threadEndCnt = 0;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public List<String> getRejectList() {
		return rejectList;
	}

	public void setRejectList(List<String> rejectList) {
		this.rejectList = rejectList;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String getArrow() {
		return arrow;
	}

	public void setArrow(String arrow) {
		this.arrow = arrow;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public ThreadStatus getThreadStatus() {
		return threadStatus;
	}

	public void setThreadStatus(ThreadStatus threadStatus) {
		this.threadStatus = threadStatus;
	}

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public int getThreadEndCnt() {
		return threadEndCnt;
	}

	public void setThreadEndCnt(int threadEndCnt) {
		this.threadEndCnt = threadEndCnt;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getxTitle2() {
		return xTitle2;
	}

	public void setxTitle2(String xTitle2) {
		this.xTitle2 = xTitle2;
	}

	public String getxContent() {
		return xContent;
	}

	public void setxContent(String xContent) {
		this.xContent = xContent;
	}

	public String getxError1() {
		return xError1;
	}

	public void setxError1(String xError1) {
		this.xError1 = xError1;
	}

	public String getxError2() {
		return xError2;
	}

	public void setxError2(String xError2) {
		this.xError2 = xError2;
	}

	public List<String> getOverlapUrlList() {
		return overlapUrlList;
	}

	public void setOverlapUrlList(List<String> overlapUrlList) {
		this.overlapUrlList = overlapUrlList;
	}

	public List<String> getDeadUrlList() {
		return deadUrlList;
	}

	public void setDeadUrlList(List<String> deadUrlList) {
		this.deadUrlList = deadUrlList;
	}

	public BlockingQueue<String> getUrlQueue() {
		return urlQueue;
	}

	public void setUrlQueue(BlockingQueue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}

	public BlockingQueue<List<String>> getReadyUrlQueue() {
		return readyUrlQueue;
	}

	public void setReadyUrlQueue(BlockingQueue<List<String>> readyUrlQueue) {
		this.readyUrlQueue = readyUrlQueue;
	}

	@Override
	public String toString() {
		return "ConfigVo [webName=" + webName + ", rejectList=" + rejectList + ", seed=" + seed + ", arrow=" + arrow
				+ ", status=" + status + ", threadStatus=" + threadStatus + ", resultSize=" + resultSize
				+ ", threadEndCnt=" + threadEndCnt + ", xTitle=" + xTitle + ", xTitle2=" + xTitle2 + ", xContent="
				+ xContent + ", xError1=" + xError1 + ", xError2=" + xError2 + ", overlapUrlList=" + overlapUrlList
				+ ", deadUrlList=" + deadUrlList + ", urlQueue=" + urlQueue + ", readyUrlQueue=" + readyUrlQueue + "]";
	}

	
	
	
}
