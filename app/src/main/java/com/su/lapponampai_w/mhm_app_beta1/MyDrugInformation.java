package com.su.lapponampai_w.mhm_app_beta1;

/**
 * Created by apple on 7/5/16.
 */
public class MyDrugInformation {
        //เอาไว้ใส่ข้อมูลยาแบบ Offline

    public String[] receiveInformation(String med_Id) {
        String[] strREAD = new String[5];

        if (med_Id.equals("2")) {

            strREAD[0] = "Efavirenz";
            strREAD[1] = "Stocrin\nEfavirenz GPO";
            strREAD[2] = "- รับประทานยาตรงเวลา วันละ 1 ครั้ง ถ้าไม่ตรงเวลา บวกลบไม่เกิน 30 นาทีเพื่อป้องกันการดื้อยา" +
                    "\n- แนะนำให้รับประทานยาท้องว่างหลังอาหาร 2 ชั่วโมงหรือก่อนนอน" +
                    "\n- หลีกเลี่ยงอาหารไขมันสูงในมื้อก่อนรับประทานยา เพื่อลดอาการข้างเคียง" +
                    "\n- อาการข้างเคียงระยะสั้น (หลังเริ่มรับประทานยาครั้งแรกไม่เกิน 6 เดือน)" +
                    "\n     + คลื่นไส้" +
                    "\n     + อาเจียน" +
                    "\n     + เวียนศีรษะ " +
                    "\n     + มึนศีรษะ" +
                    "\n     + ฝันร้าย" +
                    "\n     + สับสน" +
                    "\n     + ผื่นขึ้น คัน มีไข้ ปวดเมื่อยตามเนื้อตัว คลื่นไส้ อาเจียน ปวดข้อ เจ็บคอร่วมกับผื่นแดงเป็นจุด" +
                    "เล็กและเป็นปื้นใหญ่ตรงกลาง ต่อมาเป็นผื่นพุพองและแตกบริเวณเยื่อบุช่องปาก ตา หรืออวัยวะเพศ และอาจ" +
                    "หลุดลอกเป็นแผ่น (ผื่นรุนแรง)" +
                    "\n- อาการข้างเคียงระยะยาว (หลังเริ่มรับประทานยาครั้งแรกนานเกิน 6 เดือน)" +
                    "\n     + ภาวะเต้านมโต" +
                    "\n- อาการที่ต้องรีบกลับมาพบแพทย์โดยเร็วที่สุด ห้ามหยุดยาเอง : ผื่นรุนแรง" +
                    "\n- ห้ามรับประทานร่วมกับยาแก้ปวดไมเกรนกลุ่ม ergotamine เช่น Cafergot, Avamigran, " +
                    "Neuramizone และยาแก้ปวดท้อง Cisapride (Cipasid, Cisaride) ";
            strREAD[3] = "Ya & You ยากับคุณ;http://www.yaandyou.net/index.php/component/drug/?nsetidT=1372&drugname=EFAVIRENZ+TABLETS+600+MG&drugtype=t";
            strREAD[4] = "AIDSinfo;https://aidsinfo.nih.gov/drugs/269/efavirenz/0/patient";

        } else {
            strREAD[0] = "";
            strREAD[1] = "";
            strREAD[2] = "";
            strREAD[3] = "";
            strREAD[4] = "";
        }


        return strREAD;
    }

} //Main Class
