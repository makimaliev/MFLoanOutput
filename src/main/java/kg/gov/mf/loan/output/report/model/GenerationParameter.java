package kg.gov.mf.loan.output.report.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="generation_parameter")

public class GenerationParameter {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name="ref_id")
    private long refId;

	@Column(name="position")
	private long postion;

	@Column(name="position_in_list")
	private long postionInList;


	@ManyToOne(targetEntity=GenerationParameterType.class, fetch = FetchType.EAGER)
	@JoinColumn(name="generation_parameter_type__id")
	GenerationParameterType generationParameterType;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public long getPostion() {
		return postion;
	}

	public void setPostion(long postion) {
		this.postion = postion;
	}

	public long getPostionInList() {
		return postionInList;
	}

	public void setPostionInList(long postionInList) {
		this.postionInList = postionInList;
	}

	public GenerationParameterType getGenerationParameterType() {
		return generationParameterType;
	}

	public void setGenerationParameterType(GenerationParameterType generationParameterType) {
		this.generationParameterType = generationParameterType;
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
		final GenerationParameter other = (GenerationParameter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
