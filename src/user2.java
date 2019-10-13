import java.awt.EventQueue;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class user2 {
	public static void main(String[] args) {	
		try {
			Registry registry = LocateRegistry.getRegistry();
			String username = "user2";
			
			registry.lookup(username);
			
			System.out.print("the name you entered is already bounded");
			
		}
		// invoked this if it is first time execute and bound
		catch(NotBoundException e) {
			
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry();
				String username = "user2";
				
				RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
				
				boolean flag = manager.getApproval(username);
				/*
				 * where I place the check approval, only execute pictub if receives approval
				 */
				if (flag) {
					PictHub window = new PictHub(username);				
					
					
					
		            registry.bind(username, window);  
		            manager.login(username);
		            ArrayList<String> temp = manager.getUserList();
		            window.setUserList(temp);
		            
		            // add myself to those non-manager-notme user
		            for (String user:temp) {
		            	if (!user.equals("SharedCanvasManager") && !user.equals(username)) {
		            		RemoteSharedCanvas buffer = (RemoteSharedCanvas)registry.lookup(user);
		            		buffer.syncUserlist(username);
		            		buffer.addUser(username);
		            		System.out.println("after adding my userlist size is: "+buffer.getUserList().size());
		            	}
		            }
		            window.initializeUserList(temp);
		            
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
			}
		}
//		catch (AlreadyBoundException abe) {
//	        System.out.println("The address is already bounded");
//	    }
		catch (ConnectException e) {
			System.out.println("Seems like you failed to connect to the RMI register or your manager.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
