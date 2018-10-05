package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface PaymentScheduleViewDao {

	public void create(PaymentScheduleView paymentScheduleView);

	public void edit(PaymentScheduleView paymentScheduleView);

	public void deleteById(long id);

	public PaymentScheduleView findById(long id);

	public List<PaymentScheduleView> findAll();

	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
