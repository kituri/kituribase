//package com.kituri.ams;
//
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import com.google.protobuf.GeneratedMessage;
//import com.kituri.app.utils.MessageUtils;
//
//public class SocketClient {
//
//	private Socket client;
//	DataOutputStream dout;  
//   // DataInputStream din; 
//	
//	public final static String SOCKET_SERVER = "vlineserver.utan.com";
//
//	public final static int SOCKET_PORT = 3601;
//	
//	public SocketClient() {
//		try {
//			client = new Socket(SOCKET_SERVER, SOCKET_PORT);
//			dout = new DataOutputStream(client.getOutputStream());  
//            //din = new DataInputStream(client.getInputStream());  
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void pause(){
//		
//	}
//	
//	public void onResume(){
//		
//	}
//	
//	public boolean isConnected(){
//		return client.isConnected();
//	}
//	
//	//收数据可能会2个包一起粘一起过来
//	public GeneratedMessage getMsg() {
//		GeneratedMessage  pkg = null;
//		try {
////			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
////			return in.readLine();
//			pkg = MessageUtils.recvSocketMsg(client.getInputStream());
//			//pkg = SocketMsgInfo.GROUP_UPDATE_REQ_PKG.parseFrom(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return pkg;
//	}
//
////	public void sendMsg(String msg) {
////		try {
////			PrintWriter out = new PrintWriter(client.getOutputStream());
////			out.println(msg);
////			out.flush();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
//	
//	/**
//	 * 发送数据
//	 * 
//	 * @param bytes
//	 * @throws IOException
//	 */
//	public void sendByte(byte[] bytes) throws IOException
//	{
//		//ByteBuffer writeBuffer = ByteBuffer.wrap(bytes);
//		
//		if (client == null)
//		{
//			throw new IOException();
//		}
//		dout.write(bytes);
//	}
//	
//	public void closeSocket() {
//		try {
//			client.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//}
