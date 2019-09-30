import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class User {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					
					Registry registry = LocateRegistry.getRegistry();
		            registry.bind("SharedCanvasUser", window);
		            
		            RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
		            manager.login("SharedCanvasUser");
		            ArrayList<String> temp = manager.getUserList();
//		            System.out.println(temp.size());
		            
		            window.setUserList(temp);
//		            window.setShapes(manager.getShapes());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
