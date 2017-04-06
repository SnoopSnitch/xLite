package zz.snsn.xlite.qdmon;

import java.util.Vector;

import zz.snsn.xlite.analysis.Event;
import zz.snsn.xlite.analysis.ImsiCatcher;
import zz.snsn.xlite.analysis.RAT;
import zz.snsn.xlite.analysis.Risk;

public interface AnalysisEventDataInterface {
	public Event getEvent(long id);
	public Vector<Event> getEvent(long startTime, long endTime);
	public ImsiCatcher getImsiCatcher(long id);
	public Vector<ImsiCatcher> getImsiCatchers(long startTime,	long endTime);
	public Risk getScores();
	public RAT getCurrentRAT();
}
