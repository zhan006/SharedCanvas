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
//		int pt = Integer.parseInt(remoteport);
		try {
			int pt = Integer.parseInt(remoteport);
			Registry registry = LocateRegistry.getRegistry(remoteip,pt);
			registry.lookup(username);
			System.out.print("the name you entered is already bounded");
			
		}
		// invoked this if it is first time execute and bound
		catch(NotBoundException e) {
			
			Registry registry;
			try {
				int pt = Integer.parseInt(remoteport);
				registry = LocateRegistry.getRegistry(remoteip,pt);
				RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
				
				boolean flag = manager.getApproval(username);
				
				if(manager.getUserList().containsKey(username)) {
					System.out.println("the user is alrady exist");
					System.exit(0);
				}
				
				/*
				 * where I place the check approval, only execute pictub if receives approval
				 */
				if (flag) {
					PictHub window = new PictHub(username);
					LocateRegistry.createRegistry(1099);
					Registry localregistry = LocateRegistry.getRegistry();
					String host = InetAddress.getLocalHost().getHostAddress();
		            localregistry.bind(username, window);  
		            manager.login(username,host,"1099");
		            HashMap<String,ArrayList<String>> temp = manager.getUserList();
		            window.setUserList(temp);
		            
		            // add myself to those non-manager-notme user
		            for (String user:temp.keySet()) {
		            	if (!user.equals("SharedCanvasManager") && !user.equals(username)) {
		            		RemoteSharedCanvas buffer = (RemoteSharedCanvas)registry.lookup(user);
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
				System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
			}
			catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("something wrong with the remote object");
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("the name is already registered");
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("No manager found");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				System.out.println("NPlease chec the ip address or port number again");
			}
		}
		catch (ConnectException e) {
			System.out.println("Seems like you failed to connect to the RMI register or your manager.");
		}
//		catch (UnknownHostException e2) {
//			// TODO Auto-generated catch block
//			System.out.println("Please check the ip address or port number again");
//		}
		catch (NumberFormatException e5) {
			System.out.println("Please enter integer for port number");
		}
		catch (Exception e) {
			System.out.println();
			System.out.println("Seems like you failed to connect to the RMI register or your manager.");
			System.out.println("Using <username> <hostIP> <host port number>");
		}
		
	}
}
