package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImplementation implements ElasticSearchService
{
	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String index="fundoo";
	private String type="notedto";
	
	
	@Override
	public Result newNote(NoteDTO note) throws IOException {
		
		Map dataMap = objectMapper.convertValue(note,Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type,  note.getId()+"").source(dataMap);
		IndexResponse response = client.index(indexRequest,RequestOptions.DEFAULT);
		return response.getResult();
	}

	@Override
	public Result updateNote(NoteDTO note) throws IOException 
	{
		 UpdateRequest updateRequest = new UpdateRequest(index, type, note.getId()+"").fetchSource(true);  
		  String notestring = objectMapper.writeValueAsString(note);
		  updateRequest.doc(notestring, XContentType.JSON);
		  UpdateResponse updateResponse =  client.update(updateRequest,RequestOptions.DEFAULT);
		return updateResponse.getResult();
	}

	@Override
	public Result deleteNote(int id) 
	{
		
		DeleteRequest deleteRequest = new DeleteRequest(index, type,id+"");
		  try {
		    DeleteResponse deleteResponse = client.delete(deleteRequest,RequestOptions.DEFAULT);
		    return deleteResponse.getResult();
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
		  return null;
	}

	public NoteDTO getnote(String id) throws IOException
	{
		GetRequest getRequest=new GetRequest(index,type,id);
		GetResponse getResponse=client.get(getRequest,RequestOptions.DEFAULT);
		return objectMapper.convertValue(getResponse.getSourceAsMap(),NoteDTO.class);
	}
	
	
	public List<NoteDTO> getAllNotes() throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits object = searchResponse.getHits();
		List<NoteDTO> notesall = new ArrayList();
		object.forEach(s -> {
			NoteDTO temp =objectMapper.convertValue(s.getSourceAsMap(),NoteDTO.class);
			notesall.add(temp);
		});
		return notesall;
	}

	public List<NoteDTO> getMatchedNote(String text) throws IOException  {
		String search = ".*" + text + ".*";
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.regexpQuery("title",search));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits object = searchResponse.getHits();
		List<NoteDTO> notesall = new ArrayList();
		object.forEach(s -> {
			NoteDTO temp =objectMapper.convertValue(s.getSourceAsMap(),NoteDTO.class);
			notesall.add(temp);
		});
		return notesall;

	}


	

}
