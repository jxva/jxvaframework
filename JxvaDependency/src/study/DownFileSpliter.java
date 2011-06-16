package study;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownFileSpliter extends Thread {
	String dlURL;
	long startPos;
	long endPos;
	int threadID;
	boolean done = false;
	public boolean stop = false;
	RandomAccessFile file;

	public DownFileSpliter(String dlURL, String saveAs, long nStart, long nEnd,
			int id) throws IOException {
		this.dlURL = dlURL;
		this.startPos = nStart;
		this.endPos = nEnd;
		this.threadID = id;
		file = new RandomAccessFile(saveAs, "rw");
		file.seek(startPos);
	}

	public void run() {
		try {
			long start = System.currentTimeMillis();
			URL url = new URL(dlURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			String sProperty = "bytes=" + startPos + "-";
			httpConnection.setRequestProperty("RANGE", sProperty);
			httpConnection.setRequestProperty("Accept",
					"image/gif,image/x-xbitmap,application/msword,*/*");
			System.out.println("�߳�" + threadID + "�����ļ���������ȴ�");
			InputStream input = httpConnection.getInputStream();
			byte[] buf = new byte[1024];
			int offset;
			offset = (int) endPos - (int) startPos;
			if (offset > 1024)
				offset = 1024;
			BufferedInputStream bis = new BufferedInputStream(httpConnection
					.getInputStream());
			while (bis.read(buf, 0, offset) > 0 && startPos < endPos)// ѭ����ȡ�ļ�
			{
				offset = (int) endPos - (int) startPos;
				if (offset > 1024)
					offset = 1024;
				// System.out.println("threadID: " + threadID + " started: "+
				// startPos + " offset: " + offset);
				// Thread.sleep(2000);
				file.write(buf, 0, offset);
				if (stop) {
					System.out.println("�߳�:" + threadID + ",��ǿ����ֹ");
					break;
				}
				startPos += offset;
			}
			System.out.println("�߳�" + threadID + "ִ����ϣ���");
			System.out.println("�߳�" + threadID + ",����ʱ��"
					+ String.valueOf(System.currentTimeMillis() - start));
			file.close();
			input.close();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String args[]) {
		for (int i = 1; i < 5; i++) {
			try {
				DownFileSpliter df = new DownFileSpliter("E:\\8.jpg",
						"E:\\Struts\\", 1024, 0, i);
				df.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
