package scripts.com.mercosur.slayer.util;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;

public class Sleep {

	public static boolean conditionalSleep(Condition condition, int minimumBreak, int maximumBreak) {
		return Timing.waitCondition(condition, General.random(minimumBreak, maximumBreak));
	}
}
