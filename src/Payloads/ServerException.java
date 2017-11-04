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

import Payloads.RequestPayload;
import Payloads.ResponsePayload;


/**
 *
 * @author root
 */
public class ServerException extends Exception {
    private ResponsePayload response;
    public ServerException(RequestPayload request, int code, String description) {
        super(description);
        response = new ResponsePayload(request, code, description, description);
    }
    
    public ServerException(RequestPayload request, int code, String description, String payload) {
        super(description);
        response = new ResponsePayload(request, code, description, payload);
    }

    public ServerException(ResponsePayload response) {
        this.response = response;
    }
    public ResponsePayload getPayload() {
        return response;
    }
}
