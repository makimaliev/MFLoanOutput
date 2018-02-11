package kg.gov.mf.loan.output.report.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="object_list_value")

public class ObjectListValue {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    
	@ManyToOne(targetEntity=ObjectList.class, fetch = FetchType.EAGER)
    @JoinColumn(name="object_list_id")
    ObjectList objectList;


	
	
	public ObjectListValue() {
	}

	public ObjectListValue(String name, ObjectList objectList) {
		this.name = name;
		this.objectList = objectList;
	}
	
	public ObjectListValue(Long id, String name, ObjectList objectList) {
		this.id = id;
		this.name = name;
		this.objectList = objectList;
	}

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
		final ObjectListValue other = (ObjectListValue) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}
}
