package edu.sjsu.mithai.config;

import edu.sjsu.mithai.util.StoppableRunnableTask;

import java.io.File;
import java.io.IOException;

/**
 * Task which checks if configuration file is updated. If updated, reloads it.
 * <p>
 * TODO: Propagate exceptions correctly
 */
public class ConfigMonitorTask extends StoppableRunnableTask {

    private static final int CHECKING_INTERVAL = 10000;

    private File file;
    private long lastModified;
    private Configuration config;

    public ConfigMonitorTask(Configuration config) {
        this.config = config;
        this.file = new File(config.getPropertyFile());
        this.lastModified = file.lastModified();
    }

    @Override
    public void execute() {

        System.out.println("Checking if file is changed..");

        long modified = file.lastModified();
        if (modified != lastModified) {
            lastModified = modified;
            try {
                onChange();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Waiting till checking interval till rechecking if file is modified
        try {
            Thread.sleep(CHECKING_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void onChange() throws IOException {
        config.reload();
    }
}