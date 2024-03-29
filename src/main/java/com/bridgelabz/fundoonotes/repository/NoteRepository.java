package com.bridgelabz.fundoonotes.repository;

import java.util.List; 
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;


@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Notes,Integer>,PagingAndSortingRepository<Notes, Integer>
{
	
	@Query("from UserInfo where username=?1")
	UserInfo findByUsername(String username);
	
	@Query("from UserInfo where id=?1")
	UserInfo findUserById(int id);
	
	@Query("from Labels where label=?1")
	Labels findLabelByName(String label);
	
	@Query("from Labels where label=?1")
	Labels getLabel(String label);
	
	@Query(value = "insert into labels(label,userinfo_id) values(:newlabel,:user)", nativeQuery = true)
	@Modifying
	Integer createLabel(String newlabel,UserInfo user);
	
	Page<Notes> findAllByuserinfo(UserInfo user,Pageable pageable);
	
	@Query("from Notes where id=?1")
	Notes getNotes(int id);
	
	@Query(value = "update notes set istrash=0 where id=:id",nativeQuery = true)
	@Modifying
	Integer restorenotebyId(int id);
	
	@Query(value = "Select * from notes where userinfo_id=:id", nativeQuery = true)
	List<Notes> getAllNotes(int id);
	
	@Query(value = "Select * from notes where isarchieve=1 and userinfo_id=:id", nativeQuery = true)
	List<Notes> getAllArchieve(int id);
	
	
	@Query(value = "select max(id) from notes",nativeQuery = true)
	Integer giveMaxId();
	
	@Query(value="select * from user_info where email=:email",nativeQuery = true)
	UserInfo getCollaborators(String email);
	
	@Query(value = "update notes set istrash=true where id=?1",nativeQuery = true)
	@Modifying
	Integer deleteByNoteid(int id);
	
	@Query("from Notes where ispinned=true and userinfo=?1")
	List<Notes> getAllPinned(UserInfo user);
	
	
	@Query("from Notes where istrash=true and userinfo=?1")
	List<Notes> getAllTrash(UserInfo user);
	
	@Query(value = "delete from notes where istrash=true and userinfo_id=?1" ,nativeQuery = true)
	@Modifying
	Integer cleanBin(int id);

}
