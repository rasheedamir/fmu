package se.inera.fmu.infrastructure.persistence.stub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.party.Bestallaradministrator;

@Repository
public class EavropRepositoryStub implements EavropRepository {

	@Override
	public List<Eavrop> findAll() {
		ArrayList<Eavrop> list = new ArrayList<Eavrop>();
		
		list.add( EavropBuilder.eavrop()
				.withArendeId(new ArendeId("123421"))
				.withUtredningType(UtredningType.AFU) 
				.withInvanare(new Invanare(new PersonalNumber("8702225467"),new Name("Jacob", "william", "anderson"),Gender.MALE, new Address("blomstervägen", "58435", "Linköping", "sweden"),"jacob.william@gmail.com",null))
				.withLandsting(new Landsting(new LandstingCode(1), "Stockholms läns landsting"))
				.withBestallaradministrator(new Bestallaradministrator("Per Elofsson","Handläggare", "LFC Stockholm", "08123456", "per.elofsson@forsakringskassan.se" ))
				.build());


		list.add( EavropBuilder.eavrop()
				.withArendeId(new ArendeId("753423"))
				.withUtredningType(UtredningType.SLU) 
				.withInvanare(new Invanare(new PersonalNumber("7702225267"),new Name("Erik", null, "lindgren"),Gender.MALE, new Address("ugglegatan", "55435", "Göteborg", "Sweden"), "erik.lin@gmail.com", "Personen är rullstilsbunden. Taxi behöver beställas"))
				.withLandsting(new Landsting(new LandstingCode(14), "Stockholms läns landsting"))
				.withBestallaradministrator(new Bestallaradministrator("Jan Björklund","Handläggare", "LFC Göteborg", "031123456", "jan.bjorklund@forsakringskassan.se" ))
				.build());

		list.add( EavropBuilder.eavrop()
				.withArendeId(new ArendeId("44240"))
				.withUtredningType(UtredningType.AFU) 
				.withInvanare(new Invanare(new PersonalNumber("7702225467"),new Name("Anna", null, "Hård"),Gender.MALE, new Address("stenvägen", "58435", "Uppsala","Sweden"), "anna.hård@gmail.com",null))
				.withLandsting(new Landsting(new LandstingCode(3), "Uppsala läns landsting"))
				.withBestallaradministrator(new Bestallaradministrator("Jakob Hård","Handläggare", "LFC Uppsala", "013123456", "jakob.hard@forsakringskassan.se" ))
				.build());

		list.add( EavropBuilder.eavrop()
				.withArendeId(new ArendeId("78743"))
				.withUtredningType(UtredningType.TMU) 
				.withInvanare(new Invanare(new PersonalNumber("8705225460"),new Name("Jansa", "William", "Falk"),Gender.FEMALE, new Address("Kungsgatan", "34435", "oskarshamn", "sweden"), "anna.hård@gmail.com",null))
				.withLandsting(new Landsting(new LandstingCode(8), "Kalmar läns landsting"))
				.withBestallaradministrator(new Bestallaradministrator("Tintin","Andersson", "LFC Kalmar", "0771524524", "tintin.andersson@forsakringskassan.se" ))
				.build());
		
		return list;
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInBatch(Iterable<Eavrop> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Eavrop> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Eavrop> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public Eavrop getOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Eavrop> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Eavrop> S saveAndFlush(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Eavrop> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Eavrop arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Eavrop> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Eavrop findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Eavrop> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Eavrop findByArendeId(ArendeId arendeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Eavrop> findAllByLandsting(Landsting landsting) {
		// TODO Auto-generated method stub
		return null;
	}

}
