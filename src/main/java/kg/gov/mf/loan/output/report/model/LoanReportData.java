package kg.gov.mf.loan.output.report.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.sql.Date;
import java.util.Set;

import kg.gov.mf.loan.manage.model.loan.Loan;


public class LoanReportData
{
	private LoanReportData Parent                = null;
	private LinkedList<LoanReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private Set<Loan> loans = new HashSet<Loan>(0);


	public LoanReportData()
	{
		ChildDataList = new LinkedList<LoanReportData>();
	}


	public LoanReportData addChild()
	{
		LoanReportData ChildData = new LoanReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public LoanReportData[] getChilds()
	{
		return  (LoanReportData [])ChildDataList.toArray(new LoanReportData[ChildDataList.size()]);
	}


	public LinkedList<LoanReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<LoanReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<LoanReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<LoanReportData> data)
	{
		ChildDataList = data;
	}


	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public LoanReportData getParent()
	{
		return Parent;
	}
	public void setParent(LoanReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public Set<Loan> getLoans() {
		return loans;
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}
}