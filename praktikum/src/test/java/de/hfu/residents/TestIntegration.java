package de.hfu.residents;




import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepositoryStub;
import de.hfu.residents.service.BaseResidentService;
import de.hfu.residents.service.ResidentServiceException;

public class TestIntegration {
		ResidentRepositoryStub residentRepo = new ResidentRepositoryStub();
		BaseResidentService residentService = new BaseResidentService();
		
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
			residentRepo.addResident(resident1);
			residentRepo.addResident(resident2);
			residentRepo.addResident(resident3);
			residentRepo.addResident(resident4);
			residentService.setResidentRepository(residentRepo);
			
			Resident mResident = new Resident ("M*", "M*", "*", "*", null);
			List<Resident> filteredMList = residentService.getFilteredResidentsList(mResident);
			assertThat(filteredMList.get(0).getGivenName(), equalTo("Max"));
			assertThat(filteredMList.get(1).getGivenName(), equalTo("Michael"));
			
			Resident emptyResident = new Resident ("1", "2", "3", "4", null);
			filteredMList = residentService.getFilteredResidentsList(emptyResident);
			assertThat(filteredMList.isEmpty(), equalTo(true));
			
			Resident oneResident = new Resident ("*", "*", "Flugplatzstr.*", "*", null);
			filteredMList = residentService.getFilteredResidentsList(oneResident);
			assertThat(filteredMList.get(0).getGivenName(), equalTo("Kevin"));
			assertThat(filteredMList.get(1).getGivenName(), equalTo("Max"));
			assertThat(filteredMList.size(), equalTo(2));
			
			
			
		}
		@Test(expected = ResidentServiceException.class)
		public void getUniqueResidentsTest() throws ResidentServiceException {
			residentRepo.addResident(resident1);
			residentRepo.addResident(resident2);
			residentRepo.addResident(resident3);
			residentRepo.addResident(resident4);
			residentService.setResidentRepository(residentRepo);
			
			Resident mResident = new Resident ("Michael", "Mastermann", "", "", null);
			assertThat(residentService.getUniqueResident(mResident).getGivenName(), equalTo("Michael"));
			assertThat(residentService.getUniqueResident(mResident).getStreet(), equalTo("Kruttenaustr. 11"));
			
			Resident dResident = new Resident ("Daniel", "", "", "", null);
			assertThat(residentService.getUniqueResident(dResident).getGivenName(), equalTo("Daniel"));
			assertThat(residentService.getUniqueResident(dResident).getStreet(), equalTo("Kanadaring 2"));
			
			Resident nResident = new Resident ("", "", "", "", null);
			
			assertThat(residentService.getUniqueResident(nResident), equalTo(null));
		}

}
