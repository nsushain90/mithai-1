package edu.sjsu.mithai.util;

import edu.sjsu.mithai.sensors.TemperatureSensor;

public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            TaskManager.getInstance().submitTask(new TemperatureSensorTask(i));
        }

//        for (int i = 0; i < 5; i++) {
//            TaskManager.getInstance().submitTask(new KafkaExporterTask("KAFKA"));
//        }

        try {
            Thread.sleep(31000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping tasks...");
        try {
            TaskManager.getInstance().stopAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class TemperatureSensorTask extends StoppableExecutableTask {

        TemperatureSensor sensor;

        public TemperatureSensorTask(int id) {
            this.sensor = new TemperatureSensor("sensor-" + String.valueOf(id));
        }

        @Override
        public void execute() {
            System.out.println(sensor.getId() + "=>" + sensor.sense());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    static class KafkaExporterTask extends StoppableExecutableTask {
//
//        IExporter exporter;
//        Configuration configuration;
//
//        public KafkaExporterTask(String type) throws IOException {
//
//            exporter = ExporterFactory.getExporter();
//            try {
//                exporter.setup();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void execute() {
//            try {
//                exporter.send();
//                Thread.sleep(15000);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            try {
//                exporter.tearDown();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
