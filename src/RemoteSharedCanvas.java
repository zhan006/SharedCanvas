import java.awt.Color;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

//interfaces for remote users to access the canvas board
public interface RemoteSharedCanvas extends Remote{
	
	public void login(String myname) throws RemoteException;
	//	add self into the users list
	public ArrayList<String> getUserList() throws RemoteException;
	
	public void setUserList(ArrayList<String> temp) throws RemoteException;
	
	public ArrayList<Graph> getShapes()throws RemoteException;
	
	public void AddShapes(Graph shape) throws RemoteException;
	
	public void setShapes(ArrayList<Graph> shapes)throws RemoteException; 
	
	public void drawLine(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//  draw a straight line
	public void drawOval(int x1, int y1,int x2,int y2,Tool tool) throws RemoteException;
	//  draw an Oval
	public void drawCircle(int x1, int y1,int x2,int y2,Tool tool) throws RemoteException;
	//  draw a circle
	public void drawString(String text,int x1,int y1,Tool tool) throws RemoteException;
	// draw String
	public void drawRect(int x1,int y1,int x2,int y2,Tool tool) throws RemoteException;
	// draw Rectangle
	
	public void drawEraser(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//draw eraser
	
	public void drawSmallEraser(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//draw small eraser
	
	public void drawMediumEraser(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//draw medium eraser
	
	public void drawLargeEraser(int x1,int y1, int x2, int y2,Tool tool) throws RemoteException;
	//draw large eraser
	public void sendText() throws RemoteException;
	public void setChattingArea(String text) throws RemoteException;
	
	public void syncUserlist(String managerList)throws RemoteException;
	public void initializeUserList(ArrayList<String> managerList)throws RemoteException;
	public void addUser(String laterUser) throws RemoteException;
	public void removeFromDisplay(String username) throws RemoteException;
	
	public void kickUser() throws RemoteException;
	public void deleteUser(String username) throws RemoteException;
	
	public void leave() throws RemoteException;
	
	public void newPicture() throws RemoteException;
	public void repaintPicture(ArrayList<Graph> shapes) throws RemoteException;
}
