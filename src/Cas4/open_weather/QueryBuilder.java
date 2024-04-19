package Cas4.open_weather;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Scanner;

public class QueryBuilder {
    private final AllowedParams allowedParams;
    private final String endPoint;

    public QueryBuilder(String[] params,String endPoint) throws RuntimeException{
        this.allowedParams = new AllowedParams(params);
        this.endPoint = endPoint;

        System.out.println("Please enter values for the following parameters:");

        try(Scanner in = new Scanner(System.in)){
            for(String param : params){
                System.out.println(MessageFormat.format("\"{0}\": ",param));
                allowedParams.setParam(param, in.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void setParamValue(String param, String value){
        try{
            this.allowedParams.setParam(param,value);
        }catch (Exception e){
            System.err.println("Unsupported parameter!");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.endPoint + this.allowedParams.makeQuery();
    }

    public URL toUrl() throws MalformedURLException {
        return new URL(this.toString());
    }
}
