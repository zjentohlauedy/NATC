package natc.utility;

public class Utility {

	public static double roll( double range ) {

		double x = 0.0;

		/*
		 ** This function generates a random number on a pseudo-bell curve by
		 ** rolling 3 times and dividing by 3.
		 */

		for ( int i = 0; i < 3; ++i ) {
			
			x += Math.random() * range;
		}

		x /= 3.0;

		return x;
	}
	
	public static double rollPerfect( double range ) {
	
		double x = 0.0;
		
		x = roll( range );
		
		if ( Math.ceil( x ) == Math.ceil( range ) ) {
			
			return range + (range * Math.random());
		}
		
		return x;
	}
}
