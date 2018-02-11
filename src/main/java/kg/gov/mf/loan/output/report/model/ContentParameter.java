package kg.gov.mf.loan.output.report.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="content_parameter")

public class ContentParameter {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    

    @Column(name="object_type_id")
    private long objectTypeId;

    @Column(name="field_name", nullable=false)
    private String fieldName;
    
    @Column(name="classificator_id")
    private long classificatorId;

    @Column(name="classificator_value_id")
    private long classificatorValueId;

    @Column(name="constant_value")
    private double constantValue;

    @Column(name="constant_text", nullable=false)
    private String constantText;
    
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    @Temporal(TemporalType.DATE)
    private Date constant_date;

    
    

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

	public long getObjectTypeId() {
		return objectTypeId;
	}

	public void setObjectTypeId(long objectTypeId) {
		this.objectTypeId = objectTypeId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public long getClassificatorId() {
		return classificatorId;
	}

	public void setClassificatorId(long classificatorId) {
		this.classificatorId = classificatorId;
	}

	public long getClassificatorValueId() {
		return classificatorValueId;
	}

	public void setClassificatorValueId(long classificatorValueId) {
		this.classificatorValueId = classificatorValueId;
	}

	public double getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(double constantValue) {
		this.constantValue = constantValue;
	}

	public String getConstantText() {
		return constantText;
	}

	public void setConstantText(String constantText) {
		this.constantText = constantText;
	}

	public Date getConstant_date() {
		return constant_date;
	}

	public void setConstant_date(Date constant_date) {
		this.constant_date = constant_date;
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
		final ContentParameter other = (ContentParameter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
