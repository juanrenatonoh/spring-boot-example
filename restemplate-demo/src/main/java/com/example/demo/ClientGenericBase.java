package com.example.demo;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/***
 * Client generic 
 * all clases than use must be a @service (herencia)
 * 
 * @author jnoh
 *
 */
public class ClientGenericBase {
	
	@Autowired
	private Environment enviroment;
	
	@Autowired
	RestTemplate restTemplate;
	
	protected HttpHeaders globalHeaders = new HttpHeaders();
	
	
	


	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseEntity<T> execute(String urlProperties,HttpMethod httpMetod,Class<T> responseTypeClass) {
		
		//resolve urlquery in case queryparams
		String url =  String.format("%s", enviroment.getProperty(StringUtils.substringBefore(urlProperties, "?")));
		
		String queryParams = StringUtils.substringAfter(urlProperties, "?");
		if(StringUtils.isNotBlank(queryParams.trim())) {
			url = String.format("%s?%s", url,queryParams);
		}
		
		HttpEntity<String> httpEntity = new HttpEntity<>(globalHeaders);
		
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
	}


	


}
