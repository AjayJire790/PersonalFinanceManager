package personalFinanceManager;

/*
 * This class is an entry point of execution for PersonalExpenseManager Application (PEMapp).
 * @author Ajay Jire
 */
public class StartApp {
	/*
	 * This method is creating <code>PEMService</code> object 
	 * and show app menu by calling showMenu() method.
	 */
	public static void main(String[] args) {
		PEMService service=new PEMService();
		service.showMenu();
	}

} 
