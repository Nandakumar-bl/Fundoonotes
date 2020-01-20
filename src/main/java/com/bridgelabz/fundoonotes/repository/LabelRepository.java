package com.bridgelabz.fundoonotes.repository;

import java.util.List; 

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Labels,Integer>
{
	
	@Query("from UserInfo where username=?1")
	UserInfo findByUsername(String username);
	
	@Query("from Labels where label=?1")
	Labels getLabel(String label);
	
	@Query("from Labels where id=?1")
	Labels getLabelbyId(int id);
	
	@Query(value = "insert into labels(label,userinfo_id) values(:newlabel,:user)", nativeQuery = true)
	@Modifying
	Integer createLabel(String newlabel,UserInfo user);
	
	@Query(value = "update labels set label=:label where id=:id",nativeQuery = true)
	@Modifying
	Integer updateLabel(String label,int id);
	
	@Query(value = "delete from labels where id=:id",nativeQuery = true)
	@Modifying
	int deleteLabel(int id);
	@Query(value = "select * from labels where userinfo_id=:id",nativeQuery = true)
	List<Labels> getAllUserLabel(int id);
	
	@Query("from Labels where id=?1")
	Labels getLabel(int id);
	
	@Query("from Notes where id=?1")
	Notes findbynoteid(int id);

}
