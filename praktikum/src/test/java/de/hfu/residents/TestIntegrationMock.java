package de.hfu.residents;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*; 
import static org.easymock.EasyMock.*;

import org.junit.Test;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.repository.ResidentRepositoryStub;
import de.hfu.residents.service.BaseResidentService;

public class TestIntegrationMock {
	BaseResidentService residentService = new BaseResidentService();
	List<Resident> residentList = new ArrayList<Resident>();
	
	@SuppressWarnings("deprecation")
	Resident resident1 = new Resident("Daniel", "Mueller", "Kanadaring 2", "Lahr", new Date(1999,4,30));
	@SuppressWarnings("deprecation")
	Resident resident2 = new Resident("Kevin", "Schmidt", "Flugplatzstr. 29", "Frieseneim", new Date (1999, 5, 26));
	@SuppressWarnings("deprecation")
	Resident resident3 = new Resident("Max", "Mustermann", "Flugplatzstr. 20", "Lahr", new Date(2000, 5, 29));
	@SuppressWarnings("deprecation")
	Resident resident4 = new Resident("Michael", "Mastermann", "Kruttenaustr. 11", "Offenburg", new Date(1994,5,16));
	
	
	
	@Test
	public void getFilteredResidentsTest() {
		ResidentRepository residentRepo = createMock(ResidentRepository.class);
		residentList.add(resident1);
		residentList.add(resident2);
		residentList.add(resident3);
		residentList.add(resident4);
		residentService.setResidentRepository(residentRepo);
		expect(residentRepo.getResidents()).andStubReturn(residentList);
		replay(residentRepo);
		residentService.setResidentRepository(residentRepo);
		
		Resident mResident = new Resident ("M*", "M*", "*", "*", null);
		List<Resident> filteredMList = residentService.getFilteredResidentsList(mResident);
		assertThat(filteredMList.get(0).getGivenName(), equalTo("Max"));
		assertThat(filteredMList.get(1).getGivenName(), equalTo("Michael"));
		
		Resident emptyResident = new Resident ("1", "2", "3", "4", null);
		filteredMList = residentService.getFilteredResidentsList(emptyResident);
		assertThat(filteredMList.isEmpty(), equalTo(true));
		
		Resident oneResident = new Resident ("*", "*", "Flugplatzstr.*", "*", null );
		filteredMList = residentService.getFilteredResidentsList(oneResident);
		assertThat(filteredMList.get(0).getGivenName(), equalTo("Kevin"));
		assertThat(filteredMList.get(1).getGivenName(), equalTo("Max"));
		assertThat(filteredMList.size(), equalTo(2));
		
		@SuppressWarnings("deprecation")
		Resident dateResident = new Resident ("*", "*", "*", "*", new Date(1994,5,16));
		filteredMList = residentService.getFilteredResidentsList(dateResident);
		assertThat(filteredMList.get(0).getGivenName(), equalTo("Michael"));
		
		
	}
}
