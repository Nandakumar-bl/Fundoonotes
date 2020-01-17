package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class NoteImplementation implements NoteService {
	NoteRepository repository;
	Utility utility;

	@Autowired
	public NoteImplementation(NoteRepository repository, Utility utility) {
		this.repository = repository;
		this.utility = utility;

	}

	public boolean saveNewNoteImpl(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {

		UserInfo user = utility.getUser(jwt);
		if (user != null) {
			int notesid;
			try {
				notesid = repository.giveMaxId() + 1;
			} catch (NullPointerException e) {
				notesid = 1;
			}
			List<Label> labels = getLabelsImpl(notedto.getLabel(), jwt);
			List<Images> images = getImagesImpl(notedto.getImages(), notesid);
			Notes notes = new Notes(notedto.getTitle(), notedto.getTakeanote(), notedto.getReminder(),
					notedto.getColor(), labels, images, user);
			repository.save(notes);
			return true;

		} else {
			throw new UserException("No user for this Username");
		}
	}

	public List<Images> getImagesImpl(List<String> dtoimages, int notesid) {

		dtoimages.stream().map(s -> repository.createImages(s, notesid));
		List<Images> images = repository.getImages(notesid);
		return images;
	}

	public List<Label> getLabelsImpl(List<String> dtolabels, String jwt) {
		int size = dtolabels.size();
		UserInfo user = utility.getUser(jwt);
		for (int i = 0; i < size; i++) {
			if (repository.getLabel(dtolabels.get(i)) == null) {
				repository.createLabel(dtolabels.get(i), user);
			}
		}
		List<Label> labels = dtolabels.stream().map(s -> repository.findLabelByName(s)).collect(Collectors.toList());

		return labels;
	}

	public void deleteNoteImpl(int id, String jwt) throws JWTTokenException, NoteNotFoundException {
		UserInfo user = utility.getUser(jwt);
		if (utility.validateToken(jwt) && user != null) {
			if (repository.deleteByNoteid(id) == null)
				throw new NoteNotFoundException("No Note available with this ID");
		} else {
			throw new JWTTokenException("Your Token is Not valid");
		}
	}

	public boolean updateNoteImpl(UpdateNoteDTO updatedto, String jwt) throws NoteNotFoundException, JWTTokenException {
		if (utility.validateToken(jwt)) {
			List<Label> labels = getLabelsImpl(updatedto.getLabel(), jwt);
			List<Images> images = getImagesImpl(updatedto.getImages(), updatedto.getId());
			Notes note = repository.getNotes(updatedto.getId());
			if (note == null)
				throw new NoteNotFoundException("Note not available for this id");
			System.out.println(note);
			Notes notes = utility.getUpdatedNote(updatedto, note, images, labels);
			repository.save(notes);
			return true;
		} else
			throw new JWTTokenException("Problem with your Token");
	}

	public NoteDTO getNoteImpl(int id) {
		NoteDTO notes = new NoteDTO();
		Notes note = repository.getNotes(id);
		BeanUtils.copyProperties(note, notes);
		return notes;
	}

	public List<NoteDTO> getAllNoteImpl(String jwt) {

		UserInfo user = utility.getUser(jwt);
		List<Notes> allnotes = repository.getAllNotes(user.getId());
		List<NoteDTO> allnotedto = allnotes.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

		return allnotedto;
	}

	public List<NoteDTO> getAllArchieveImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> allarchieve = repository.getAllArchieve(user.getId());
		List<NoteDTO> allnotedtoarchieve = allarchieve.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

		return allnotedtoarchieve;
	}

	@Override
	public List<NoteDTO> getAllPinnedImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> allpinned = repository.getAllPinned(user);
		List<NoteDTO> allnotedtoarchieve = allpinned.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

		return allnotedtoarchieve;
	}

	@Override
	public List<NoteDTO> getAllTrashNotesImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> alltrash = repository.getAllTrash(user);
		List<NoteDTO> alltrashdto = alltrash.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

		return alltrashdto;
	}

	@Override
	public void emptyTheBin(String jwt) throws NoteNotFoundException 
	{
		UserInfo user = utility.getUser(jwt);
		if(repository.cleanBin(user.getId())!=0)
		{
			return ;
		}
		else
			throw new  NoteNotFoundException("your bin is clean already");
		
	}
	
	

}
