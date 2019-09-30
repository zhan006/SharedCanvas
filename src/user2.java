import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class user2 {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					
					Registry registry = LocateRegistry.getRegistry();
		            registry.bind("SharedCanvasUser2", window);
		            
		            RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
		            manager.login("SharedCanvasUser2");
		            ArrayList<String> temp = manager.getUserList();
		            System.out.println(temp.size());
		            
		            window.setUserList(temp);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
