package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.PaymentViewDao;
import kg.gov.mf.loan.output.report.model.PaymentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PaymentViewServiceJpaImpl implements PaymentViewService {
	
	@Autowired
    private PaymentViewDao paymentViewDao;
 
    public void setPaymentViewDao(PaymentViewDao paymentViewDao) {
        this.paymentViewDao = paymentViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(PaymentView paymentView) {
		this.paymentViewDao.create(paymentView);
		
	}

	@Override
	@Transactional	
	public void edit(PaymentView paymentView) {
		this.paymentViewDao.edit(paymentView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.paymentViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public PaymentView findById(long id) {
		return this.paymentViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<PaymentView> findAll() {
        return this.paymentViewDao.findAll();
    }


	@Override
	@Transactional
	public List<PaymentView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {
		return this.paymentViewDao.findByParameter(parameters);
	}


}
