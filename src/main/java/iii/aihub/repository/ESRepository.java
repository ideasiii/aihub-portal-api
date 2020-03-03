package iii.aihub.repository;

import java.util.List;

public interface ESRepository {

	public void index(String indexName, String typeName, String id, String body) throws Exception;
	
	public void index(String indexName, String typeName, String body) throws Exception;
	
	public List search(String indexName, String typeName, String queryString) throws Exception;
}
