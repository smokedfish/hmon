package org.hmon.server;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.utils.Exceptions;

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        String trace = Exceptions.getStackTraceAsString(ex);
        return Response.status(500).entity(trace).type("text/plain").build();
    }
}
