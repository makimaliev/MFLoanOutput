package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.PaymentScheduleViewDao;
import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PaymentScheduleViewServiceJpaImpl implements PaymentScheduleViewService {
	
	@Autowired
    private PaymentScheduleViewDao paymentScheduleViewDao;
 
    public void setPaymentScheduleViewDao(PaymentScheduleViewDao paymentScheduleViewDao) {
        this.paymentScheduleViewDao = paymentScheduleViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(PaymentScheduleView paymentScheduleView) {
		this.paymentScheduleViewDao.create(paymentScheduleView);
		
	}

	@Override
	@Transactional	
	public void edit(PaymentScheduleView paymentScheduleView) {
		this.paymentScheduleViewDao.edit(paymentScheduleView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.paymentScheduleViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public PaymentScheduleView findById(long id) {
		return this.paymentScheduleViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<PaymentScheduleView> findAll() {
        return this.paymentScheduleViewDao.findAll();
    }


	@Override
	@Transactional
	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.paymentScheduleViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {
		return this.paymentScheduleViewDao.findByParameter(parameters,offset,limit);
	}


}
