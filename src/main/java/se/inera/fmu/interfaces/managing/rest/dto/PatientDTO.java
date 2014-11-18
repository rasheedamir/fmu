package se.inera.fmu.interfaces.managing.rest.dto;

public class PatientDTO {
	private String initials;
	private String gender;
	private String dobYear;
	private String residenceCity;
	private Details details;
	
	public String getInitials() {
		return initials;
	}



	public void setInitials(String initials) {
		this.initials = initials;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getDobYear() {
		return dobYear;
	}



	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}



	public String getResidenceCity() {
		return residenceCity;
	}



	public void setResidenceCity(String residenceCity) {
		this.residenceCity = residenceCity;
	}



	public Details getDetails() {
		return details;
	}



	public void setDetails(Details details) {
		this.details = details;
	}


	public static class Details{
		private String socSecNo;
		private String name;
		private String phone;
		private String prevAssesment;
		private String sjukskrivandeVk;
		private String sjukskrivandeLakare;
		private String specialNeeds;
		public String getSocSecNo() {
			return socSecNo;
		}
		public void setSocSecNo(String socSecNo) {
			this.socSecNo = socSecNo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getPrevAssesment() {
			return prevAssesment;
		}
		public void setPrevAssesment(String prevAssesment) {
			this.prevAssesment = prevAssesment;
		}
		public String getSjukskrivandeVk() {
			return sjukskrivandeVk;
		}
		public void setSjukskrivandeVk(String sjukskrivandeVk) {
			this.sjukskrivandeVk = sjukskrivandeVk;
		}
		public String getSjukskrivandeLakare() {
			return sjukskrivandeLakare;
		}
		public void setSjukskrivandeLakare(String sjukskrivandeLakare) {
			this.sjukskrivandeLakare = sjukskrivandeLakare;
		}
		public String getSpecialNeeds() {
			return specialNeeds;
		}
		public void setSpecialNeeds(String specialNeeds) {
			this.specialNeeds = specialNeeds;
		}
	}
}
