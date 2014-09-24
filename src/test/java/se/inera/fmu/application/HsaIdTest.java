package se.inera.fmu.application;

import static org.junit.Assert.*;

import org.junit.Test;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

public class HsaIdTest {

	@Test
	public void test() {
		HsaId hsaId = new HsaId("SE160000000000-HAHAHHSAA") ;
	}
	
	
//	HSA-id är ett attribut som används för att identifiera objekt i HSA. HSA-id är en unik nyckel 
//	som används av andra tjänster för att identifiera objekt. 
//	På det sättet kan man veta att ”Anna 
//	Andersson” är samma ”Anna Andersson” trots att det finns flera personer med samma namn i 
//	en organisation, eller att vårdcentralen ”City” är samma enhet som vårdcentralen ”Kungsgatan” 
//	trots att den bytt namn.
//	När man skapar ett objekt i HSA Admin får det automatiskt ett HSA-id. HSA-id består av två 
//	delar. En del som identifierar utfärdande organisation och en del som identifierar det unika 
//	objektet. Strukturen för ett HSA-id ser ut på följande sätt:
//	SE<organisationsnummer för utfärdande organisation>-<löpnummer för objekt>
//	Löpnummer kan bestå av både bokstäver och siffror.
//	Om en person förekommer på flera ställen hos en och samma organisation har personen samma 
//	HSA-id på alla ställen. Däremot om en person förekommer hos flera organisationer kommer 
//	personen att ha olika HSA-id

}
