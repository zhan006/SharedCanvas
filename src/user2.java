import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class user2 {
	public static void main(String[] args) {
		try {
			String username = "user2";
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
            ArrayList<Graph> myshapes = window.getShapes();
            System.out.println("windows previous size is: "+window.getShapes().size());
            for(Graph g:local) {
            	myshapes.add(g);
            }
            System.out.println("windows shapes size is: "+window.getShapes().size());
            window.repaintPicture(window.getShapes());
            
            window.repaintPicture(window.getShapes());
            System.out.println(local.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
