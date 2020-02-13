package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;
import com.bridgelabz.fundoonotes.utility.sortbyDate;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class NoteImplementation implements NoteService {
	private NoteRepository repository;
	private Utility utility;
	@Autowired
	private ElasticSearchService eservice;
	@Autowired
	ModelMapper mapper;

	int i = 0;

	@Autowired
	public NoteImplementation(NoteRepository repository, Utility utility) {
		this.repository = repository;
		this.utility = utility;

	}

	public boolean saveNewNoteImpl(NoteDTO notedto, String jwt) throws Exception {

		UserInfo user = utility.getUser(jwt);
		List<Labels> labels;
		if (user != null) {
			int notesid;
			try {
				notesid = repository.giveMaxId() + 1;
			} catch (NullPointerException e) {
				notesid = 1;
			}
			if (notedto.getLabels() != null)
				labels = getLabelsImpl(notedto.getLabels(), jwt);
			else
				labels = null;
			Notes notes = new Notes(notedto.getTitle(), notedto.getTakeanote(), notedto.getReminder(),
					notedto.getColor(), labels, user);
			repository.save(notes);
			notedto.setId(notesid);
			eservice.newNote(notedto);
			return true;

		} else {
			throw new UserException("No user for this Username");
		}
	}

	public List<Labels> getLabelsImpl(List<String> dtolabels, String jwt) {
		int size = dtolabels.size();
		UserInfo user = utility.getUser(jwt);
		for (int i = 0; i < size; i++) {
			if (repository.getLabel(dtolabels.get(i)) == null) {
				repository.createLabel(dtolabels.get(i), user);
			}
		}

		return dtolabels.stream().map(s -> repository.findLabelByName(s)).collect(Collectors.toList());

	}

	@CacheEvict(value = "mynotedelete", key = "#noteid")
	public void deleteNoteImpl(int noteid, String jwt) throws JWTTokenException, NoteNotFoundException {
		UserInfo user = utility.getUser(jwt);
		eservice.deleteNote(noteid);
		if (utility.validateToken(jwt) && user != null) {
			if (repository.deleteByNoteid(noteid) == null)
				throw new NoteNotFoundException("No Note available with this ID");
		} else {
			throw new JWTTokenException("Your Token is Not valid");
		}
	}

	public NoteDTO updateNoteImpl(UpdateNoteDTO updatedto, String jwt) throws JsonProcessingException, Exception {
		int noteid = updatedto.getId();
		if (utility.validateToken(jwt)) {
			List<Labels> labels = getLabelsImpl(updatedto.getLabels(), jwt);
			Notes note = repository.getNotes(updatedto.getId());
			if (note == null)
				throw new NoteNotFoundException("Note not available for this id");
			Notes notes = utility.getUpdatedNote(updatedto, note, labels);
			repository.save(notes);
			NoteDTO notedt = new NoteDTO();
			BeanUtils.copyProperties(updatedto, notedt);
			eservice.updateNote(notedt);
			return notedt;
		} else {
			throw new JWTTokenException("Problem with your Token");
		}
	}

	public NoteDTO getNoteImpl(int noteid) {
		Notes note = repository.getNotes(noteid);

		return mapper.map(note, NoteDTO.class);

	}

	public List<NoteDTO> getAllNoteImpl(String jwtAllNotes, int pageNo) {

		UserInfo user = utility.getUser(jwtAllNotes);

		Pageable pagednote = PageRequest.of(pageNo, 10);

		Page<Notes> pagenotes = repository.findAllByuserinfo(user, pagednote);

		return pagenotes.getContent().stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

	}

	public List<NoteDTO> getAllArchieveImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> allarchieve = repository.getAllArchieve(user.getId());

		return allarchieve.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

	}

	@Override
	public List<NoteDTO> getAllPinnedImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> allpinned = repository.getAllPinned(user);
		return allpinned.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

	}

	@Override
	public List<NoteDTO> getAllTrashNotesImpl(String jwt) {
		UserInfo user = utility.getUser(jwt);
		List<Notes> alltrash = repository.getAllTrash(user);
		return alltrash.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());

	}

	@Override
	public void emptyTheBin(String jwt) throws NoteNotFoundException {
		UserInfo user = utility.getUser(jwt);
		if (repository.cleanBin(user.getId()) == 0) {
			throw new NoteNotFoundException("your bin is clean already");
		}

	}

	public boolean restoreNoteImpl(int id) {
		if (repository.restorenotebyId(id) != null)
			return true;
		else
			return false;
	}

	public NoteDTO[] sortedNotes(String jwt) {
		UserInfo user = utility.getUser(jwt);

		List<Notes> allnotes = repository.getAllNotes(user.getId());
		List<NoteDTO> allnotedto = allnotes.stream().map(s -> {
			NoteDTO temp = new NoteDTO();
			BeanUtils.copyProperties(s, temp);
			return temp;
		}).collect(Collectors.toList());
		NoteDTO[] dtos = new NoteDTO[allnotedto.size()];
		allnotedto.forEach(s -> {
			dtos[i] = s;
			i++;
		});
		Arrays.sort(dtos, new sortbyDate());
		return dtos;
	}

}
