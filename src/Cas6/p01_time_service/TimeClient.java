package Cas6.p01_time_service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeClient {
    //We are using TIME protocol here (port 37)
    public static void main(String[] args) {
        String hostname = "time.nist.gov";

        TimeZone gmt = TimeZone.getTimeZone("GMT");
        //Create calendar
        Calendar epoch1900 = Calendar.getInstance(gmt);
        //set it to point in time
        epoch1900.set(1900,Calendar.JANUARY,1,0,0);
        //Get number of miliseconds since that point in time
        long epoch1900ms = epoch1900.getTimeInMillis();
        //Do same for the other point
        Calendar epoch1970 = Calendar.getInstance(gmt);
        epoch1970.set(1970,Calendar.JANUARY,1,0,0);

        long epoch1970ms = epoch1970.getTimeInMillis();

        long epochDifferenceInMils = epoch1970ms - epoch1900ms;
        //this is how we get that magic number in SERVER
        long epochDiffInSeconds = epochDifferenceInMils/1000;

        System.out.println(epochDiffInSeconds);

        try (Socket socket = new Socket(hostname,37);){
            //WE RECIEVE BYTE BY BYTE
            InputStream raw = new BufferedInputStream(socket.getInputStream());

            long secondsSince1900 = 0;

            for(int i = 0; i < 4; i++){
                secondsSince1900 = (secondsSince1900 << 8) | raw.read();
            }


            // Now we can calculate current time
            long secondsSince1970 = secondsSince1900 - epochDiffInSeconds;
            long millisecondsSince1970 = secondsSince1970 * 1000;

            Date now = new Date(millisecondsSince1970);

            System.out.println("It is " + now + " at " + hostname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
