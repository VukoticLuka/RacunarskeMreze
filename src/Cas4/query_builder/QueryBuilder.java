package Cas4.query_builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryBuilder {
    private StringBuffer query;
    private boolean hasQuery;

    public QueryBuilder(String baseUrl){
        this.query = new StringBuffer(baseUrl);
    }

    public void append(String name, String value){
        this.query.append(this.hasQuery ? '&' : '?');
        this.hasQuery = true;
        this.encode(name,value);
    }



    private void encode(String name, String value){
        this.query.append(URLEncoder.encode(name, StandardCharsets.UTF_8));
        this.query.append('=');
        this.query.append(URLEncoder.encode(value,StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return this.query.toString();
    }

    public URL toUrl() throws MalformedURLException {
        return new URL(this.toString());
    }
}
