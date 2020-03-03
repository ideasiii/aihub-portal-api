package iii.aihub.repository.imp;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.restlet.engine.io.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import iii.aihub.repository.ESRepository;
import org.springframework.stereotype.Component;

@Component(value = "esrepository")
public class ESRepositoryImp implements ESRepository {
	
	private Logger logger = LoggerFactory.getLogger("es-repository");

	private RestClient restClient;
	
	private String esIP;
	
	@PostConstruct
	public void init(){
		restClient = RestClient.builder(
		        new HttpHost("139.162.115.75", 9200, "http")
		        ).build();
	}
	
	@PreDestroy
	public void destroy(){
		try {
			restClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void _index(String indexName, String typeName, String id, String jsonBody) throws Exception {
		Response response = null;
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity(jsonBody, ContentType.APPLICATION_JSON);
			response = restClient.performRequest("PUT", "/"+indexName+"/"+typeName+"/"+id, params, entity);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("[_index]status code: ["+statusCode+"]");
		} catch (Exception e) {
			if(response != null){
				logger.error("[_index]status code: ["+response.getStatusLine().getStatusCode()+"]");
			}
			logger.error(e.getLocalizedMessage(), e);
			throw new Exception(e.getLocalizedMessage());
		}

	}
	
	private void _index(String indexName, String typeName, String jsonBody) throws Exception {
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity(jsonBody, ContentType.APPLICATION_JSON);
			Response response = restClient.performRequest("POST", "/"+indexName+"/"+typeName+"/", params, entity);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("[_index]status code: ["+statusCode+"]");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	private void _update(String indexName, String typeName, String id, String jsonBody) throws Exception {
		Response response = null;
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity("{\"doc\":"+jsonBody+"}", ContentType.APPLICATION_JSON);
			response = restClient.performRequest("POST", "/"+indexName+"/"+typeName+"/"+id+"/_update", params, entity);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("[_update]status code: ["+statusCode+"]");
		} catch (IOException e) {
			logger.error("rest client:"+restClient.toString());
			logger.error(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	private boolean _checkDocumentExist(String indexName, String typeName, String id, String jsonBody) throws Exception {
		String method = "GET";
		int statusCode = -1;
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity(jsonBody, ContentType.APPLICATION_JSON);
			Response response = restClient.performRequest(method, "/"+indexName+"/"+typeName+"/"+id, params, entity);
			statusCode = response.getStatusLine().getStatusCode();
			logger.info("[_checkDocumentExist]status code: ["+statusCode+"]");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}finally{
			if(statusCode == 200){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void index(String indexName, String typeName, String id, String jsonBody) throws Exception {
		//--- es auto update when id exist
		
		boolean isExist = _checkDocumentExist(indexName, typeName, id, jsonBody);
		logger.info("is exist: "+isExist);
		if(isExist){
			_update(indexName, typeName, id, jsonBody);
		}else{
			_index(indexName, typeName, id, jsonBody);
		}
		//_index(indexName, typeName, id, jsonBody);
	}
	
	public void index(String indexName, String typeName, String jsonBody) throws Exception {
		_index(indexName, typeName, jsonBody);
	}

	private List<Map<String, Object>> _search(String indexName, String typeName, String queryString){
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		String method = "GET";
		int statusCode = -1;
		Map<String, Object> data = null;
		List<Map<String, Object>> sources = null;
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity(queryString, ContentType.APPLICATION_JSON);
			Response response = restClient.performRequest(method, "/"+indexName+"/_search", params, entity);
			statusCode = response.getStatusLine().getStatusCode();
			logger.info("[_search]status code: ["+statusCode+"]");
			String content = IoUtils.toString(response.getEntity().getContent());
			logger.info("response: "+content);
			data = gson.fromJson(content, type);
			logger.info("data map: "+data.toString());
			sources = (List<Map<String, Object>>) ((Map<String, Object>) data.get("hits")).get("hits");
			logger.info("test: "+sources);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			//throw new Exception(e.getLocalizedMessage());
		}finally{
			
		}
		return sources;
		
	}
	
	public List<Map<String, Object>> search(String indexName, String typeName, String queryString){
		return _search(indexName, typeName, queryString);
	}
	
	public static void main(String[] args) throws Exception {
		ESRepositoryImp es = new ESRepositoryImp();
		es.init();
		/*
		String indexName = "test";
		String typeName ="test-type";
		String id ="111";
		String body = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch...\"" +
		        "}";
		es.index(indexName, typeName, id, body);
		//es.index(indexName, typeName, body);
		 * 
		 */
		String indexName = "hotel-priceai";
		String typeName = "";
		String queryString = "{\"query\":{\"match_all\":{}},\"size\":1,\"sort\":[{\"check_in_date\":{\"order\":\"desc\"}}]}";
		try{
			List<Map<String, Object>> sources = es.search(indexName, typeName, queryString);
			System.out.println("sources size: "+sources.size());
			Map<String, Object> data = (Map<String, Object>) (sources.get(0)).get("_source");
			System.out.println("date: "+ data.toString());
			System.out.println("check in date: "+ data.get("check_in_date"));
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		
		es.destroy();
		
		
	}

	public String getEsIP() {
		return esIP;
	}

	public void setEsIP(String esIP) {
		this.esIP = esIP;
	}

}
