package score.Check;

import java.io.File;

/**
 * This interface is used to check whether information your input is effective
 * and the type Cherk_data_efficiency must implement the inherited abstract.
 * method
 * 
 * @author Bloduc_Spauter
 *
 */
abstract class Check_information {
	/**
	 * It can verify the validation of all inputs before you add information into
	 * the two-dimensional array. Not can you append data until
	 * {@link #Check_uid(String)},{@link #Check_name(String)} and
	 * {@link #Check_score(String)} are true.
	 * 
	 * @param uid
	 * @param name
	 * @param MathScore
	 * @param EnglishScore
	 * @param ComputerScore
	 * @return boolean
	 */
	public boolean CheckDataEfficiency(String uid, String name, String MathScore, String EnglishScore,
			String ComputerScore) {

		/** Checking whether the students' number is valid. */
		Check_uid(uid);

		/** Checking whether the student's name is valid. */
		Check_name(name);

		/** Checking whether the student's Math scores is valid . */
		Check_score(MathScore);

		/** Checking whether the student's English scores is valid. */
		Check_score(EnglishScore);

		/** Checking whether the student's Computer scores is valid . */
		Check_score(ComputerScore);

		return Check_uid(uid) && Check_name(name) && Check_score(MathScore) && Check_score(EnglishScore)
				&& Check_score(ComputerScore);
	}

	/**
	 * A abstract way to validate the student's number whether is right.
	 * 
	 * 
	 * @param uid
	 * @return
	 */
	abstract boolean Check_uid(String uid);

	/**
	 * A abstract way to validate the student's name.
	 * 
	 * @param name
	 * @return
	 */
	abstract boolean Check_name(String name);

	/**
	 * A abstract way to validate the student's scores.
	 * 
	 * @param score
	 * @return
	 */
	abstract boolean Check_score(String score);

	/**
	 * A abstract way to validate whether the file exists.
	 * 
	 * @param path
	 * @return
	 */
	abstract boolean Check_File(String path);

}

/**
 * This is instantiated by inheritance. 
 * <p/>
 * Used to check the efficiency of data.
 * 
 * @author Bloduc_Spauter
 *
 */
public class DataValidation extends Check_information {

	/**
	 * To validate the student's number.If the number you input of digits entered is
	 * not enough,or a string with non-numeric characters was entered,this method
	 * will return false.Besides ,is will return false when you didn't enter
	 * anything.
	 * <p/>
	 * Because this approach can't check whether having the same students'
	 * numbers;it by other way.
	 */
	public boolean Check_uid(String uid) {
		if (uid == "null" || uid.length() == 0) {
			return false;
		} else if (uid.length() != 4) {
			return false;
		} else {
			try {
				if (Integer.parseInt(uid) <= 0) {
					return false;
				}
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}

	/**
	 * This method is only to check whether you input is empty.
	 */
	public boolean Check_name(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * To verify student's scores.It's similar to checking student numbers.If string
	 * with non-numeric characters was entered,this method will return false.Due to
	 * the score is between 0 and 100; so this score must be not out of range.
	 */
	public boolean Check_score(String score) {
		if (score == null || score.isEmpty()) {
			return false;
		} else {
			try {
				int result = Integer.parseInt(score);
				if (result < 0 || result > 100) {
					return false;
				}
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}

	/**
	 * This method is used to check whether the file exists, and to check whether
	 * the file extension is "dat".If file is not exists or extension is not "dat"
	 * ,this method will return false;
	 */
	public boolean Check_File(String path) {
		File file = new File(path);
		if (file.exists() && path.endsWith(".dat")) {
			return true;
		} else {
			return false;
		}
	}
}