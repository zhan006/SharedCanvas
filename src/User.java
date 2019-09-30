import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class User {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					
					Registry registry = LocateRegistry.getRegistry();
		            registry.bind("SharedCanvasUser", window);
		            PictHub manager = (PictHub)registry.lookup("SharedCanvas");
		            manager.login(window);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
