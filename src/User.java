import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class User {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String username = "user1";
					PictHub window = new PictHub(username);				
					Registry registry = LocateRegistry.getRegistry();
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
		            window.repaintPicture(local);
		            System.out.println(local.size());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
