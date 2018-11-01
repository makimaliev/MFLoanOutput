package kg.gov.mf.loan.output.report.service;



import kg.gov.mf.loan.output.report.model.PaymentScheduleView;

import java.util.LinkedHashMap;
import java.util.List;

public interface PaymentScheduleViewService {


	public void create(PaymentScheduleView paymentScheduleView);

	public void edit(PaymentScheduleView paymentScheduleView);

	public void deleteById(long id);

	public PaymentScheduleView findById(long id);

	public List<PaymentScheduleView> findAll();

	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit);

}
