package service;

import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MessageService {

	private final String _config_file = "db_conf.properties";
	private String Response_Writer_path;
	private int _threads;
	private int size;
	public MessageService() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(this._config_file)) {
			props.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._threads = Integer.parseInt(props.getProperty("sender.threads"));
		this.Response_Writer_path = props.getProperty("sender.Response_Writer_path");
		this.size = Integer.parseInt(props.getProperty("sender.size"));
		
	}

	 static <T> Collection<List<String>> get_chunks(List<String> inputList, int size) {
			final AtomicInteger counter = new AtomicInteger(0);
			return inputList.stream().collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size)).values();
		}

	public HashMap<String, Integer> sendMultiPushFromCsv(String csvHeader, String csv_path,JSONObject protocol,
			String api_key) {

		HashMap<String, Integer> _return = new HashMap<>();
		int success = 0;
		int failure = 0;
		int total = 0;
		int start=0;
		try {
			List<String> reg_idList = new ArrayList<>();

			 BufferedReader	br = new BufferedReader(new FileReader(csv_path));
			 String line;
			ExecutorService executor = Executors.newFixedThreadPool(this._threads);
			String header = br.readLine();
			while((line = br.readLine()) != null){
				total++;
				System.out.println("line ="+line);
				String _gcmid = line;
				reg_idList.add(_gcmid);
				if (total % (size * this._threads) == 0) {
					Collection<List<String>> chunks = get_chunks(reg_idList, size);
					 Iterator<List<String>> reg_idIterator = chunks.iterator();
					 List<MultiPush> callableTasks = new ArrayList<>();
						while (reg_idIterator.hasNext()) {
							start++;
							 List<String> reg_id = reg_idIterator.next();
							 System.out.println("reg_id "+reg_id);
						protocol.put("registration_ids", reg_id);
						callableTasks.add(new MultiPush(protocol, api_key, Response_Writer_path));
						  if(start%this._threads==0)  {  
						List<Future<JSONObject>> futures = executor.invokeAll(callableTasks);
						for (Future<JSONObject> future : futures) {
							if (future.isDone()) {
								success += (int) future.get().get("success");
								failure += (int) future.get().get("failure");
							}
						}
						
						callableTasks.clear();
						reg_idList.clear();
					}
				}
						
				}
			}
			 br.close();
			if (reg_idList.size() > 0 ) {
				Collection<List<String>> chunks = get_chunks(reg_idList,size );
				 Iterator<List<String>> reg_idIterator = chunks.iterator();
				 List<MultiPush> callableTasks = new ArrayList<>();
					while (reg_idIterator.hasNext()) {
						 List<String> reg_id = reg_idIterator.next();
						 System.out.println("reg_id =>"+reg_id);
					protocol.put("registration_ids", reg_id);
					callableTasks.add(new MultiPush(protocol, api_key, Response_Writer_path)); 
					List<Future<JSONObject>> futures = executor.invokeAll(callableTasks);
					for (Future<JSONObject> future : futures) {
						if (future.isDone()) {
							success += (int) future.get().get("success");
							failure += (int) future.get().get("failure");
						}
					}
					callableTasks.clear();
					reg_idList.clear();
					_return.put("success", success);
					_return.put("failure", failure);				  
			}			
			}
			_return.put("total", total);
			executor.shutdown();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return _return;
	}
}
