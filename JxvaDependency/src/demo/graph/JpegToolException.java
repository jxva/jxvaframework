package demo.graph;

public class JpegToolException extends Exception {
	private static final long serialVersionUID = 318822337566910102L;
	private String errMsg = "";

	public JpegToolException(String errMsg) {

		this.errMsg = errMsg;

	}

	public String getMsg() {

		return "JpegToolException:" + this.errMsg;

	}

}