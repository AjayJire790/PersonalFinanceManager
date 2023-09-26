package personalFinanceManager;

import java.util.ArrayList;
import java.util.List;

public class Repository {
	/*
	 * The list holds all expenses added by user
	 */
	public List<Expense> expList=new ArrayList();
	/*
	 * The list holds all expenses-categories added by user
	 */
	public List<Category> catList=new ArrayList(); 
	/*
	 * A singleton reference of repository
	 */
	private static Repository repository;
	/*
	 * Private constructor to restrict object creation from outside
	 */
	private Repository() {
	}
	public static Repository getRepository() {
		if(repository==null) {
			repository=new Repository();
		}
		return repository;
	}

}
