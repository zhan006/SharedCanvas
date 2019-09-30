import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Manager {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictHub window = new PictHub();
					window.login("SharedCanvasManager");
					Registry registry = LocateRegistry.getRegistry();
		            registry.bind("SharedCanvasManager", window);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
