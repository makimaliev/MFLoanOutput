package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="collateral_item_view")
@Immutable
public class CollateralResultView extends DebtorView
{
	@Column
	private long v_ca_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_agreementDate;

	@Column
	private String v_ca_agreementNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_arrestRegDate;

	@Column
	private String v_ca_arrestRegNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_collateralOfficeRegDate;

	@Column
	private String v_ca_collateralOfficeRegNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_notaryOfficeRegDate;

	@Column
	private String v_ca_notaryOfficeRegNumber;






	@Id
	@Column
	private long v_ci_id;



	@Column
	private double v_ci_collateralValue;

	@Column
	private double v_ci_demand_rate;

	@Column
	private String v_ci_description;



}