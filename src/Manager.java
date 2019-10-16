import java.awt.EventQueue;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Manager {
	public static void main(String[] args) {
		int port = 1099;

		if(args.length != 0) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch(Exception e) {
				System.out.println("invalid port number, using default port of 1099 instead");
			}
		}
		try {
			LocateRegistry.createRegistry(port);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			
			String username = "SharedCanvasManager";
			Registry registry = LocateRegistry.getRegistry(port);
			
			registry.lookup(username);
			
			System.out.print("Manager is already registered");
					
		} 
		catch (NotBoundException e) {
			String username = "SharedCanvasManager";
			Registry registry;
			try {
				
				registry = LocateRegistry.getRegistry(port);
				PictHub window = new PictHub(username);
				String host = "localhost";
				try {
					host = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.print("The host ip address is: "+host);
				String pt = String.valueOf(port);
				window.login(username, host, pt);
	            registry.bind("SharedCanvasManager", window);
			}
			catch (ConnectException e5) {
				System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
			}
			catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("something wrong with the remote object");
			} catch (AlreadyBoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("Manager is already registered");
			}
		}
		catch (ConnectException e) {
			System.out.println("Seems like you failed to connect to the RMI register. Did you start it?");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
