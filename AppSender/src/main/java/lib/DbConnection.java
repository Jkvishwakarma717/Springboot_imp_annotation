package lib;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DbConnection {

    public Connection _connection;
    private final String _db_host;
    private final String _db_user;
    private final String _db_pass;
    private final String _db_name;
    private final String _db_port;
    public final String _campaign_table;
    public final String _segment_table;
    public final String _datafilter_table;
   // public final String _apps_table;
    public final String _version;
    public final int _gap_in_mins;
    public final String _web_table;
    public final String _csv_root;
    public final String _generated_csv_path;
    private final String _config_file = "db_conf.properties";
    public final String _tbl_user;
    public final String _seg_apps;
    public final int _threads;
    public final int _status_pending;
    public final int _status_running;
    public final int _status_complete;

    public DateTimeFormatter _db_datetime_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DbConnection() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();

        try(InputStream resourceStream = loader.getResourceAsStream(this._config_file)) {
            props.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this._db_host = props.getProperty("mysqldb.host");
        this._db_user = props.getProperty("mysqldb.user");
        this._db_pass = props.getProperty("mysqldb.pass");
        this._db_name = props.getProperty("mysqldb.name");
        this._db_port = props.getProperty("mysqldb.port", "3306");
        this._campaign_table = props.getProperty("tablenames.campaign", "seg_campaigns");
        this._segment_table = props.getProperty("tablenames.segments", "seg_segments");
        this._seg_apps =  props.getProperty("tablenames.seg", "seg_apps");
        this._datafilter_table = props.getProperty("tablenames.datafilter", "seg_datafilter");
       // this._apps_table = props.getProperty("tablenames.apps", "tbl_user");
        this._web_table = props.getProperty("tablenames.web_table", "subscribed_users");
        this._csv_root = props.getProperty("sender.csv_root", "");
        this._generated_csv_path = props.getProperty("sender.generated_csv_path", "");
        this._version = props.getProperty("sender.version", "0");
        this._gap_in_mins = Integer.parseInt(props.getProperty("sender.select_gap", "5"));
		this._tbl_user =  props.getProperty("tablenames.tbl_user", "tbl_user");
		this._threads = Integer.parseInt(props.getProperty("sender.threads", ""));
		this._status_pending = Integer.parseInt(props.getProperty("campaign.status.pending", "0"));
	    this._status_running = Integer.parseInt(props.getProperty("campaign.status.running", "2"));
	    this._status_complete = Integer.parseInt(props.getProperty("campaign.status.complete", "3"));

        connect();

    }

    public Connection connect(){
        try {
            this._connection = DriverManager.getConnection("jdbc:mysql://"+this._db_host+":"+this._db_port+"/"+this._db_name+"?serverTimezone=UTC&zeroDateTimeBehavior=convertToNull",this._db_user,this._db_pass);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return this._connection;
    }

    public List<HashMap<String, String>> select(String sqlQuery) {
        List<HashMap<String, String>> _result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> _temp = new HashMap<>();

        try {
            PreparedStatement ps = this._connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while(rs.next()){
                _temp.clear();
                for (int i = 1; i <= columns; i++) {
                    if(md.getColumnName(i) != null){
                        String key = md.getColumnLabel(i);
                        String value = rs.getObject(i) != null ? rs.getObject(i, String.class) : "";
                        _temp.put(key, value);
                    }
                }
                _result.add(_temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _result;
    }
    public String generateCsvFromSql(String sqlQuery, String csv_file,String csvHeader){
        try {
            PreparedStatement ps = this._connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
           if(rs.isBeforeFirst()){
                FileWriter fileWriter = new FileWriter(csv_file, true);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180);
                csvPrinter.printRecord(csvHeader);
                while(rs.next()){
                    String[] csvLine = new String[columns];
                    for (int i = 1; i <= columns; i++) {
                        csvLine[i-1] = rs.getObject(i, String.class);
                    }
                    csvPrinter.printRecord(csvLine);
                }
                csvPrinter.flush();
           }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return csv_file;
    }


    public int update(String updateSql){
        int _return = 0;
        if(!updateSql.equals("") && !updateSql.isEmpty()){
            try {
                Statement stmt = this._connection.createStatement();
                _return = stmt.executeUpdate(updateSql);
                System.out.println("Update Succesfully " );
            } catch (SQLException sqlException) {
                System.out.println("Update failed: " + sqlException.getMessage());
                _return = 0;
            }
        }
        return _return;
    }

  
}
