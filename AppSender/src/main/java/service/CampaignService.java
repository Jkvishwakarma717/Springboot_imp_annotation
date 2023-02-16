package service;

import lib.DbConnection;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class CampaignService extends DbConnection{
    private int _campaign_type;
    
    public CampaignService(int campaign_type){
        super();
        this._campaign_type = campaign_type;
    }
    public List<HashMap<String, String>> getScheduledCampaigns(int campaign_id){
    	String campaignSelect = "SELECT " +
                "c.id, c.`landing_url`, c.`title`, c.`message`, c.`banner_img_url`, c.`icon_img_url`, c.`cta_button_count`, c.`btn_a_title`, c.`btn_a_url`, c.`btn_b_title`, c.`btn_b_url`, c.`notify_expire_in`, c.`open_inside_app`, c.`notify_sticky`, c.`app_id`, c.`iz_run_id`, c.`data_source`, c.run_time, " +
                "s.id as segment_id, s.data_by, s.file_path, " +
                "d.id as datafilter_id, d.sfrom as start_date, d.sto as end_date, d.states, d.cities, d.apk_version, d.and_version, d.models, d.brands, d.langs, d.devices, d.browsers, d.platforms, " +
                "k.iz_pid,k.iz_cid,k.tkn " +
                "FROM " +
                this._campaign_table + " c " +
                "LEFT JOIN " +
                this._segment_table + " s ON c.`segment_id` = s.`id` " +
                "LEFT JOIN " +
                this._datafilter_table + " d ON c.`datafilter_id` = d.`id` " +
                "LEFT JOIN " +
                this._seg_apps + " k ON c.`id` = k.`id` " +
                "WHERE " +
                "c.id = " + Integer.toString(campaign_id);
        return this.select(campaignSelect);
    }
    
    public List<HashMap<String, String>> getScheduledCampaigns(){
        String start_time = LocalDateTime.now().format(this._db_datetime_format);
        String end_time = LocalDateTime.now().plusMinutes(this._gap_in_mins).format(this._db_datetime_format);
        String campaignSelect = "SELECT " +
        		 "c.id, c.`landing_url`, c.`title`, c.`message`, c.`banner_img_url`, c.`icon_img_url`, c.`cta_button_count`, c.`btn_a_title`, c.`btn_a_url`, c.`btn_b_title`, c.`btn_b_url`, c.`notify_expire_in`, c.`open_inside_app`, c.`notify_sticky`, c.`app_id`, c.`iz_run_id`, c.`data_source`, c.run_time, " +
        		  "s.id as segment_id, s.data_by, s.file_path, " +
                  "d.id as datafilter_id, d.sfrom as start_date, d.sto as end_date, d.states, d.cities, d.apk_version, d.and_version, d.models, d.brands, d.langs, d.devices, d.browsers, d.platforms, " +
        		 "k.iz_pid,k.iz_cid,k.tkn " +
                 "FROM " +
                 this._campaign_table + " c " +
                 "LEFT JOIN " +
                 this._segment_table + " s ON c.`segment_id` = s.`id` " +
                 "LEFT JOIN " +
                 this._datafilter_table + " d ON c.`datafilter_id` = d.`id` " +
                 "LEFT JOIN " +
                 this._seg_apps + " k ON c.`id` = k.`id` " +
                 "WHERE " +
                 "c.status = 0 AND c.run_time >= '" + start_time + "' AND k.app_type = " + this._campaign_type + " AND c.run_time <= '" + end_time + "' AND c.version = "+this._version;
             return this.select(campaignSelect);
           
    }
  
    
    public String getUserCsv(HashMap<String, String> CampaignData){
    	 String _return = "";
    	  String csvHeader = "gcmid";
    	 String exe_sql = "";
		 String sql=" SELECT t.gcmid FROM " + this._tbl_user + " t ";
		 String output_csv = this._generated_csv_path +"\\"+CampaignData.get("id")+"_"+new Date().getTime()+".csv";
		 String whereClause = " WHERE status = 1 ";
			  if(CampaignData.get("iz_pid") != "" && CampaignData.get("iz_pid") != null){
			  whereClause += " AND t.application = "+CampaignData.get("iz_pid"); }
			  
		 // Segment Data
		 if(CampaignData.get("data_source").equals("1")){
	            String segment_column = getDataByMeaning(Integer.parseInt(CampaignData.get("data_by").toString()));
	            String segment_path = this._csv_root+CampaignData.get("file_path");
	            File segment_file = new File(segment_path);
	            if(segment_file.exists()){
	                try{
	                    Path path = Paths.get(segment_path);
	                    long lines = Files.lines(path).parallel().count();
	                  
	                    String segment_where = " AND t."+segment_column+" IN (";
	                    String line = "";
	                    int line_counter = 0;
	                    FileReader fr = new FileReader(segment_file);
	                    BufferedReader br = new BufferedReader(fr);
	                    while ((line = br.readLine()) != null){
	                        line_counter++;
	                        segment_where += "'"+line+"', ";
	                        if(line_counter % 5000 == 0 || line_counter == lines){
	                            segment_where = StringUtils.stripEnd(segment_where, ", ");
	                            segment_where += ")";
	                            exe_sql = sql + whereClause + segment_where;
	                            generateCsvFromSql(exe_sql, output_csv,csvHeader);

	                            segment_where = " AND t."+segment_column+" IN (";
	                        }
	                    }
	                    br.close();
	                }catch(Exception ex){
	                    ex.printStackTrace();
	                }
	                _return = output_csv;
	            }
	        }
		 // Filter Data
	        else if(CampaignData.get("data_source").equals("2")){
	            if(!CampaignData.get("start_date").equals("") && !CampaignData.get("start_date").equals(null)){
	                whereClause += " AND u.entry_datetime >= '"+CampaignData.get("start_date")+" 00:00:00'";
	            }

	            if(!CampaignData.get("end_date").equals("") && !CampaignData.get("end_date").equals(null)){
	                whereClause += " AND u.entry_datetime <= '"+CampaignData.get("end_date")+" 23:59:59'";
	            }

	            if(!CampaignData.get("states").equals("") && !CampaignData.get("states").equals(null)){
	                whereClause += " AND u.state IN ('"+CampaignData.get("states").replace(",", "','")+"')";
	            }

	            if(!CampaignData.get("cities").equals("") && !CampaignData.get("cities").equals(null)){
	                whereClause += " AND u.city IN ('"+CampaignData.get("cities").replace(",", "','")+"')";
	            }

	            if(!CampaignData.get("devices").equals("") && !CampaignData.get("devices").equals(null)){
	                whereClause += " AND u.device_type IN ('"+CampaignData.get("devices")+"')";
	            }

	            if(!CampaignData.get("brands").equals("") && !CampaignData.get("brands").equals(null)){
	                whereClause += " AND u.ua_brand IN ('"+CampaignData.get("brands").replace(",", "','")+"')";
	            }

	            if(!CampaignData.get("models").equals("") && !CampaignData.get("models").equals(null)){
	                whereClause += " AND u.ua_model IN ('"+CampaignData.get("models").replace(",", "','")+"')";
	            }

	           

	            exe_sql = sql + whereClause;
	            generateCsvFromSql(exe_sql, output_csv,csvHeader);
	            _return = output_csv;
	        }

	        return _return;
	    }

    public boolean setToRunning(int campaign_id){
        return updateCampaignStatus(campaign_id, "running", 0, 0, 0);
    }

    public boolean setToComplete(int campaign_id, long target_count, long success_count, long run_id){
        return updateCampaignStatus(campaign_id, "complete", target_count, success_count, run_id);
    }

    private boolean updateCampaignStatus(int campaign_id, String status, long target_count, long success_count, long run_id){
        boolean _return = false;
        String updateSql  = "UPDATE " + this._campaign_table + " SET status = " + getStatusByName(status) + ", last_run = '"+LocalDateTime.now().format(this._db_datetime_format)+"'";
        if(status.equalsIgnoreCase("complete")){
            updateSql += ", target_count = " + target_count + ", success_count = " + success_count + ", iz_run_id = "+ run_id;
        }
        updateSql  += " WHERE id = " + String.valueOf(campaign_id);
        int success = update(updateSql);
        if (success > 0){
            _return = true;
        }else {
            _return = false;
        }
        return _return;
    }         
	       
    private String getDataByMeaning(int data_by){
        //1-> Android Id, 2-> IMEI, 3-> GAID, 4-> User ID
        String _return;
        switch(data_by){
            case 1:
                _return = "android_id";
                break;
            case 2:
                _return = "imei";
                break;
            case 3:
                _return = "device_gaid";
                break;
            case 4:
                _return = "userid";
                break;
            default:
                _return = "";
        }
        return _return;
    }
    public int getStatusByName(String status){
        int _return;
        switch (status){
            case "running":
                _return = this._status_running;
                break;
            case "pending":
                _return = this._status_pending;
                break;
            case "complete":
                _return = this._status_complete;
                break;
            default:
                _return = 9;
        }
        return _return;
    }
}
