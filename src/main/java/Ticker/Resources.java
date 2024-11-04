package Ticker;

import java.util.ArrayList;
import java.util.List;

public class Resources {

    private boolean[] resourceslinks;
    private List<String> resourceslinksnames;

    public Resources(DataConfig listOfSockets) {

        this.resourceslinks = new boolean[DataConfig.getLinkCount()];
        this.resourceslinksnames = new ArrayList<>(listOfSockets.getLinksNames());
    }
}
