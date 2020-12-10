package de.hfu.residents.repository;

import java.util.ArrayList;
import java.util.List;

import de.hfu.residents.domain.Resident;

public class ResidentRepositoryStub implements ResidentRepository{
	
	List<Resident> residentList = new ArrayList<Resident>();

	@Override
	public List<Resident> getResidents() {
		return this.residentList;
	}
	
	public void addResident(Resident resident) {
		this.residentList.add(resident);
	}

}
