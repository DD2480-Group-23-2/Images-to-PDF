package swati4star.createpdf.function;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.test.rule.ActivityTestRule;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import org.junit.Rule;
import org.junit.Test;


import swati4star.createpdf.R;
import swati4star.createpdf.activity.MainActivity;


public class ParseQRCodeMockTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    public String parseQRCode(Drawable drawable) {
        try {
            // read Bitmap from file
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // convert Bitmap into 2d-array
            int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            // scan QR code by Zxing
            Result result = new QRCodeReader().decode(binaryBitmap);
            String qrCodeContent = result.getText();

            // get decode result
            return  ("Result: " + qrCodeContent);

        } catch (NotFoundException e) {
            e.printStackTrace();
            System.out.println("Not found QR code");
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void mockTest() {

        Resources r = mActivityRule.getActivity().getApplicationContext().getResources();
        Drawable drawable = r.getDrawable(R.drawable.test);

        String rsl = parseQRCode(drawable);

        System.out.println(rsl);

    }



}
