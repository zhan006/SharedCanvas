import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class User {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String username = "SharedCanvasUser";
					PictHub window = new PictHub(username);				
					Registry registry = LocateRegistry.getRegistry();
					// check if the username is already in registry
					String[] registerNames = registry.list();
					for(String user:registerNames){
						if(user.equals(username)){
							System.out.println("\"" + username +
									"\" has already exists, try other usernames.");
							System.exit(0);
						}
					}
		            registry.bind("SharedCanvasUser", window);

		            RemoteSharedCanvas manager = (RemoteSharedCanvas)registry.lookup("SharedCanvasManager");
		            manager.login("SharedCanvasUser");
		            ArrayList<String> temp = manager.getUserList();
		            window.setUserList(temp);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
