/* 
 * Copyright (C) 2017 Daniil Gentili
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Payloads;



import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Daniil Gentili
 */
public class RequestPayload extends Payload {
    
    private String URI;
    private String method;

    
    public String getMethod() {
        return method;
    }
    public String getURI() {
        return URI;
    }
    public Payload setMethod(String method) {
        this.method = method;
        return this;
    }
    public Payload setURI(String URI) {
        this.URI = URI;
        return this;
    }
    
    
    public RequestPayload(BufferedReader input) throws IOException {
        fromString(input);
    }

    public RequestPayload(String method, String URI, String payload) {
        this.protocol = "HTTP";
        this.protocolVersion = "1.1";
        this.keepalive = false;
        
        this.method = method;
        this.URI = URI;
        this.payload = payload;
    }
    public RequestPayload(String method, String URI) {
        this.protocol = "HTTP";
        this.protocolVersion = "1.1";
        this.keepalive = false;
        
        this.method = method;
        this.URI = URI;        
    }
    
    protected String getFirstLine() {
        return method+" "+URI+" "+protocol+"/"+protocolVersion;
    }
    protected void parseFirstLine(String line) {
        String[] temp;

        temp = line.split(" ");
        method = temp[0].trim();
        URI = temp[1].trim();
        
        temp = temp[2].trim().split("/");
        protocol = temp[0];
        protocolVersion = temp[1];
    }
}
