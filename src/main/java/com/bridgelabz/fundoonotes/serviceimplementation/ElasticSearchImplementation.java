package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.io.NotSerializableException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImplementation implements ElasticSearchService
{
	@Autowired
	RestHighLevelClient client;
	
	@Autowired
	ObjectMapper objectMapper;
	
	String index="fundoo";
	String type="notedto";
	
	
	@Override
	public void newNote(NoteDTO note) throws IOException {
		
		
		System.out.println(note);
		Map dataMap = objectMapper.convertValue(note,Map.class);
		System.out.println(dataMap);
		IndexRequest indexRequest = new IndexRequest(index, type,  note.getId()+"").source(dataMap);
		IndexResponse response = client.index(indexRequest,RequestOptions.DEFAULT);
	}

	@Override
	public void updateNote(NoteDTO note) throws Exception 
	{
		 UpdateRequest updateRequest = new UpdateRequest(index, type, note.getId()+"").fetchSource(true);  
		  String notestring = objectMapper.writeValueAsString(note);
		  updateRequest.doc(notestring, XContentType.JSON);
		  UpdateResponse updateResponse =  client.update(updateRequest);
		
	}

	@Override
	public void deleteNote(int id) 
	{
		
		DeleteRequest deleteRequest = new DeleteRequest(index, type,id+"");
		  try {
		    DeleteResponse deleteResponse = client.delete(deleteRequest);
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
	}

	public Map getnote(String id) throws IOException
	{
		GetRequest getRequest=new GetRequest(index,type,id);
		GetResponse getResponse=client.get(getRequest);
		return getResponse.getSourceAsMap();	
	}
	
	
	public List<NoteDTO> getAllNotes() throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits object = searchResponse.getHits();
		List<NoteDTO> notesall = new ArrayList<NoteDTO>();
		object.forEach(s -> {
			NoteDTO temp =objectMapper.convertValue(s.getSourceAsMap(),NoteDTO.class);
			System.out.println(s.getSourceAsMap());
			notesall.add(temp);
		});
		return notesall;
	}

	public List<NoteDTO> getMatchedNote(String text) throws Exception {
		String search = ".*" + text + ".*";
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.regexpQuery("title", search));
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits object = searchResponse.getHits();
		List<NoteDTO> notesall = new ArrayList<NoteDTO>();
		object.forEach(s -> {
			NoteDTO temp =objectMapper.convertValue(s.getSourceAsMap(),NoteDTO.class);
			notesall.add(temp);
		});
		return notesall;

	}


	

}
