import java.util.Vector;
import java.io.*;

public class Drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=2;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
