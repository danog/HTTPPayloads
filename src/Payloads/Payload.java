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
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author root
 */
public abstract class Payload {

    public Hashtable<String, String> headers = new Hashtable<>();
    protected String protocol;
    protected String protocolVersion;

    protected String payload = "";
    protected Boolean keepalive = false;

    
    
    public String getProtocol() {
        return protocol;
    }
    
    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getPayload() {
        return payload;
    }
    
    public Boolean shouldKeepAlive() {
        return keepalive;
    }
    

    public Payload setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }
    
    public Payload setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }
    
    public Payload setPayload(String protocol) {
        this.protocol = protocol;
        return this;
    }
    
    public Payload shouldKeepAlive(Boolean keepalive) {
        this.keepalive = keepalive;
        return this;
    }

    
    

    /**
     * Method used for parsing first line, different for requests and responses
     * @param line
     */
    protected abstract void parseFirstLine(String line);
    
    /**
     * Method used for getting first line, different for requests and responses
     * @return 
     */
    protected abstract String getFirstLine();
    
    
    /**
     * Constructor that parses request or response
     * @param input
     * @throws IOException
     */
    
    protected void fromString(BufferedReader input) throws IOException {
        parseFirstLine(input.readLine());
        
        String[] temp;
        String payloadtemp;
        int length;
        
        while (!(payloadtemp = input.readLine()).isEmpty()) {
            temp = payloadtemp.split(":", 2);
            headers.put(temp[0].trim().toLowerCase(), temp[1].trim().toLowerCase());
        }
        
        if (headers.containsKey("content-length")) {
            length = Integer.parseInt(headers.get("content-length"));
            char[] buffer = new char[length];
            input.read(buffer, 0, buffer.length);
            payload = String.copyValueOf(buffer);
        }
        if (headers.containsKey("connection")) {
            keepalive = "keep-alive".equals(headers.get("connection"));
        }
    }
    
    /**
     * Method used for retrieving string version of request
     * @return Data to write to socket
     */
    @Override
    public String toString() {
        String res = getFirstLine()+"\r\n";
        
        headers.put("content-length", String.valueOf(payload.length()));
        headers.put("connection", keepalive ? "keep-alive" : "close");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            res += key+": "+value+"\r\n";
        }
        return res+"\r\n"+payload;
    }
    
    public void write(BufferedWriter output) throws IOException {
        output.write(this.toString());
        output.flush();
    }
    
}
