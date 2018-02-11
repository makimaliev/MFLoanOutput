package kg.gov.mf.loan.output.report.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="filter_parameter")

public class FilterParameter {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
	@ManyToOne(targetEntity=ObjectList.class, fetch = FetchType.EAGER)
	@JoinColumn(name="object_list_id")
	ObjectList objectList;

	@ManyToOne(targetEntity=ContentParameter.class, fetch = FetchType.EAGER)
	@JoinColumn(name="content_parameter_id")
	ContentParameter contentParameter;

	@Enumerated(EnumType.STRING)
	private Comparator comparator;

    
    @Column(name="compared_value", nullable=false)
    private String comparedValue;
	
	@Enumerated(EnumType.STRING)
	private FilterParameterType filterParameterType;
    

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectList getObjectList() {
		return objectList;
	}

	public void setObjectList(ObjectList objectList) {
		this.objectList = objectList;
	}

	public ContentParameter getContentParameter() {
		return contentParameter;
	}

	public void setContentParameter(ContentParameter contentParameter) {
		this.contentParameter = contentParameter;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public String getComparedValue() {
		return comparedValue;
	}

	public void setComparedValue(String comparedValue) {
		this.comparedValue = comparedValue;
	}

	public FilterParameterType getFilterParameterType() {
		return filterParameterType;
	}

	public void setFilterParameterType(FilterParameterType filterParameterType) {
		this.filterParameterType = filterParameterType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final FilterParameter other = (FilterParameter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
