/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  dewddtran.tr
 *  dprint.r
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dewddgravity;

import dewddgravity.DigEventListener2;
import dewddgravity.Gravity;
import dewddgravity.TheJobType;
import dewddtran.tr;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

class MainLoop
implements Runnable {
	public static TheJobType jobs = new TheJobType();

	public static long lostTime = 0;
	public static long maxTime = 50;

	public MainLoop() {

	}

	@Override
	public void run() {
		// always get Item from jobs

		maxTime = (long) tr.gettrint("CONFIG_GRAVITY_MAXTIME_DO_THE_JOB");
		if (maxTime <= 0) {
			maxTime = 50;
		}
		/*
		Gravity.stick = (int) tr.gettrint("CONFIG_GRAVITY_STICKY_RADIUS");
		if (Gravity.stick <= 0) {
			Gravity.stick = 5;
		}*/

		long startTime = System.currentTimeMillis();

		// dprint.r.printAll("Recall " + MainLoop.jobs.getSize() + " " +
		// MainLoop.lostTime);

		if (MainLoop.lostTime >= maxTime) {
			MainLoop.lostTime -= maxTime;

			if (MainLoop.lostTime < 0) {
				MainLoop.lostTime = 0;
			}

			/*
			 * if (MainLoop.jobs.getSize() > 0) { dprint.r.printAll("(( sleep "
			 * + MainLoop.jobs.getSize() + " " + MainLoop.lostTime); }
			 */
			return;
		} 

		while (MainLoop.jobs.getSize() > 0 && lostTime < maxTime) {
			// dprint.r.printAll("done size " + " , " + jobs.getSize());
			//dprint.r.printC("job size = " + MainLoop.jobs.getSize() + " , losttime = " + lostTime);
			if (MainLoop.lostTime > maxTime) {
				// dprint.r.printAll("__ break cuz time out " +
				// (MainLoop.lostTime));
				return;
			}

			Location loc = MainLoop.jobs.get();

			if (loc != null) {

				Block blo = loc.getBlock();
				if (Gravity.needBlock(blo) == false) {
					continue;
				}

				Gravity noop = new Gravity(blo, null, 1);
				Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, noop);

			}

		}
		
		

	}
}
