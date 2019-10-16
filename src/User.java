import java.awt.EventQueue;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {
	public static void main(String[] args) {
		String username = "user1";
		String remoteip = "localhost";
		String remoteport = "1099";
		if(args.length == 3) {
			username = args[0];
			remoteip = args[1];
			remoteport = args[2];
		}
		System.out.println("remoteip: "+remoteip+" remoteport: "+remoteport);
		int pt = Integer.parseInt(remoteport);
		try {
			Registry registry = LocateRegistry.getRegistry(remoteip,pt);
			registry.lookup(username);
			System.out.print("the name you entered is already bounded");
			
		}
		// invoked this if it is first time execute and bound
		catch(NotBoundException e) {
			
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(remoteip,pt);
				RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
				
				boolean flag = manager.getApproval(username);
				
				/*
				 * where I place the check approval, only execute pictub if receives approval
				 */
				if (flag) {
					PictHub window = new PictHub(username);
					try {
						LocateRegistry.createRegistry(1099);}
					catch(RemoteException a){
						System.out.println("resgistry already created!");
					}
					Registry localregistry = LocateRegistry.getRegistry();
					String host = InetAddress.getLocalHost().getHostAddress();
					localregistry.bind(username, window); 
					
		            manager.login(username,host,"1099");
		            HashMap<String,ArrayList<String>> temp = manager.getUserList();
		            System.out.println(temp);
		            window.setUserList(temp);
		            
		            // add myself to those non-manager-notme user
		            for (String user:temp.keySet()) {
		            	if (!user.equals("SharedCanvasManager") && !user.equals(username)) {
		            		Registry remote;
		            		String ip = temp.get(user).get(0);
		            		String port = temp.get(user).get(1);
		            		remote = LocateRegistry.getRegistry(ip, Integer.parseInt(port));
		            		RemoteSharedCanvas buffer = (RemoteSharedCanvas)remote.lookup(user);
		            		buffer.syncUserlist(username);
		            		buffer.addUser(username,host,"1099");
		            		System.out.println("after adding my userlist size is: "+buffer.getUserList().size());
		            	}
		            }
		            ArrayList<String> userlist = new ArrayList<String>();
		            userlist.addAll(temp.keySet());
		            window.initializeUserList(userlist);
		            
		            ArrayList<Graph> drawing = manager.getShapes();
		            ArrayList<Graph> myshapes = window.getShapes();
		            
		            
		            for(Graph g:drawing) {
		            	myshapes.add(g);
		            }
		            
		            window.repaintPicture(window.getShapes());
		            System.out.println("the window shape size is: "+window.getShapes().size());
		            
		            window.sendHello(username);
				}
				else {
					System.out.println("my join request is refused ;(");
				}   
			}
			catch (ConnectException e5) {
				System.out.println("Seems like you failed to connect. Using <username> <hostIP> <host port number>");
				System.out.println("Please check your input.");
			}
			catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("something wrong with the remote object");
//				e1.printStackTrace();
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("the name is already registered");
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
//				e1.printStackTrace();
				System.out.println("No manager found");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				System.out.println("host is unknown. Using <username> <hostIP> <host port number>");
//				e1.printStackTrace();
			}
		}
//		catch (AlreadyBoundException abe) {
//	        System.out.println("The address is already bounded");
//	    }
		catch (ConnectException e) {
			System.out.println("Seems like you failed to connect to the RMI register or your manager.");
			System.out.println("Check your input:  <username> <hostIP> <host port number>");
//			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println();
			System.out.println("Seems like you failed to connect to the RMI register or your manager.");
			System.out.println("Using <username> <hostIP> <host port number>");
		}
		
	}
}
