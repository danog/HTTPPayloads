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
 * @author root
 */
public class ResponsePayload extends Payload {
    private int responseCode;
    private String responseDescription;
    
    public ResponsePayload(BufferedReader input) throws IOException {
        fromString(input);
    }
    
    public ResponsePayload(int responseCode, String responseDescription) {
        this.protocol = "HTTP";
        this.protocolVersion = "1.1";
        this.keepalive = false;
        
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        
    }
    public ResponsePayload(RequestPayload request, int responseCode, String responseDescription) {
        this.protocol = request.getProtocol();
        this.protocolVersion = request.getProtocolVersion();
        this.keepalive = request.shouldKeepAlive();
        
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        
    }
    public ResponsePayload(RequestPayload request, int responseCode, String responseDescription, String responsePayload) {
        this.protocol = request.getProtocol();
        this.protocolVersion = request.getProtocolVersion();
        this.keepalive = request.shouldKeepAlive();
        
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.payload = responsePayload;
    }
    public Integer getResponseCode() {
        return responseCode;
    }
    public String getResponseDescription() {
        return responseDescription;
    }
    @Override
    protected String getFirstLine() {
        return protocol+"/"+protocolVersion+" "+responseCode+" "+responseDescription;
    }

    @Override
    protected void parseFirstLine(String line) {
        String[] temp;

        temp = line.split(" ", 3);
        responseCode = Integer.parseInt(temp[1].trim());
        responseDescription = temp[2];
        
        temp = temp[0].trim().split("/");
        protocol = temp[0];
        protocolVersion = temp[1];
    }
}
