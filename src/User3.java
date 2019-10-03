import java.awt.EventQueue;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class User3 {
	public static void main(String[] args) {	
		try {
			Registry registry = LocateRegistry.getRegistry();
			String username = "user3";
			
			System.out.print(registry.lookup(username));
			
			System.out.print("the name you entered is already bounded");
			
		}
		// invoked this if it is first time execute and bound
		catch(NotBoundException e) {
			
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry();
				String username = "user3";
				
				PictHub window = new PictHub(username);				
				
				
				
	            registry.bind(username, window);  
	            RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
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
	        
	            
	            ArrayList<Graph> local = new ArrayList<Graph>();;
	            for(Graph g:drawing) {
	            	local.add(g);
	            }
	            ArrayList<Graph> myshapes = window.getShapes();
	            System.out.println("windows previous size is: "+window.getShapes().size());
	            for(Graph g:local) {
	            	myshapes.add(g);
	            }
	            System.out.println("windows shapes size is: "+window.getShapes().size());
	            window.repaintPicture(window.getShapes());
	            
	            window.repaintPicture(window.getShapes());
	            System.out.println(local.size());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("something wrong with the remote object");
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("the name is already registered");
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("the name is not bounded yet!");
			}
		}
//		catch (AlreadyBoundException abe) {
//	        System.out.println("The address is already bounded");
//	    }
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
