import java.awt.EventQueue;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Manager {
	public static void main(String[] args) {
		try {
			String username = "SharedCanvasManager";
			Registry registry = LocateRegistry.getRegistry();
			
			registry.lookup(username);
			
			System.out.print("Manager is already registered");
					
		} 
		catch (NotBoundException e) {
			String username = "SharedCanvasManager";
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry();
				PictHub window = new PictHub(username);
//				PictHub window = new PictHub();
				window.login("SharedCanvasManager");
	            registry.bind("SharedCanvasManager", window);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("something wrong with the remote object");
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("Manager is already registered");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
