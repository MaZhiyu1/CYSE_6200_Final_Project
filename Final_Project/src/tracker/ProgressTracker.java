package tracker;

import java.util.List;

import user.GeneralUser;

//3) Abstract Classes/Interfaces
// 4) Generics/Collections/Iterators
public interface ProgressTracker<T> {

	/** Logs a new record from a user. **/
	void logRecord(GeneralUser user);

	/** Removes a specific record. **/
	void removeRecord(T record);

	/** Returns full record history. **/
	List<T> getRecordHistory();
}
