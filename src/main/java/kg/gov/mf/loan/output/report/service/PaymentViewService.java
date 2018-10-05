package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.PaymentView;

import java.util.LinkedHashMap;
import java.util.List;

public interface PaymentViewService {


	public void create(PaymentView paymentView);

	public void edit(PaymentView paymentView);

	public void deleteById(long id);

	public PaymentView findById(long id);

	public List<PaymentView> findAll();

	public List<PaymentView> findByParameter(LinkedHashMap<String, List<String>> parameters);

}
