package com.rest.webservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rest.webservices.bean.SomeBean;

@RestController
public class FilteringController {

	@GetMapping(value = "/filtering")
	public MappingJacksonValue retreiveSomeBean() {
		SomeBean bean = new SomeBean("Value1", "Value2", "Value3");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
	@GetMapping(value = "/filtering-list")
	public MappingJacksonValue retreiveListOfSomeBean() {
		List<SomeBean> asList = Arrays.asList(new SomeBean("Value1", "Value2", "Value3"), new SomeBean("Value12", "Value22", "Value32"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(asList);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
}