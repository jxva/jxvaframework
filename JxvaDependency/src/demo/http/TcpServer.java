package demo.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TcpServer implements Runnable, Cloneable {  
    Thread runner = null;  
  
    ServerSocket server = null;  
  
    Socket data = null;  
  
    volatile boolean shouldStop = false;  
  
    public synchronized void startServer(int port) throws IOException {  
        if (runner == null) {  
            server = new ServerSocket(port);  
            runner = new Thread(this);  
            runner.start();  
        }  
    }  
  
    public synchronized void stopServer() {  
        if (server != null) {  
            shouldStop = true;  
            runner.interrupt();  
            runner = null;  
            try {  
                server.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
  
    public void run() {  
        if (server != null) {  
            while (!shouldStop) {  
                try {  
                    Socket dataSocket = server.accept();  
                    TcpServer newSocket = (TcpServer) clone();  
  
                    newSocket.server = null;  
                    newSocket.data = dataSocket;  
                    newSocket.runner = new Thread(newSocket);  
                    newSocket.runner.start();  
                } catch (Exception ex) {  
                    ex.printStackTrace();  
                }  
            }  
        } else {  
            run(data);  
        }  
    }  
  
    abstract void run(Socket socket);  
  
    public static void main(String[] args) throws IOException {  
       // new TcpServer().startServer(8080);  
    }  
  
}  