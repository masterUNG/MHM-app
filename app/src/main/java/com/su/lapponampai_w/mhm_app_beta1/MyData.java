package com.su.lapponampai_w.mhm_app_beta1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 5/1/16.
 */
public class MyData {

    public String[] translate_uomArray(String[] uom) {

        String[] strRead = new String[uom.length];

        for (int i = 0; i < uom.length ; i++) {

            if (uom[i].equals("1")) {
                strRead[i] = "mg";
            } else if (uom[i].equals("2")) {
                strRead[i] = "cc";
            } else if (uom[i] == null || uom[i].equals("")) {
                strRead[i] = "N/A";
            }
        }
        return strRead;
    } //translate_uom


    public String translate_EA(String ea) {
        String strREAD = null;

        if (ea.equals("1")) {
            strREAD = "เม็ด";
        }

        return strREAD;
    }

    public String translate_uom(String uom) {

        String strREAD = "N/A";
        if (uom.equals("1")) {
            strREAD = "mg";
        } else if (uom.equals("2")) {
            strREAD = "cc";
        }

        return strREAD;
    }

    public String translate_Which_Date_D(String which_date_d) {
        String strREAD = null;

        if (which_date_d.equals("ED:0")) {
            strREAD = "รับประทานยาทุกวัน";
        } else if (which_date_d.equals("ED:1")) {
            strREAD = "รับประทานยาวันเว้นวัน";
        } else if (which_date_d.equals("ED:2")) {
            strREAD = "รับประทานยา 1 วันเว้น 2 วัน(ทุก 3 วัน)";
        } else if (which_date_d.equals("ED:3")) {
            strREAD = "รับประทานยา 1 วันเว้น 3 วัน(ทุก 4 วัน)";
        } else if (which_date_d.equals("ED:4")) {
            strREAD = "รับประทานยา 1 วันเว้น 4 วัน(ทุก 5 วัน)";
        } else if (which_date_d.equals("ED:5")) {
            strREAD = "รับประทานยา 1 วันเว้น 5 วัน(ทุก 6 วัน)";
        } else {
            String[] queryDay = which_date_d.split(":");
            String[] queryDetailDay = queryDay[1].split(",");
            if (queryDay[0].equals("DOM")) {
                StringBuilder stringBuilder = new StringBuilder("วันที่ : ");
                for(int i = 0;i<queryDetailDay.length;i++) {
                    stringBuilder.append(queryDetailDay[i]);
                    stringBuilder.append(", ");
                }
                String s = stringBuilder.toString();
                s = s.substring(0, s.length() - 2);
                s = s.concat(" ของทุกเดือน");
                strREAD = s;
            }

            if (queryDay[0].equals("DOW")) {
                final String[] strings = {"วันอาทิตย์", "วันจันทร์", "วันอังคาร", "วันพุธ", "วันพฤหัสบดี", "วันศุกร์", "วันเสาร์"};
                StringBuilder stringBuilder = new StringBuilder();
                int x;
                for(int i = 0;i<queryDetailDay.length;i++) {
                    x = Integer.parseInt(queryDetailDay[i]);
                    x = x - 1;

                    stringBuilder.append(strings[x]);
                    stringBuilder.append(", ");
                }
                String s = stringBuilder.toString();
                s = s.substring(0, s.length() - 2);
                s = s.concat(" ของทุกสัปดาห์");
                strREAD = s;

            }

        }


        return strREAD;
    }

    /*
    public String[] translate_Which_Date_D(String which_date_d) {
        String[] strREAD = null;
        strREAD = new String[2];

        if (which_date_d.equals("ED:0")) {
            strREAD[0] = "1";
            strREAD[1] = "ทานยาทุกวัน";

        }

        return strREAD;
    }
    */

    public int[] translate_Appearance_News(String[] appearance_News) {
        int[] intsRead = new int[appearance_News.length];

        for (int i = 0; i < appearance_News.length; i++) {
            if (appearance_News[i].equals("N1")) {
                intsRead[i] = R.drawable.news;
            }
        }

        return intsRead;
    }


    public int[] translate_Appearance(String[] appearance) {

        int[] intsRead = new int[appearance.length];
        for (int i = 0; i < appearance.length; i++) {
            if (appearance[i].equals("1")) {
                intsRead[i] = R.drawable.img0101;
            } else if (appearance[i].equals("2")) {
                intsRead[i] = R.drawable.img0102;
            } else if (appearance[i].equals("3")) {
                intsRead[i] = R.drawable.img0201;
            } else if (appearance[i].equals("4")) {
                intsRead[i] = R.drawable.img0302;
            } else if (appearance[i].equals("5")) {
                intsRead[i] = R.drawable.img0203;
            } else if (appearance[i].equals("6")) {
                intsRead[i] = R.drawable.img0501;
            } else if (appearance[i].equals("icon_question")) {
                intsRead[i] = R.drawable.icon_question;
            } else if (appearance[i].equals("7")) {
                intsRead[i] = R.drawable.img90506;
            } else if (appearance[i].equals("8")) {
                intsRead[i] = R.drawable.img0604;
            } else if (appearance[i].equals("9")) {
                intsRead[i] = R.drawable.img0607;
            } else if (appearance[i].equals("10")) {
                intsRead[i] = R.drawable.img0602;
            }
        }
        return intsRead;
    }


    public int[] translate_Small_Appearance(String[] appearance) {

        int[] intsRead = new int[appearance.length];
        for (int i = 0; i < appearance.length; i++) {
            if (appearance[i].equals("1")) {
                intsRead[i] = R.drawable.ib0101;
            } else if (appearance[i].equals("2")) {
                intsRead[i] = R.drawable.ib0102;
            } else if (appearance[i].equals("3")) {
                intsRead[i] = R.drawable.ib0201;
            } else if (appearance[i].equals("4")) {
                intsRead[i] = R.drawable.ib0302;
            } else if (appearance[i].equals("5")) {
                intsRead[i] = R.drawable.ib0203;
            } else if (appearance[i].equals("6")) {
                intsRead[i] = R.drawable.ib0501;
            } else if (appearance[i].equals("7")) {
                intsRead[i] = R.drawable.ib90506;
            } else if (appearance[i].equals("8")) {
                intsRead[i] = R.drawable.ib0604;
            } else if (appearance[i].equals("9")) {
                intsRead[i] = R.drawable.ib0607;
            } else if (appearance[i].equals("10")) {
                intsRead[i] = R.drawable.ib0602;
            }
        }
        return intsRead;
    }


    public int[] translate_Smallate_Appearance(String[] appearance) {

        int[] intsRead = new int[appearance.length];
        for (int i = 0; i < appearance.length; i++) {



            if (appearance[i].equals("1")) {
                intsRead[i] = R.drawable.ibc0101;
            } else if (appearance[i].equals("2")) {
                intsRead[i] = R.drawable.ibc0102;
            } else if (appearance[i].equals("3")) {
                intsRead[i] = R.drawable.ibc0201;
            } else if (appearance[i].equals("4")) {
                intsRead[i] = R.drawable.ibc0302;
            } else if (appearance[i].equals("5")) {
                intsRead[i] = R.drawable.ibc0203;
            } else if (appearance[i].equals("6")) {
                intsRead[i] = R.drawable.ibc0501;
            } else if (appearance[i].equals("7")) {
                intsRead[i] = R.drawable.ibc90506;
            } else if (appearance[i].equals("8")) {
                intsRead[i] = R.drawable.ibc0604;
            } else if (appearance[i].equals("9")) {
                intsRead[i] = R.drawable.ibc0607;
            } else if (appearance[i].equals("10")) {
                intsRead[i] = R.drawable.ibc0602;
            }

        }
        return intsRead;
    }


    public int[] translate_SmallSkipHold_Appearance(String[] appearance) {

        int[] intsRead = new int[appearance.length];
        for (int i = 0; i < appearance.length; i++) {

            if (appearance[i].equals("1")) {
                intsRead[i] = R.drawable.ibs0101;
            } else if (appearance[i].equals("2")) {
                intsRead[i] = R.drawable.ibs0102;
            } else if (appearance[i].equals("3")) {
                intsRead[i] = R.drawable.ibs0201;
            } else if (appearance[i].equals("4")) {
                intsRead[i] = R.drawable.ibs0302;
            } else if (appearance[i].equals("5")) {
                intsRead[i] = R.drawable.ibs0203;
            } else if (appearance[i].equals("6")) {
                intsRead[i] = R.drawable.ibs0501;
            } else if (appearance[i].equals("7")) {
                intsRead[i] = R.drawable.ibs90506;
            } else if (appearance[i].equals("8")) {
                intsRead[i] = R.drawable.ibs0604;
            } else if (appearance[i].equals("9")) {
                intsRead[i] = R.drawable.ibs0607;
            } else if (appearance[i].equals("10")) {
                intsRead[i] = R.drawable.ibs0602;
            }

        }
        return intsRead;
    }

    public int[] translate_Star_Appearance(String[] stringsStar) {
        int[] intsRead = new int[stringsStar.length];

            for(int i = 0;i < stringsStar.length;i++) {
                if (stringsStar[i].equals("G")) {
                    intsRead[i] = R.drawable.star_gold;
                } else if (stringsStar[i].equals("S")) {
                    intsRead[i] = R.drawable.star_silver;
                } else if (stringsStar[i].equals("W")) {
                    intsRead[i] = R.drawable.star_blank;
                } else if (stringsStar[i].equals("B")) {
                    intsRead[i] = R.drawable.star_black;
                } else if (stringsStar[i].equals("N")) {
                    intsRead[i] = R.drawable.star_none;
                }
            }

        return intsRead;
    }



    public String createStringTime(int hour,int minute) {

        String strHour, strMinute, strTime;

        strHour = Integer.toString(hour);
        strMinute = Integer.toString(minute);

        if (strHour.length() == 1) {
            strHour = "0".concat(strHour);
        }
        if (strMinute.length() == 1) {
            strMinute = "0".concat(strMinute);
        }
        strTime = strHour.concat(":");
        strTime = strTime.concat(strMinute);

        return strTime;
    }

    public String createStringDay(int day,int month,int year) {

        String strDay, strMonth, strYear,strDateMonthYear;
        strDay = Integer.toString(day);
        strMonth = Integer.toString(month);

        strYear = Integer.toString(year);

        if (strDay.length() == 1) {
            strDay = "0".concat(strDay);
        }
        if (strMonth.length() == 1) {
            strMonth = "0".concat(strMonth);
        }

        strDateMonthYear = strDay + "/" + strMonth + "/" + strYear;

        return strDateMonthYear;
    }

    public String currentDay() {
        String today;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        today = dateFormat.format(System.currentTimeMillis());
        return today;
    }

    public String currentDateTime() {
        String today;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        today = dateFormat.format(System.currentTimeMillis());
        return today;
    }

    public String currentDateTime_Withoutsecond() {
        String today;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        today = dateFormat.format(System.currentTimeMillis());
        return today;
    }


    public String currentTime_Minus() {
        String today;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        today = dateFormat.format(System.currentTimeMillis());
        return today;
    }

    public String current_DayOfMonth() {

        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_MONTH);

        return Integer.toString(i);
    }

    public String current_DayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        return Integer.toString(i);
    }

    public Date stringChangetoDateWithOutTime(String strDate) {
        //ใช้ได้ในกรณี strDate เป็น dd/MM/yyyy เท่านั้นไม่รวม เวลา
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public Date stringChangetoDate(String strDate) {
        //ใช้ได้ในกรณี strDate เป็น dd/MM/yyyy
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public Date stringChangetoTime_Minute(String strDate) {
        //ใช้ได้ในกรณี strDate เป็น dd/MM/yyyy เท่านั้นไม่รวม เวลา
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public String string_ddMMyyyy_ConvertedFromSpecificDate(Date date) {
        //ใช้เมื่อต้องการเปลี่ยน Date เป็น String
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }





    /*
    public String[] stringsNews(String[] med_id) {

        String[] stringsREAD = new String[med_id.length];
        for (int i = 0;i<med_id.length;i++) {

            if (med_id[i].equals("1")) {
                stringsREAD[i] = "เนื้อหาความรู้เกี่ยวกับยา Efaviren GPO";
            } else if (med_id[i].equals("2")) {
                stringsREAD[i] = "เนื้อหาความรู้เกี่ยวกับยา Stocrin";
            }
        }
        return stringsREAD;

    }  //stringsNews

    */


} //Main class

