package org.jsp.adminhospitalapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsp.adminhospitalapp.dto.Admin;
import org.jsp.adminhospitalapp.dto.Hospital;

public class HospitalDao {
	EntityManager manager = Persistence.createEntityManagerFactory("dev").createEntityManager();
	
	public Hospital saveHospital(Hospital hospital, int admin_id)
	{
		Admin admin = manager.find(Admin.class, admin_id);
		if(admin != null)
		{
			admin.getHospitals().add(hospital);
			hospital.setAdmin(admin);
			EntityTransaction transaction = manager.getTransaction();
			manager.persist(hospital);
			transaction.begin();
			transaction.commit();
			return hospital;
		}
		return null;
	}
	
	public Hospital updateHospital(Hospital hospital)
	{
		Hospital dbHospital = manager.find(Hospital.class, hospital.getId());
		if(dbHospital != null)
		{
			EntityTransaction transaction = manager.getTransaction();
			dbHospital.setFounder(hospital.getFounder());
			dbHospital.setName(hospital.getName());
			dbHospital.setYear_of_estb(hospital.getYear_of_estb());
			dbHospital.setGst_number(hospital.getGst_number());
			transaction.begin();
			transaction.commit();
			return dbHospital;
		}
		return null;
	}
	
	public List<Hospital> findHospitalByAdminId(int id)
	{
		Query q = manager.createQuery("select a.hospitals from Admin a where a.id=?1");
		q.setParameter(1, id);
		return q.getResultList();
	}
	
	public List<Hospital> findHospitalsByAdminPhonePassword(long phone, String password)
	{
		Query q = manager.createQuery("select a.hospitals from Admin a where a.phone=?1 and a.password=?2");
		q.setParameter(1, phone);
		q.setParameter(2, password);
		return q.getResultList();
	}
	
	public List<Hospital> findHospitalsByAdminEmailPassword(String email, String password){
		Query q = manager.createQuery("select a.hospitals from Admin a where a.email=?1 and a.password=?2");
		q.setParameter(1, email);
		q.setParameter(2, password);
		return q.getResultList();
	}
	
}
