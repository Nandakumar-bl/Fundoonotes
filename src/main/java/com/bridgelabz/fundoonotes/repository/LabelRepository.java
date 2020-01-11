package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Label,Integer>
{
	
	@Query("from UserInfo where username=?1")
	UserInfo findByUsername(String username);
	
	@Query("from Label where label=?1")
	Label getLabel(String label);
	
	@Query("from Label where id=?1")
	Label getLabelbyId(int id);
	
	@Query(value = "insert into label(label,userinfo_id) values(:newlabel,:user)", nativeQuery = true)
	@Modifying
	Integer createLabel(String newlabel,UserInfo user);
	
	@Query(value = "update label set label=:label where id=:id",nativeQuery = true)
	@Modifying
	Integer updateLabel(String label,int id);
	
	@Query(value = "delete from label where id=:id",nativeQuery = true)
	@Modifying
	int deleteLabel(int id);

}
