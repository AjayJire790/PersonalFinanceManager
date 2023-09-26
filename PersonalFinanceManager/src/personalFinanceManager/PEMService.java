package personalFinanceManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/*
 * The class contains most of the operations related to PEM applications.
 * <p>
 * This class prepares menu and various method are present to handle the user action.
 * The class make use of <code>Repository</code> to store the data;
 * Also using <code>Repository</code> to generate different required reports.
 * @author Ajay Jire
 */
public class PEMService {
	/*
	 * Declare a reference of repository by calling a static method which return a singleton repository object.
	 */
	Repository repo=Repository.getRepository();
	
	/*
	 * Declare a reference of ReportService to call different method to calculate reports
	 */
	ReportService reportService=new ReportService(); 
	/*
	 * Declare a scanner object to take standard input from keyboard.
	 */
	private Scanner in=new Scanner(System.in);
	/*
	 * This variable store the value of menu=choice.
	 */
	private int choice;
	/*
	 * Call this constructor to create PEMService object with default details.
	 */
	
	public PEMService(){
		//prepareSampleData();
		//TODO: Delete this method call after testing is completed
		restoreRepository();
	}
	/*
	 * THis method prepare a PEMApp menu using switch-case and infinite loop, also wait ask for user choice.
	 */
	public void showMenu() {
		while(true) {
			printMenu();
			switch(choice) {
			case 1:
				onAddCategory();
				pressAnyKeyToContinue();
				break;
			case 2:
				onCategoryList();
				pressAnyKeyToContinue();
				break;
			case 3:
				onExpenseEntry();
				pressAnyKeyToContinue();
				break;
			case 4:
				onExpenseList();
				pressAnyKeyToContinue();
				break;	
			case 5:
				onMonthlyExpenseList();
				pressAnyKeyToContinue();
				break;	
			case 6:
				onYearlyExpenseList();
				pressAnyKeyToContinue();
				break;	
			case 7:
				onCategorizedExpenseList();
				pressAnyKeyToContinue();
				break;	
			case 0:
				System.out.println("\nExited!");
				onExit();
				break;
			}
		}
	}
	/*
	 * This method prints a menu (CUI/CLI menu)
	 */
	public void printMenu() {
		System.out.println("----------PEM Menu--------------");
		System.out.println("1. Add Category");
		System.out.println("2. Category List");
		System.out.println("3. Expense Entry");
		System.out.println("4. Expense List");
		System.out.println("5. Monthly Expense List");
		System.out.println("6. Yearly Expense List");
		System.out.println("7. Categorized Expense List");
		System.out.println("0. Exit");
		System.out.println("--------------------------------");
		System.out.print("Enter Your Choice: ");
		choice=in.nextInt();
	}
	/*
	 * THis method is called to hold a output screen after processing the required task 
	 * and wait for any char input to calculate to the menu.
	 */
	public void pressAnyKeyToContinue() {
		try {
		System.out.println("\nPress Enter key to continue....");
		System.in.read();
		}catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	/*
	 * This method is taking expense category name as input to add new category in the system
	 */
	public void onAddCategory() {
		in.nextLine();//new line char is read here which is already present in stream and its not in use for now
		System.out.println("\nEnter Category Name : ");
		String catName=in.nextLine();
		Category cat=new Category(catName);
		repo.catList.add(cat);
		System.out.println("Category Added");
		
	}
	/*
	 * Call this method to print existing category print
	 */
	public void onCategoryList() {
		System.out.println("\nCategories List");
		System.out.println("-------------------------");
		List<Category> clist=repo.catList;
		for(int i=0;i<clist.size();i++) {
			Category c=clist.get(i);
			System.out.println((i+1)+". "+c.getName()+", "+c.getCategoryId());
			
		}
		System.out.println("-------------------------");
		
	}
	/*
	 * Call this method to enter expense details. The entered details will be added in repository
	 */
	
	public void onExpenseEntry() {
		System.out.println("Enter Details for Expense Entry...");
		onCategoryList();
		System.out.print("choose Category : ");
		int catChoice=in.nextInt();
		Category selectedCat = repo.catList.get(catChoice-1);
		
		System.out.print("Enter Amount : ");
		float amount=in.nextFloat();
		
		System.out.print("Enter Remark : ");
		in.nextLine();
		String remark=in.nextLine();
		
		System.out.print("Enter Date(DD/MM/YYYY): ");
		String dateAsString = in.nextLine();
		Date date=DateUtil.stringToDate(dateAsString);
		
		//System.out.print("Enter Date : ");	
		
		//Add Expense details in Expense object
		Expense exp=new Expense();
		exp.setCategoryId(selectedCat.getCategoryId());
		exp.setAmount(amount);
		exp.setRemark(remark);
		exp.setDate(date);
		
		//store expense object in repository
		repo.expList.add(exp);
		System.out.println("Success: Expense Added");
		
	}
	/*
	 * The method prints all entered expenses.
	 */
	private void onExpenseList() {
		System.out.println("\nExpense Listing...");
		System.out.println("------------------------------------\n");
		List<Expense> expList=repo.expList;
		for(int i=0;i<expList.size();i++) {
			Expense exp=expList.get(i);
			String catName=reportService.getCategoryNameById(exp.getCategoryId());
			String dateString=DateUtil.dateToString(exp.getDate());
			System.out.println((i+1)+".  "+catName+",  "+exp.getAmount()+",  "+exp.getRemark()+",  "+dateString);	
		}
		System.out.println("\n------------------------------------");
	}
	/*
	 * This method is called from menu to prepare monthly-wise-expense-total. 
	 * Its using <code>ReportService</code> to calculate report.
	 * The returned result is printed by this method.
	 * Means this method invoke a call to generate report then result is printed by this method.
	 */
	
	private void onMonthlyExpenseList() {
		System.out.println("\nMonthly Expense Total...");
		System.out.println("--------------------------------\n");
		Map<String, Float> resultMap = reportService.calculateMonthlyTotal();
		Set<String> keys = resultMap.keySet();
		for(String yearMonth : keys) {
			String[] arr = yearMonth.split(",");
			String year = arr[0];
			Integer monthNo = Integer.parseInt(arr[1]);
			String monthName = DateUtil.getMonthName(monthNo);			
			System.out.println(year+", "+monthName+" : "+resultMap.get(yearMonth));
		}
		System.out.println("\n---------------------------------");
	}
	/*
	 * This method is called from menu to prepare yearly-wise-expense-total. 
	 * Its using <code>ReportService</code> to calculate report.
	 * The returned result is printed by this method.
	 * Means this method invoke a call to generate report then result is printed by this method.
	 */
	
	private void onYearlyExpenseList() {
		System.out.println("\nYearly Expense Total...");
		System.out.println("-------------------------------\n");
		Map<Integer,Float> resultMap = reportService.calculateYearlyTotal();
		Set<Integer> years = resultMap.keySet();
		Float total = 0.0F;
		for(Integer year : years) {
			Float exp = resultMap.get(year);
			total=total+exp;
			System.out.println(year+" : "+resultMap.get(year));
		}
		System.out.println("\n-------------------------------");
		System.out.println("Total Expense(INR) : "+total);
	}
	/*
	 * This method is called from menu to prepare categorized-wise-expense-total. 
	 * Its using <code>ReportService</code> to calculate report.
	 * The returned result is printed by this method.
	 * Means this method invoke a call to generate report then result is printed by this method.
	 */
	
	private void onCategorizedExpenseList() {
		System.out.println("\nCategory wise expense Listing...");
		System.out.println("-------------------------------------\n");
		Map<String,Float> resultMap = reportService.calculateCategoriedTotal();
		Set<String> categories = resultMap.keySet();
		Float netTotal = 0.0F;
		for(String categoryName : categories) {
			Float catWiseTotal = resultMap.get(categoryName);
			netTotal = netTotal + catWiseTotal;
			System.out.println(categoryName+" : "+resultMap.get(categoryName));
		}
		System.out.println("\n------------------------------------");
		System.out.println("Net Total : "+ netTotal);
		
	}
	/*
	 * This method stop a JVM, before storing repository permanently, Its closing PEM application.
	 * Its like a shutdown hook
	 */
	private void onExit() {
		persistRepository();
		System.exit(0);
	}
	
	/*
	 * This method is preparing sample data for testing purpose. 
	 * It should be report once app is tested ok.
	 */
	
//	public void prepareSampleData() {
//		Category catParty = new Category("Party");
//		delay();
//		Category catShopping = new Category("Shopping");
//		delay();
//		Category catGift = new Category("Gift");
//		
//		repo.catList.add(catParty);
//		repo.catList.add(catShopping);
//		repo.catList.add(catGift);
//		
//		//January-2022
//		Expense e1 = new Expense(catParty.getCategoryId(), 1000.0F, DateUtil.stringToDate("11/01/2022"), "N/A");
//		delay();
//		
//		Expense e2 = new Expense(catParty.getCategoryId(), 2000.0F, DateUtil.stringToDate("12/02/2022"), "N/A");
//		delay();
//		
//		//February-2022
//		Expense e3 = new Expense(catShopping.getCategoryId(), 200.0F, DateUtil.stringToDate("02/02/2022"), "N/A");
//		delay();		
//		
//		Expense e4 = new Expense(catParty.getCategoryId(), 100.0F, DateUtil.stringToDate("03/02/2022"), "N/A");
//		delay();
//		
//		//May-2022
//		Expense e5 = new Expense(catGift.getCategoryId(), 500.0F, DateUtil.stringToDate("24/05/2022"), "N/A");
//		delay();
//		
//		//June-2022
//		Expense e6 = new Expense(catParty.getCategoryId(), 700.0F, DateUtil.stringToDate("02/06/2022"), "N/A");
//		delay();
//		
//		//July-2022
//		Expense e7 = new Expense(catShopping.getCategoryId(), 100.0F, DateUtil.stringToDate("20/07/2022"), "N/A");
//		
//		//August-2022	
//		Expense e8 = new Expense(catGift.getCategoryId(), 5000.0F, DateUtil.stringToDate("10/08/2022"), "N/A");
//		
//		//January-2023
//		Expense e9 = new Expense(catShopping.getCategoryId(), 8000.0F, DateUtil.stringToDate("01/05/2023"), "N/A");
//		
//		//February-2023
//		Expense e10 = new Expense(catGift.getCategoryId(), 7000.0F, DateUtil.stringToDate("10/02/2023"), "N/A");
//		 
//		repo.expList.add(e1);
//		repo.expList.add(e2);
//		repo.expList.add(e3);
//		repo.expList.add(e4);
//		repo.expList.add(e5);
//		repo.expList.add(e6);
//		repo.expList.add(e7);
//		repo.expList.add(e8);
//		repo.expList.add(e9);
//		repo.expList.add(e10);
//	}
//	/*
//	 * The method sleep a thread for 10ms.
//	 */
//	private void delay() {
//		try {
//			Thread.sleep(10);
//		}catch (InterruptedException ex) {
//			ex.printStackTrace();
//		}
//	}
	private void persistRepository() {
		serialize("expenses.ser", repo.expList);
		serialize("categories.ser", repo.catList);
	}
	public void serialize(String file, Object obj) {
		try {
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			
			oos.writeObject(obj);//store expense list in file
			
			oos.close();
			fos.close();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	public Object deser(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();//deser
			return obj;
		}catch (Exception ex) {
			//ex.printStackTrace();
			System.out.println("No existing data present.");
			return null;
		}
	}
	private void restoreRepository() {
		List<Expense> expList = (List<Expense>) deser("expenses.ser");
		List<Category> catList = (List<Category>) deser("Categories.ser");
		if(expList!=null) {
			//set existing expenses in category
			repo.expList=expList;
		}
		if(catList!=null) {
			repo.catList = catList;
		}
	}
}
