import java.awt.Color;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//interfaces for remote users to access the canvas board
public interface RemoteSharedCanvas extends Remote{
	
	public void login(String myname) throws RemoteException;
	//	add self into the users list
	public ArrayList<String> getUserList() throws RemoteException;
	
	public void setUserList(ArrayList<String> temp) throws RemoteException;
	
	public void drawLine(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//  draw a straight line
	public void drawOval(int x1, int y1,int x2,int y2,Tool tool) throws RemoteException;
	//  draw an Oval
	public void drawString(String text,int x1,int y1,Tool tool) throws RemoteException;
	// draw String
	public void drawRect(int x1,int y1,int x2,int y2,Tool tool) throws RemoteException;
	// draw Rectangle
	
	
}
