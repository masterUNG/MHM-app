package com.su.lapponampai_w.mhm_app_beta1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutMHMActivity extends AppCompatActivity {

    //Explicit
    TextView textViewH1,textViewD1,textViewH2, textViewD2, textViewD3,
            textViewH4,textViewD4,textViewH5,textViewD5,textViewH6,textViewD6;
    String stringH1,stringD1,stringH2,stringD2,stringD3,stringH4,stringD4,stringH5,stringD5,stringH6,stringD6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_mhm);

        bindWidget();

        setText_AboutMHMActivity();

        textViewH1.setText(stringH1);

        textViewD1.setText(stringD1);

        textViewH2.setText(stringH2);

        textViewD2.setText(stringD2);

        textViewD3.setText(stringD3);

        textViewH4.setText(stringH4);

        textViewD4.setText(stringD4);

        textViewH5.setText(stringH5);

        textViewD5.setText(stringD5);

        textViewH6.setText(stringH6);

        textViewD6.setText(stringD6);


    }

    private void setText_AboutMHMActivity() {

        stringH1 = "เกี่ยวกับ MHM Application";

        stringD1 = "         MHM Application เป็นแอพพลิเคชั่น ที่ใช้ช่วยจัดการด้านยา และการรับประทานยาด้วย" +
                "ตนเองของผู้ใช้ทั่วไป มีระบบการแจ้งเตือนการรับประทานยา และการให้ข้อมูลด้านยา";

        stringH2 = "ข้อควรระวังและคำแนะนำ";

        stringD2 = "         แอพพลิเคชั่นนี้ อาจมีข้อมูลด้านการทานยา และการเกิดปฏิกิริยาระหว่างยาเพียงบางส่วน " +
                "ซึ่งอาจไม่ครอบคลุมทั้งหมด ผู้ใช้โปรแกรมไม่สามารถนำ แอพพลิเคชั่นนี้ไปใช้เพื่อเป็นแหล่งอ้างอิง" +
                " หรือเรียกร้องกล่าวหาผู้จัดทำว่ามีสารสนเทศไม่ครบถ้วนมิได้ หากผู้ใช้มีข้อสงสัยในข้อมูลยาโปรดติดต่อ" +
                "แพทย์หรือเภสัชกรโดยตรง หรือสอบถามได้ที่ MHMApp@gmail.com";

        stringD3 = "         พบปัญหา หรือมีข้อแนะนำเกี่ยวกับ การพัฒนาแอพพลิเคชั่น กรุณาส่งมาที่ MHMApp@gmail.com";

        stringH4 = "บันทึกการปรับรุ่น";

        stringD4 = "         1.0 เป็นรุ่นที่เผยแพร่ในวงจำกัดเพื่อทดสอบความเป็นไปได้ในการใช้งานโปรแกรมประยุกต์บนมือถือ";

        stringH5 = "แอพพลิเคชั่นนี้ถูกพัฒนาโดย";

        stringD5 = "         ภาควิชาสารสนเทศศาสตร์ทางสุขภาพ คณะเภสัชศาสตร์ มหาวิทยาลัยศิลปากร";

        stringH6 = "ขอขอบคุณ : ";

        stringD6 = "    - ภก.ผศ.ดร.พีรยศ ภมร.... ที่ปรึกษาโครงการ\n" +
                "    - อ. ....... ที่ปรึกษาด้านเทคนิก\n" +
                "    - ภก. ไพโรจน์ สัจจาวิรุกิจ ที่ปรึกษาด้านข้อมูลยา";





    }

    private void bindWidget() {

        textViewH1 = (TextView) findViewById(R.id.textView155);
        textViewD1 = (TextView) findViewById(R.id.textView161);
        textViewH2 = (TextView) findViewById(R.id.textView162);
        textViewD2 = (TextView) findViewById(R.id.textView164);
        textViewD3 = (TextView) findViewById(R.id.textView165);
        textViewH4 = (TextView) findViewById(R.id.textView166);
        textViewD4 = (TextView) findViewById(R.id.textView167);
        textViewH5 = (TextView) findViewById(R.id.textView168);
        textViewD5 = (TextView) findViewById(R.id.textView169);
        textViewH6 = (TextView) findViewById(R.id.textView170);
        textViewD6 = (TextView) findViewById(R.id.textView171);
    }


}
