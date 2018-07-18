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



	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	@Enumerated(EnumType.STRING)
	private RowType rowType;

	@Enumerated(EnumType.STRING)
	private CellType cellType;

    @Column(name="field_name")
    private String fieldName;
    
    @Column(name="classificator_id")
    private long classificatorId;

    @Column(name="classificator_value_id")
    private long classificatorValueId;

    @Column(name="constant_value")
    private double constantValue;

	@Column(name="constant_int")
	private int constantInt;


    @Column(name="constant_text")
    private String constantText;
    
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    @Temporal(TemporalType.DATE)
    private Date constantDate;

	@Column(name="position")
	private int position;

	@Column(name="col_from")
	private int col_from;

	@Column(name="row_from")
	private int row_from;

	@Column(name="col_to")
	private int col_to;

	@Column(name="row_to")
	private int row_to;




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

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
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

	public Date getConstantDate() {
		return constantDate;
	}

	public void setConstantDate(Date constantDate) {
		this.constantDate = constantDate;
	}

	public RowType getRowType() {
		return rowType;
	}

	public void setRowType(RowType rowType) {
		this.rowType = rowType;
	}

	public int getConstantInt() {
		return constantInt;
	}

	public void setConstantInt(int constantInt) {
		this.constantInt = constantInt;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCol_from() {
		return col_from;
	}

	public void setCol_from(int col_from) {
		this.col_from = col_from;
	}

	public int getRow_from() {
		return row_from;
	}

	public void setRow_from(int row_from) {
		this.row_from = row_from;
	}

	public int getCol_to() {
		return col_to;
	}

	public void setCol_to(int col_to) {
		this.col_to = col_to;
	}

	public int getRow_to() {
		return row_to;
	}

	public void setRow_to(int row_to) {
		this.row_to = row_to;
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
