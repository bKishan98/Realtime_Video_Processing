package bibhu.kishan.kafka.project;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class WebcamProducer {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String rawVideoTopic = "rawVideoTopic";
        Producer<String, byte[]> producer = KafkaProducerInstance.createProducer(); // Instantiate your Kafka producer

        VideoCapture videoCapture = new VideoCapture(0); // 0 corresponds to the default webcam

        try {
            if (!videoCapture.isOpened()) {
                System.err.println("Error: Could not open webcam.");
                return;
            }

            while (true) {
                Mat frame = new Mat();
                videoCapture.read(frame);

                // Convert the frame to byte array
                MatOfByte matOfByte = new MatOfByte();
                Imgcodecs.imencode(".jpg", frame, matOfByte);

                byte[] imageData = matOfByte.toArray();

                // Produce the raw video data to Kafka
                ProducerRecord<String, byte[]> record = new ProducerRecord<>(rawVideoTopic, "key", imageData);
                producer.send(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            videoCapture.release(); // Close the VideoCapture object
            producer.close();       // Close the Kafka producer
        }
    }
}



