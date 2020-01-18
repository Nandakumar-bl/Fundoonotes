package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImplementation implements ElasticSearchService
{
	@Autowired
	ElasticSearchConfig client;
	
	@Autowired
	ObjectMapper objectMapper;
	
	String index="fundoo";
	String type="notedto";
	
	
	@Override
	public void newNote(NoteDTO note) throws IOException {
		
		
		Map dataMap = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, note.getId() + "").source(dataMap);
		IndexResponse response = client.client().index(indexRequest);
	}

	@Override
	public void updateNote(NoteDTO note) throws Exception 
	{
		 UpdateRequest updateRequest = new UpdateRequest(index, type, note.getId()+"").fetchSource(true);  
		  String notestring = objectMapper.writeValueAsString(note);
		  updateRequest.doc(notestring, XContentType.JSON);
		  UpdateResponse updateResponse =  client.client().update(updateRequest);
		
	}

	@Override
	public void deleteNote(int id) 
	{
		
		DeleteRequest deleteRequest = new DeleteRequest(index, type,id+"");
		  try {
		    DeleteResponse deleteResponse = client.client().delete(deleteRequest);
		  } catch (java.io.IOException e){
		    e.getLocalizedMessage();
		  }
	}

	public Object getnote(String id) throws IOException
	{
		GetRequest getRequest=new GetRequest(index,type,id);
		GetResponse getResponse=client.client().get(getRequest);
	   NoteDTO notedto=objectMapper.readValue(getResponse.getSource(),NoteDTO.class);

		return getResponse.getSource();	
	}

}
