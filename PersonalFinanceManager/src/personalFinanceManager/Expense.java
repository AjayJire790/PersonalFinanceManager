package personalFinanceManager;

import java.io.Serializable;
import java.util.Date;
/*
 * This is a domain class represents Expense
 * @author Ajay
 */
public class Expense implements Serializable{
	private Long expenseId = System.currentTimeMillis();
	private Long categoryId;
	private Float amount;
	private Date date;
	private String remark;
	
	public Expense() {
		
	}

	public Expense(Long categoryId, Float amount, Date date, String remark) {
		super();
		this.categoryId = categoryId;
		this.amount = amount;
		this.date = date;
		this.remark = remark;
	}

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
