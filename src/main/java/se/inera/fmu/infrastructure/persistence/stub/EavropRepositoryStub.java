package se.inera.fmu.infrastructure.persistence.stub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Gender;
import se.inera.fmu.domain.model.patient.Initials;
import se.inera.fmu.domain.model.patient.Name;
import se.inera.fmu.domain.model.patient.Patient;

@Repository
public class EavropRepositoryStub implements EavropRepository {

	@Override
	public List<Eavrop> findAll() {
		ArrayList<Eavrop> list = new ArrayList<Eavrop>();
		list.add(new Eavrop(new ArendeId("123421"), UtredningType.AFU, "tolk field", new Patient("8702225467",
				new Name(Initials.MR, "Jacob", "william", "anderson"), Gender.MALE, new Address("blomstervägen",
						"58435", "Linköping", "sweden"), "jacob.william@gmail.com")));
		list.add(new Eavrop(new ArendeId("753423"), UtredningType.SLU, "tolk field", new Patient("7702225267",
				new Name(Initials.MR, "Erik", null, "lindgren"), Gender.MALE, new Address("ugglegatan", "55435",
						"Göteborg", "sweden"), "erik.lin@gmail.com")));
		list.add(new Eavrop(new ArendeId("44240"), UtredningType.AFU, null, new Patient("7702225467", new Name(
				Initials.MRS, "Anna", null, "hård"), Gender.MALE, new Address("stenvägen", "58435", "Stockholm",
				"sweden"), "anna.hård@gmail.com")));
		list.add(new Eavrop(new ArendeId("78743"), UtredningType.TMU, "tolk field", new Patient("8705225460", new Name(
				Initials.MISS, "Jansa", "william", "falk"), Gender.MALE, new Address("kungsgatan", "34435",
				"oskarshamn", "sweden"), "jansa.falk@gmail.com")));
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

}
