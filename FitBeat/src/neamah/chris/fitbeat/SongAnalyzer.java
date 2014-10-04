package neamah.chris.fitbeat;


import java.io.File;

import com.echonest.api.v3.EchoNestException;
import com.echonest.api.v3.track.TrackAPI;
import com.echonest.api.v3.track.TrackAPI.AnalysisStatus;

public class SongAnalyzer {
	private static String ECHO_NEST_API_KEY = "VZVDNHSLZQESCOBYY";
	
	private TrackAPI trackAPI;
	
	public SongAnalyzer() {
		try {
			trackAPI = new TrackAPI(ECHO_NEST_API_KEY);
		} catch (EchoNestException e) {
			System.out.println("Cannot initialize SongAnalyzers");
		}
	}
	
	public float getTempo(File song) {
		try {
			String id = trackAPI.uploadTrack(song, false);
		    AnalysisStatus status = trackAPI.waitForAnalysis(id, 60000);
		    if (status == AnalysisStatus.COMPLETE) {
		       System.out.println("Tempo in BPM: " + trackAPI.getTempo(id));
		       return trackAPI.getTempo(id).getValue();
		    } 
		} catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;             
	}
}
