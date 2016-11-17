package swim.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import swim.entities.ClubTest;
import swim.entities.MarkTest;
import swim.entities.SwimmerTest;
import swim.entities.SwimmingPoolTest;


@SuiteClasses({
	
	ClubTest.class,
	MarkTest.class,
	SwimmerTest.class,
	SwimmingPoolTest.class
})

@RunWith(Suite.class)
public class EntitiesSuite {}
