package com.bridgelabz.fundoonotes.repository;

import java.util.List; 

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;


@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Notes,Integer> 
{
	
	@Query("from Label where label=?1")
	Label findLabelByName(String label);
	
	@Query("from Label where label=?1")
	Label getLabel(String label);
	
	@Query(value = "insert into label(label) values(:newlabel)", nativeQuery = true)
	@Modifying
	Integer createLabel(String newlabel);
	
	
	
	@Query(value = "select max(id) from notes",nativeQuery = true)
	Integer giveMaxId();

	@Query(value = "insert into images(imagelink,notes_id) values(:link,:notesid)", nativeQuery = true)
	@Modifying
	Integer createImages(String link,int notesid);
	
	@Query(value = "select * from drawing where notes_id=:notesid",nativeQuery = true)
	List<Images> getImages(Integer notesid);
	
	@Query(value="select * from user_info where email=:email",nativeQuery = true)
	UserInfo getCollaborators(String email);
	
	
	
	@Query(value="insert into notes(title,takeanote,reminder,label,images,collaborator) values(:title,:takeanote,:reminder,:labels,:images,:collaborator)",nativeQuery = true)
	@Modifying
	Integer saveNote(String title,String takeanote,String reminder,List<Label> labels,List<Images> images,List<UserInfo> collaborator);

	
}
