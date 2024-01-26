package bibhu.kishan.kafka.project;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class EnrichmentUtils {

    public static byte[] applyEnrichment(byte[] rawImageData) {
        // Convert byte array to OpenCV Mat
        MatOfByte matOfByte = new MatOfByte(rawImageData);
        Mat rawFrame = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_UNCHANGED);

        // Apply grayscale transformation
        Mat enrichedFrame = new Mat();
        Imgproc.cvtColor(rawFrame, enrichedFrame, Imgproc.COLOR_BGR2GRAY);

        // Convert the enriched frame back to a byte array
        MatOfByte enrichedMatOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", enrichedFrame, enrichedMatOfByte);

        return enrichedMatOfByte.toArray();
    }
}

