package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Bestallaradministrator;

public class EavropBuilder {
	//Required
	ArendeId arendeId;
	UtredningType utredningType;
    Invanare invanare;
	Bestallaradministrator bestallaradministrator;
	Landsting landsting;
	
	//Optional
	String description;
    Interpreter interpreter;
    String utredningFocus;
    String additionalInformation;
	PriorMedicalExamination priorMedicalExamination;


	private EavropBuilder(){
		super();
	}
	
	public static EavropBuilder eavrop(){
		return new EavropBuilder();
	} 
	
	public EavropBuilder withArendeId(ArendeId arendeId){
		this.arendeId = arendeId;
		return this;
	}

	public EavropBuilder withUtredningType(UtredningType utredningType){
		this.utredningType = utredningType;
		return this;
	}

	public EavropBuilder withInvanare(Invanare invanare){
		this.invanare = invanare;
		return this;
	}

	public EavropBuilder withBestallaradministrator(Bestallaradministrator bestallaradministrator){
		this.bestallaradministrator = bestallaradministrator;
		return this;
	}

	public EavropBuilder withLandsting(Landsting landsting){
		this.landsting = landsting;
		return this;
	}
	public EavropBuilder withInvanare(String description){
		this.description = description;
		return this;
	}
	
	public EavropBuilder withInterpreter(Interpreter interpreter){
		this.interpreter = interpreter;
		return this;
	}
	
	public EavropBuilder withUtredningFocus(String utredningFocus){
		this.utredningFocus = utredningFocus;
		return this;
	}
	public EavropBuilder withAdditionalInformation(String additionalInformation){
		this.additionalInformation = additionalInformation;
		return this;
	}
	public EavropBuilder withPriorMedicalExamination(PriorMedicalExamination priorMedicalExamination){
		this.priorMedicalExamination = priorMedicalExamination;
		return this;
	}
	
	public Eavrop build(){
		return new Eavrop(this);
	}
	
}
