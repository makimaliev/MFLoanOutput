package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.DocumentViewDao;
import kg.gov.mf.loan.output.report.model.DocumentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DocumentViewServiceJpaImpl implements DocumentViewService {
	
	@Autowired
    private DocumentViewDao documentViewDao;
 
    public void setDocumentViewDao(DocumentViewDao documentViewDao) {
        this.documentViewDao = documentViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(DocumentView documentView) {
		this.documentViewDao.create(documentView);
		
	}

	@Override
	@Transactional	
	public void edit(DocumentView documentView) {
		this.documentViewDao.edit(documentView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.documentViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public DocumentView findById(long id) {
		return this.documentViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<DocumentView> findAll() {
        return this.documentViewDao.findAll();
    }


	@Override
	@Transactional
	public List<DocumentView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.documentViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<DocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {
		return this.documentViewDao.findByParameter(parameters,offset,limit,sortStr,sortField);
	}

	@Override
	@Transactional
	public Long getCount(LinkedHashMap<String, List<String>> parameters) {
		return this.documentViewDao.getCount(parameters);
	}


}
