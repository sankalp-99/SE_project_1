import java.util.Vector;
import java.io.*;

public class drive {

	public static void main(String[] args) {

		UserConfiguration uc=new UserConfiguration();

		int numLanes = uc.getNickName();
		int maxPatronsPerParty=uc.getFull();

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
