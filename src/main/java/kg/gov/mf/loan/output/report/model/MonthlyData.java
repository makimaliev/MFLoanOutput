package kg.gov.mf.loan.output.report.model;

public class MonthlyData implements Comparable<MonthlyData>{


	private String className;
	private Long object_id;
	private Long year;
	private Long month;
	private Double amount;
	private String name;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getObject_id() {
		return object_id;
	}

	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(MonthlyData model)
	{
		return model.getYear().compareTo(this.getYear());
	}
}