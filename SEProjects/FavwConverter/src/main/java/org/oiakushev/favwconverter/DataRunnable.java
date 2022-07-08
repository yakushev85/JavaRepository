package org.oiakushev.favwconverter;

import org.oiakushev.favwconverter.model.ConvertStatus;
import org.oiakushev.favwconverter.model.ConvertingType;
import org.oiakushev.favwconverter.model.FileToConvert;

import java.io.IOException;
import java.util.List;

public class DataRunnable implements Runnable {
    private final ConvertingType convertingType;
    private final String pathTo;
    private final List<FileToConvert> filesToConvert;
    private MainController mainController;

    public DataRunnable(ConvertingType convertingType, String pathTo, List<FileToConvert> filesToConvert) {
        this.convertingType = convertingType;
        this.pathTo = pathTo;
        this.filesToConvert = filesToConvert;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        for (FileToConvert fileToConvert : filesToConvert) {
            if (fileToConvert.getStatus() != ConvertStatus.COMPLETED) {
                fileToConvert.setStatus(ConvertStatus.CONVERTING);
                mainController.updateData();

                String genCmd = convertingType.generateCmd(
                        fileToConvert.getFile().toString(),
                        fileToConvert.getFile().getName(),
                        pathTo);
                System.out.println(genCmd);

                try {
                    Process processConverting = Runtime.getRuntime().exec(genCmd);
                    processConverting.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    mainController.handleError(e.getMessage());

                    return;
                }

                fileToConvert.setStatus(ConvertStatus.COMPLETED);
                mainController.updateData();
            }
        }
    }
}
