package com.javasampleapproach.partitioning;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringBatchPartitioningApplicationTests {

	@Test
	@Ignore
	public void contextLoads() {
	}
	
	@Test
	public void test(){
		
		Double d1 = 0.1212;
		Double d2 = 0.2212;
		List<Double> ds = new ArrayList<Double>();
		ds.add(d1);
		ds.add(d2);
		
		System.out.println("String -- "+ds.toString());
		
		Double[] i = fromString(ds.toString());
	    System.out.println("Double[] -- "+i.length);
	  }
		
	//@Test
	  private static Double[] fromString(String string) {
		//Double[] newList;
	    String[] strings = string.replace("\"", "").replace("[", "").replace("]", "").replace("\"", "").split(", ");
	    Double result[] = new Double[strings.length];
	    for (int i = 0; i < result.length; i++) {
	      result[i] = Double.parseDouble(strings[i]);
	      //newList = result;
	    }
	    return result;
	  }

}
