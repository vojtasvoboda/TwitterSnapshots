package services;

import java.util.*;
import persistence.Snapshot;
import java.net.URL;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import java.text.SimpleDateFormat;

/**
 *
 * @author Michal
 */
public class GoogleChart {

    // https://chart.googleapis.com/chart?cht=<chart_type>&chd=<chart_data>&chs=<chart_size>&...additional_parameters...
    public String getGoogleURL(List<Snapshot> snapshots){

        String edge_data = "";
        String node_data = "";
        String date_data = "";
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("MM-dd-yyyy");
        StringBuilder MMDDYYYY;
        int i = 0;
        for(Snapshot snapshot : snapshots) {
            if( i == 0) {
                i++;
                edge_data += snapshot.getEdgescount();
                node_data += snapshot.getNodescount();
                MMDDYYYY = new StringBuilder( dateformatMMDDYYYY.format( snapshot.getCreated() ) );
                date_data += MMDDYYYY;
            } else {
                edge_data += ","+snapshot.getEdgescount();
                node_data += ","+snapshot.getNodescount();
                MMDDYYYY = new StringBuilder( dateformatMMDDYYYY.format( snapshot.getCreated() ) );
                date_data += "%7C"+MMDDYYYY;
            }
        }

        String chart_url = "http://chart.googleapis.com/chart?";
        String chart_type = "cht=lc";
        String chart_colors = "chco=FF0000,0000FF";
        String chart_size = "chs=500x250";
        String chart_margin = "chma=5,5,5,25%7C40";
        String chart_data = "chd=t:" + node_data + "%7C" + edge_data;
        String chart_axis = "chxt=y,y,x";
        String chart_legend = "chl=Edges%7CNodes";
        String chart_legpos = "chdlp=b";
        String chart_xAxis = "chxl=2:%7C"+date_data;
        String test = "chxp=2,10,20,30,40&chxr=1,5,1000%7C2,0,50";

        chart_url += chart_type /*+ "&"+chart_xAxis+"&"+test*/+"&" + chart_colors + "&" + chart_size + "&" + chart_margin + "&" + chart_data + "&" + chart_axis + "&" +chart_legend + "&" + chart_legpos;
       
        //return image;
        return chart_url;
    }

    public byte[] getGoogleGraph(String chart_url) throws Exception
    {
        System.out.println("graph url "+chart_url);
        URL url = new URL(chart_url);

        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = service.fetch(url);
        byte[] image = response.getContent();
        System.out.println("creating image ");

        return image;
    }
}
