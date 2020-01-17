package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.NoteDTO;

@Repository
public interface Elasticrepository extends ElasticsearchRepository<NoteDTO,Integer>
{

}
