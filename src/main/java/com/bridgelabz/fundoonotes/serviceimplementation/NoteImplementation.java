package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.SortbyID;
import com.bridgelabz.fundoonotes.utility.Utility;
import com.bridgelabz.fundoonotes.utility.sortbyDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoteImplementation implements NoteService {
	private NoteRepository repository;
	private Utility utility;
	@Autowired
	private ElasticSearchService eservice;

	int i = 0;

	@Autowired
	public NoteImplementation(NoteRepository repository, Utility utility) {
		this.repository = repository;
		this.utility = utility;

	}

	public boolean saveNewNoteImpl(NoteDTO notedto, String jwt) throws JWTTokenException, Exception {

		UserInfo user = utility.getUser(jwt);
		List<Labels> labels;
		List<Images> images;
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
			if (notedto.getImages() != null)
				images = getImagesImpl(notedto.getImages(), notesid);
			else
				images = null;
			Notes notes = new Notes(notedto.getTitle(), notedto.getTakeanote(), notedto.getReminder(),
					notedto.getColor(), labels, images, user);
			repository.save(notes);
			notedto.setId(notesid);
			eservice.newNote(notedto);
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

	public void deleteNoteImpl(int id, String jwt) throws JWTTokenException, NoteNotFoundException {
		UserInfo user = utility.getUser(jwt);
		eservice.deleteNote(id);
		if (utility.validateToken(jwt) && user != null) {
			if (repository.deleteByNoteid(id) == null)
				throw new NoteNotFoundException("No Note available with this ID");
		} else {
			throw new JWTTokenException("Your Token is Not valid");
		}
	}

	public boolean updateNoteImpl(UpdateNoteDTO updatedto, String jwt) throws JsonProcessingException, Exception {
		if (utility.validateToken(jwt)) {
			List<Labels> labels = getLabelsImpl(updatedto.getLabels(), jwt);
			List<Images> images = getImagesImpl(updatedto.getImages(), updatedto.getId());
			Notes note = repository.getNotes(updatedto.getId());
			if (note == null)
				throw new NoteNotFoundException("Note not available for this id");
			Notes notes = utility.getUpdatedNote(updatedto, note, images, labels);
			repository.save(notes);
			NoteDTO notedt = new NoteDTO();
			BeanUtils.copyProperties(updatedto, notedt);
			eservice.updateNote(notedt);
			return true;
		} else {
			throw new JWTTokenException("Problem with your Token");
		}
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

		return allnotes.stream().map(s -> {
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
		Arrays.sort(dtos, new SortbyID());
		return dtos;
	}

}
